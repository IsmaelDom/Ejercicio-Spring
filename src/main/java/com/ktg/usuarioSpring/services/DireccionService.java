package com.ktg.usuarioSpring.services;

import com.ktg.usuarioSpring.controllers.UsuariosDTO;
import com.ktg.usuarioSpring.dao.IDireccionDao;
import com.ktg.usuarioSpring.controllers.DireccionUserDTO;
import com.ktg.usuarioSpring.dao.IUsuarioDao;
import com.ktg.usuarioSpring.model.entity.Direccion;
import com.ktg.usuarioSpring.model.entity.Usuario;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//Anotación que indica que es un servicio y tiene logica de negocio
@Service
@Log
public class DireccionService {

    @Autowired
    IDireccionDao direccionDao;

    @Autowired
    IUsuarioDao usuarioDao;

    public List<UsuariosDTO> getAll(){
        return ((List<Direccion>) direccionDao.getAll())
                .stream().map(this::convertToUsuariosDTO)
                .collect(Collectors.toList());
    }

    private UsuariosDTO convertToUsuariosDTO(Direccion direccion) {
        UsuariosDTO usuariosDTO = new UsuariosDTO();
        usuariosDTO.setId(direccion.getId());
        usuariosDTO.setFullDireccion(direccion.getCalle() + ", No. " +
                direccion.getNo_exterior() + ", " + direccion.getCp());
        usuariosDTO.setEstado(direccion.getEstado());
        usuariosDTO.setMunicipio(direccion.getMunicipio());
        usuariosDTO.setReferencia(direccion.getReferencia());
        usuariosDTO.setFullName(direccion.getUsuario().getNombre() + " " + direccion.getUsuario().getApellido());
        usuariosDTO.setCorreo(direccion.getUsuario().getCorreo());
        usuariosDTO.setEdad(direccion.getUsuario().getEdad());
        usuariosDTO.setCurp(direccion.getUsuario().getCurp());
        return usuariosDTO;
    }

    public DireccionUserDTO getFullDireccion(long id){
        DireccionUserDTO dto;

        Direccion dir = direccionDao.getDireccionById(id);
        if (dir == null){
            log.log(Level.SEVERE, "Error al obtener usuario con dirección, no existe usuario con id: " + id);
            return null;
        }else{
            Usuario user = dir.getUsuario();

            dto = new DireccionUserDTO();
            dto.setUsuario("Nombre Completo: " + user.getNombre() + " " + user.getApellido() +
                    ". E-mail: " + user.getCorreo() + ". Edad: " + user.getEdad());
            dto.setDireccion("Dirección: " + dir.getCalle() + ", " + dir.getNo_exterior() + ", " + dir.getCp()
                    + ". Estado: " + dir.getEstado() + ". Referencia: " + dir.getReferencia());
            log.log(Level.INFO, dto.toString());
            return dto;
        }
    }

    public Direccion getDireccionById(long id){
        return direccionDao.getDireccionById(id);
    }

    //Los posibles errores se almacenan en el parámetro de tipo BindingResult(resValida)
    public Map<String, Object> registrar(Direccion direccion) {
        Map<String, Object> result = new HashMap<>();

        if(direccion.getNo_exterior() == null || direccion.getEstado() == null || direccion.getCp() == null || direccion.getCalle() == null
                || direccion.getUsuario().getApellido() == null || direccion.getMunicipio() == null || direccion.getUsuario().getCorreo() == null
                || direccion.getUsuario().getNombre() == null || direccion.getUsuario().getPassword() == null || direccion.getReferencia() == null
                || direccion.getUsuario().getCurp() == null || direccion.getEstado().isEmpty() || direccion.getReferencia().isEmpty()
                || direccion.getNo_exterior().isEmpty() || direccion.getCalle().isEmpty() || direccion.getCp().isEmpty()
                || direccion.getMunicipio().isEmpty() || direccion.getUsuario().getCorreo().isEmpty()
                || direccion.getUsuario().getEdad() <= 0 || direccion.getUsuario().getApellido().isEmpty()
                || direccion.getUsuario().getNombre().isEmpty() || direccion.getUsuario().getPassword().isEmpty()) {
            result = validaDatos(direccion);
            log.log(Level.SEVERE, "####### Error al Insertar Usuario con Dirección #####");
            log.log(Level.SEVERE, "Hay valores nulos, errores: ");
            log.log(Level.SEVERE, result.toString());
        }else if(!validaEnterosConString(direccion.getNo_exterior())){
            result.put("no_exterior", "Ingrese solo números para el no. exterior.");
            log.log(Level.SEVERE, "####### Error al Editar Usuario con Dirección #####");
            log.log(Level.SEVERE, result.toString());
        }else if(!validaEnteros(direccion.getUsuario().getEdad())){
            result.put("edad", "Ingrese solo números para la edad.");
            log.log(Level.SEVERE, "####### Error al Editar Usuario con Dirección #####");
            log.log(Level.SEVERE, result.toString());
        }else if (!validaCorreo(direccion.getUsuario().getCorreo())) {
            result.put("correo", "Ingrese un correo valido.");
            log.log(Level.SEVERE, "####### Error al Editar Usuario con Dirección #####");
            log.log(Level.SEVERE, result.toString());
        }else if(direccion.getUsuario().getPassword().length() < 5){
            result.put("password", "La contraseña debe tener más de 5 caracteres.");
            log.log(Level.SEVERE, "####### Error al Editar Usuario con Dirección #####");
            log.log(Level.SEVERE, result.toString());
        }else{
            if (usuarioDao.getUsuarioLogin(direccion.getUsuario().getCorreo()).toString().isEmpty()) {
                Direccion dir = direccionDao.registrar(direccion);
                result.put("mensaje","Usuario registrado correctamente");
                result.put("usuario", dir);

                log.log(Level.INFO, "####### Usuario con Dirección Insertado Correctamente");
                log.log(Level.INFO, dir.toString());
            }else{
                result.put("correo","El correo " + direccion.getUsuario().getCorreo() + " ya existe, intente con otro.");
            }
        }
        return result;
    }

    private boolean validaCorreo(String email){
        String regexp = "^[a-zA-Z0-9]+[a-zA-Z0-9_.+-]+?@[a-zA-Z0-9-]+\\.[a-zA-Z0-9]+[.a-zA-Z]{0,3}$";
        log.log(Level.INFO,"###### Método validaCorreo devuelve: " + Pattern.matches(regexp, email) + " ######");
        return Pattern.matches(regexp, email);
    }

    private boolean validaEnteros(int numero){
        String regexp = "^[1-9][0-9]?[0-9]?$";
        String convertirInt = String.valueOf(numero);
        log.log(Level.INFO,"###### Método validaEnteros devuelve: " + Pattern.matches(regexp, convertirInt) + " ######");
        return Pattern.matches(regexp, convertirInt);
    }

    private boolean validaEnterosConString(String cadena){
        String regexp = "^\\d+$";
        log.log(Level.INFO,"###### Método validaEnterosConString devuelve: " + Pattern.matches(regexp, cadena) + " ######");
        return Pattern.matches(regexp, cadena);
    }

    private Map<String, Object> validaDatos(Direccion direccion){
        Map<String, Object> result = new HashMap<>();
        if (direccion.getEstado() == null || direccion.getEstado().isEmpty()) {
            result.put("estado", "Ingrese un estado.");
        }
        if (direccion.getReferencia() == null || direccion.getReferencia().isEmpty() || direccion.getReferencia().trim().isEmpty()) {
            result.put("referencia", "Ingrese una referencia.");
        }
        if (direccion.getCp() == null || direccion.getCp().isEmpty()) {
            result.put("cp", "Ingrese un código postal.");
        }
        if (direccion.getNo_exterior() == null || direccion.getNo_exterior().isEmpty()) {
            result.put("no_exterior", "Ingrese un No. Exterior.");
        }
        if (direccion.getCalle() == null || direccion.getCalle().isEmpty()) {
            result.put("calle", "Ingrese una calle.");
        }
        if (direccion.getMunicipio() == null || direccion.getMunicipio().isEmpty()) {
            result.put("municipio", "Ingrese un municipio.");
        }
        if (direccion.getUsuario().getCorreo() == null || direccion.getUsuario().getCorreo().isEmpty()) {
            result.put("correo", "Ingrese un correo.");
        }
        if (direccion.getUsuario().getEdad() <= 0) {
            result.put("edad", "Ingrese una edad.");
        }
        if (direccion.getUsuario().getApellido() == null || direccion.getUsuario().getApellido().isEmpty()) {
            result.put("apellido", "Ingrese un apellido.");
        }
        if (direccion.getUsuario().getNombre() == null || direccion.getUsuario().getNombre().isEmpty()) {
            result.put("nombre", "Ingrese un nombre.");
        }
        if (direccion.getUsuario().getPassword() == null || direccion.getUsuario().getPassword().isEmpty()) {
            result.put("password", "Ingrese una contraseña.");
        }
        if (direccion.getUsuario().getCurp() == null || direccion.getUsuario().getCurp().isEmpty()) {
            result.put("curp", "Ingrese una CURP.");
        }

        return result;
    }

    public Map<String, Object> editar(Direccion direccion) {
        Map<String, Object> result = new HashMap<>();
        if (direccion.getNo_exterior() == null || direccion.getEstado() == null || direccion.getCp() == null || direccion.getCalle() == null
                || direccion.getUsuario().getApellido() == null || direccion.getMunicipio() == null || direccion.getUsuario().getCorreo() == null
                || direccion.getUsuario().getNombre() == null || direccion.getUsuario().getPassword() == null || direccion.getReferencia() == null
                || direccion.getUsuario().getCurp() == null || direccion.getEstado().isEmpty() || direccion.getReferencia().isEmpty()
                || direccion.getNo_exterior().isEmpty() || direccion.getCalle().isEmpty() || direccion.getCp().isEmpty()
                || direccion.getMunicipio().isEmpty() || direccion.getUsuario().getCorreo().isEmpty()
                || direccion.getUsuario().getEdad() <= 0 || direccion.getUsuario().getApellido().isEmpty()
                || direccion.getUsuario().getNombre().isEmpty() || direccion.getUsuario().getPassword().isEmpty()) {

            result = validaDatos(direccion);
            log.log(Level.SEVERE, "####### Error al Editar Usuario con Dirección #####");
            log.log(Level.SEVERE, "####### Hay campos null errores:");
            log.log(Level.SEVERE, result.toString());
        }else if(!validaEnterosConString(direccion.getNo_exterior())){
            result.put("no_exterior", "Ingrese solo números para el no. exterior.");
            log.log(Level.SEVERE, "####### Error al Editar Usuario con Dirección #####");
            log.log(Level.SEVERE, result.toString());
        }else if(!validaEnteros(direccion.getUsuario().getEdad())){
            result.put("edad", "Ingrese solo números para la edad.");
            log.log(Level.SEVERE, "####### Error al Editar Usuario con Dirección #####");
            log.log(Level.SEVERE, result.toString());
        }else if (!validaCorreo(direccion.getUsuario().getCorreo())) {
            result.put("correo", "Ingrese un correo valido.");
            log.log(Level.SEVERE, "####### Error al Editar Usuario con Dirección #####");
            log.log(Level.SEVERE, result.toString());
        }else if(direccion.getUsuario().getPassword().length() < 5){
            result.put("password", "La contraseña debe tener más de 5 caracteres.");
            log.log(Level.SEVERE, "####### Error al Editar Usuario con Dirección #####");
            log.log(Level.SEVERE, result.toString());
        }else{
            Usuario correo = usuarioDao.getUsuarioLogin(direccion.getUsuario().getCorreo());
            if (correo.toString().isEmpty() || !correoDuplicado(direccion, correo)) {
                Direccion dir = direccionDao.editar(direccion);
                result.put("exito","Usuario editado correctamente");
                result.put("usuario", dir);

                log.log(Level.INFO, "####### Usuario con Dirección Editado Correctamente");
                log.log(Level.INFO, dir.toString());
            }else{
                log.log(Level.SEVERE,"El correo " + direccion.getUsuario().getCorreo() + " ya existe en la base de datos.");
                result.put("correo","El correo " + direccion.getUsuario().getCorreo() + " ya existe, intente con otro.");
            }
        }

        return result;
    }

    private boolean correoDuplicado(Direccion direccion, Usuario usuario){
        Direccion direccionOriginal = direccionDao.getDireccionById(direccion.getId());
        String correoOriginal = direccionOriginal.getUsuario().getCorreo();
        String correoEditado = usuario.getCorreo();
        log.log(Level.INFO,"correoOriginal: " + correoOriginal);
        log.log(Level.INFO,"correoEditado: " + correoEditado);
        if(correoOriginal.equals(correoEditado)){
            log.log(Level.INFO,"Metodo correoDuplicado devuelve falso.");
            return false;
        }
        log.log(Level.INFO,"Metodo correoDuplicado devuelve verdadero.");
        return true;
    }

    public void eliminar(long id){
        log.log(Level.INFO, "####### Usuario con Dirección: " + id + " eliminado.");
        direccionDao.eliminar(id);
    }
}