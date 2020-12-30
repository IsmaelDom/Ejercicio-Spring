package com.ktg.usuarioSpring.services;

import com.ktg.usuarioSpring.dao.IUsuarioDao;
import com.ktg.usuarioSpring.model.entity.Usuario;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

@Service
@Log
public class UsuarioService {

    @Autowired
    IUsuarioDao usuarioDao;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    public List<Usuario> getUsuarios(){
        return usuarioDao.getUsuarios();
    }

    public Usuario getUsuarioById(long id){
        return usuarioDao.getUsuarioById(id);
    }

    public Usuario registrar(Usuario user, BindingResult respuesta){
        Set<ConstraintViolation<Usuario>> violations = validator.validate(user);
        if(respuesta.hasErrors()){
            log.log(Level.SEVERE, "####### Error al Insertar Usuario #####");
            log.log(Level.SEVERE, violations.iterator().next().getMessage() +
                    ", Valor: " + violations.iterator().next().getInvalidValue());

            return null;
        }else{
            Usuario usuario = null;
            try {
                usuario = usuarioDao.registrar(user);
            } catch (DataAccessException ex){
                log.log(Level.SEVERE, "####### Error al Insertar Usuario: " + ex.getMessage() + ": " +
                        ex.getMostSpecificCause().getMessage() + " #######");
                return null;
            }
            log.log(Level.INFO, "####### Usuario Insertado Correctamente");
            log.log(Level.INFO, usuario.toString());
            return usuario;
        }
    }

    public Usuario editar(Usuario user, BindingResult respuesta){
        Set<ConstraintViolation<Usuario>> violations = validator.validate(user);
        if(respuesta.hasErrors()){
            log.log(Level.SEVERE, "####### Error al Editar Usuario #####");
            log.log(Level.SEVERE, violations.iterator().next().getMessage() +
                    ", Valor: "+violations.iterator().next().getInvalidValue());

            return null;
        }else{
            Usuario usuario = null;
            try {
                usuario = usuarioDao.editar(user);
            } catch (DataAccessException ex){//excepcion de acceso a datos
                log.log(Level.SEVERE, "####### Error al Editar Usuario: " + ex.getMessage() + ": " +
                        ex.getMostSpecificCause().getMessage() + " #######");
                return null;
            }
            log.log(Level.INFO, "####### Usuario Editado Correctamente");
            log.log(Level.INFO, usuario.toString());
            return usuario;
        }
    }

    public void eliminar(long id){
        log.log(Level.INFO, "Usuario con id:" + id + " eliminado.");
        usuarioDao.eliminar(id);
    }
}
