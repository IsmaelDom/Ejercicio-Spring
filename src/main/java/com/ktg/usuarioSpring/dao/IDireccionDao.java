package com.ktg.usuarioSpring.dao;

import com.ktg.usuarioSpring.model.entity.Direccion;

import java.util.List;

public interface IDireccionDao {

    Direccion getDireccionById(long id);
    Direccion getDireccionByIdAndStatus(long id);

    Direccion registrar(Direccion direccion);
    Direccion editar(Direccion direccion);
    void eliminar(long id);

    List<Direccion> getAll();
    String eliminaLogica(long id);
}
