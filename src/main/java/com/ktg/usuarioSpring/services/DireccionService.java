package com.ktg.usuarioSpring.services;

import com.ktg.usuarioSpring.dao.IDireccionDao;
import com.ktg.usuarioSpring.model.DireccionUserVO;
import com.ktg.usuarioSpring.model.entity.Direccion;
import com.ktg.usuarioSpring.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DireccionService {

    @Autowired
    IDireccionDao direccionDao;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    public List<DireccionUserVO> getAll(){
        return direccionDao.getAll();
    }

    public DireccionUserVO getFullDireccion(long id){
        DireccionUserVO dto;

        Direccion dir = direccionDao.getDireccionById(id);
        Usuario user = dir.getUsuario();

        dto = new DireccionUserVO();
        dto.setUsuario("Nombre: " + user.getNombre() + " " + user.getApellido());
        dto.setDireccion("Dirección: " + dir.getCalle() + ", " + dir.getNo_exterior() + ", " + dir.getCp()
                         + ". Estado: " + dir.getEstado() + ". Referencia: " + dir.getReferencia());

        return dto;
    }

    public Direccion getDireccionById(long id){
        return direccionDao.getDireccionById(id);
    }

    public Direccion registrar(Direccion direccion, BindingResult resValida){
        Set<ConstraintViolation<Direccion>> violations = validator.validate(direccion);
        Set<ConstraintViolation<Usuario>> violationUsers = validator.validate(direccion.getUsuario());

        if(resValida.hasErrors()){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"####### Error al Insertar #####");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, violations.iterator().next().getMessage());
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, violationUsers.iterator().next().getMessage());

            /*for (ConstraintViolation<Direccion> violation : violations) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, violation.getMessage());
            }*/

            /*for (ConstraintViolation<Usuario> violationUser : violationUsers) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, violationUser.getMessage());
            }*/
            return null;
        }else{
            return direccionDao.registrar(direccion);
        }
    }

    public Direccion editar(Direccion direccion, BindingResult resValida){
        Set<ConstraintViolation<Direccion>> violations = validator.validate(direccion);
        if(resValida.hasErrors()){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"####### Error al Editar #####");
            for (ConstraintViolation<Direccion> violation : violations) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, violation.getMessage());
            }
            return null;
        }else{
            return direccionDao.editar(direccion);
        }
    }

    public void eliminar(long id){
        direccionDao.eliminar(id);
    }
}
