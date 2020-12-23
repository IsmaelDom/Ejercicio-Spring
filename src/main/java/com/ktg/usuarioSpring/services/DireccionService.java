package com.ktg.usuarioSpring.services;

import com.ktg.usuarioSpring.dao.IDireccionDao;
import com.ktg.usuarioSpring.model.entity.Direccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionService {

    @Autowired
    IDireccionDao direccionDao;

    public List<Direccion> getDireccion(){
        return direccionDao.getDireccion();
    }

    public Direccion getDireccionById(long id){
        return direccionDao.getDireccionById(id);
    }

    public Direccion registrar(Direccion direccion){
        return direccionDao.registrar(direccion);
    }

    public Direccion editar(Direccion direccion){
        return direccionDao.editar(direccion);
    }

    public String eliminar(long id){
        direccionDao.eliminar(id);
        String eliminado = "Direccion Eliminada Correctamente";
        return eliminado;
    }
}
