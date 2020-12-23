package com.ktg.usuarioSpring.dao.impl;

import com.ktg.usuarioSpring.dao.IUsuarioDao;
import com.ktg.usuarioSpring.model.entity.Usuario;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class UsuarioDaoImpl implements IUsuarioDao {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public List<Usuario> getUsuarios(){
        String hql = "FROM Usuario as u";
        return (List<Usuario>) entityManager.createQuery(hql).getResultList();
    }

    @Transactional
    @Override
    public Usuario getUsuarioById(long id){

        return entityManager.find(Usuario.class, id);
    }

    @Transactional
    @Override
    public Usuario registrar(Usuario user){
        entityManager.merge(user);
        return user;
    }

    @Transactional
    @Override
    public Usuario editar(Usuario user){
        entityManager.merge(user);
        return user;
    }

    @Transactional
    @Override
    public String eliminar(long id){
        Usuario usuario = getUsuarioById(id);
        entityManager.remove(usuario);
        String eliminado = "Usuario Eliminado";
        return eliminado;
    }
}
