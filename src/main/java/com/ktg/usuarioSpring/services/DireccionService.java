package com.ktg.usuarioSpring.services;

import com.ktg.usuarioSpring.dao.IDireccionDao;
import com.ktg.usuarioSpring.controllers.DireccionUserDTO;
import com.ktg.usuarioSpring.model.entity.Direccion;
import com.ktg.usuarioSpring.model.entity.Usuario;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

//Anotación que indica que es un servicio y tiene logica de negocio
@Service
@Log
public class DireccionService {

    @Autowired
    IDireccionDao direccionDao;

    public List<DireccionUserDTO> getAll(){
        return direccionDao.getAll();
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
    public Direccion registrar(Direccion direccion){

        if(direccion.getEstado().isEmpty() || direccion.getReferencia().isEmpty() || direccion.getCp().isEmpty()
                || direccion.getNo_exterior().isEmpty() || direccion.getCalle().isEmpty()
                || direccion.getMunicipio().isEmpty() || direccion.getUsuario().getCorreo().isEmpty()
                || direccion.getUsuario().getEdad() <= 0 || direccion.getUsuario().getApellido().isEmpty()
                || direccion.getUsuario().getNombre().isEmpty() || direccion.getUsuario().getPassword().isEmpty()){

            log.log(Level.SEVERE, "####### Error al Insertar Usuario con Dirección #####");
            log.log(Level.SEVERE, "Hay valores nulos");
            return null;
        }else{
            Direccion dir = null;
            //try {
                dir = direccionDao.registrar(direccion);
            /*} catch (DataAccessException ex){
                log.log(Level.SEVERE, "####### Error al Insertar Usuario con Dirección: " + ex.getMessage() + ": " +
                                                ex.getMostSpecificCause().getMessage() + " #######");
                return null;
            }*/
            log.log(Level.INFO, "####### Usuario con Dirección Insertado Correctamente");
            log.log(Level.INFO, dir.toString());
            return dir;
        }
    }

    public Direccion editar(Direccion direccion){

        if(direccion.getEstado().isEmpty() || direccion.getReferencia().isEmpty() || direccion.getCp().isEmpty()
                || direccion.getNo_exterior().isEmpty() || direccion.getCalle().isEmpty()
                || direccion.getMunicipio().isEmpty() || direccion.getUsuario().getCorreo().isEmpty()
                || direccion.getUsuario().getEdad() <= 0 || direccion.getUsuario().getApellido().isEmpty()
                || direccion.getUsuario().getNombre().isEmpty() || direccion.getUsuario().getPassword().isEmpty()){

            log.log(Level.SEVERE, "####### Error al Editar Usuario con Dirección #####");
            log.log(Level.SEVERE, "####### Hay campos null");

            return null;
        }else{
            Direccion dir = null;
            /*try {*/
                dir = direccionDao.editar(direccion);
            /*} catch (DataAccessException ex){
                log.log(Level.SEVERE, "####### Error al Editar Usuario con Dirección: " + ex.getMessage() + ": " +
                        ex.getMostSpecificCause().getMessage() + " #######");
                return null;
            }*/
            log.log(Level.INFO, "####### Usuario con Dirección Editado Correctamente");
            log.log(Level.INFO, dir.toString());
            return dir;
        }
    }

    public void eliminar(long id){
        log.log(Level.INFO, "####### Usuario con Dirección: " + id + " eliminado.");
        direccionDao.eliminar(id);
    }
}