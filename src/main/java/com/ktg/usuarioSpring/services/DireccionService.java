package com.ktg.usuarioSpring.services;

import com.ktg.usuarioSpring.dao.IDireccionDao;
import com.ktg.usuarioSpring.controllers.DireccionUserDTO;
import com.ktg.usuarioSpring.model.entity.Direccion;
import com.ktg.usuarioSpring.model.entity.Usuario;
import lombok.extern.java.Log;
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

@Service
@Log
public class DireccionService {

    @Autowired
    IDireccionDao direccionDao;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    public List<DireccionUserDTO> getAll(){
        return direccionDao.getAll();
    }

    public DireccionUserDTO getFullDireccion(long id){
        DireccionUserDTO dto;

        Direccion dir = direccionDao.getDireccionById(id);
        if (dir == null){
            log.log(Level.SEVERE, "Error no existe usuario con id: " + id);
            return null;
        }else{
            Usuario user = dir.getUsuario();

            dto = new DireccionUserDTO();
            dto.setUsuario("Nombre Completo: " + user.getNombre() + " " + user.getApellido() +
                    ". Usuario: " + user.getUsuario());
            dto.setDireccion("Dirección: " + dir.getCalle() + ", " + dir.getNo_exterior() + ", " + dir.getCp()
                    + ". Estado: " + dir.getEstado() + ". Referencia: " + dir.getReferencia());
            log.log(Level.INFO, dto.toString());
            return dto;
        }
    }

    public Direccion getDireccionById(long id){
        return direccionDao.getDireccionById(id);
    }

    public Direccion registrar(Direccion direccion, BindingResult resValida){
        Set<ConstraintViolation<Direccion>> violations = validator.validate(direccion);
        if(resValida.hasErrors()){
            log.log(Level.SEVERE, "####### Error al Insertar #####");
            log.log(Level.SEVERE, violations.iterator().next().getMessage());

            return null;
        }else{
            log.log(Level.INFO, "####### Usuario Insertado Correctamente");
            return direccionDao.registrar(direccion);
        }
    }

    public Direccion editar(Direccion direccion, BindingResult resValida){
        Set<ConstraintViolation<Direccion>> violations = validator.validate(direccion);
        if(resValida.hasErrors()){
            log.log(Level.SEVERE, "####### Error al Editar #####");
            log.log(Level.SEVERE, violations.iterator().next().getMessage());

            return null;
        }else{
            log.log(Level.INFO, "####### Usuario Editado Correctamente");
            return direccionDao.editar(direccion);
        }
    }

    public void eliminar(long id){
        direccionDao.eliminar(id);
    }
}