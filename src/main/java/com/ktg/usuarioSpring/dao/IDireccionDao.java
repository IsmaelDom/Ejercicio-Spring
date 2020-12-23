package com.ktg.usuarioSpring.dao;

import com.ktg.usuarioSpring.model.entity.Direccion;

import java.util.List;

public interface IDireccionDao {

    List<Direccion> getDireccion();
    Direccion getDireccionById(long id);
    Direccion registrar(Direccion direccion);
    Direccion editar(Direccion direccion);
    String eliminar(long id);
}