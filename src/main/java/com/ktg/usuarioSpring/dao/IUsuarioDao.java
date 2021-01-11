package com.ktg.usuarioSpring.dao;

import com.ktg.usuarioSpring.model.entity.Usuario;

import java.util.List;

public interface IUsuarioDao {

    List<Usuario> getUsuarios();
    Usuario getUsuarioById(long id);
    Usuario registrar(Usuario user);
    Usuario editar(Usuario user);
    void eliminar(long id);
    public Usuario login(Usuario user);
}
