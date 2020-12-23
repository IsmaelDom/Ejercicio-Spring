package com.ktg.usuarioSpring.services;

import com.ktg.usuarioSpring.dao.IUsuarioDao;
import com.ktg.usuarioSpring.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    IUsuarioDao usuarioDao;

    public List<Usuario> getUsuarios(){
        return usuarioDao.getUsuarios();
    }

    public Usuario getUsuarioById(long id){
        return usuarioDao.getUsuarioById(id);
    }

    public Usuario registrar(Usuario user){
        return usuarioDao.registrar(user);
    }

    public Usuario editar(Usuario user){
        return usuarioDao.editar(user);
    }

    public String eliminar(long id){
        usuarioDao.eliminar(id);
        String eliminado = "Usuario Eliminado Correctamente";
        return eliminado;
    }
}
