package com.ktg.usuarioSpring.dao.impl;

import com.ktg.usuarioSpring.dao.IUsuarioDao;
import com.ktg.usuarioSpring.model.entity.Usuario;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//Los métodos se van a ejecutar en una transacción
@Transactional
//Se utiliza para clases que acceden directamente a la base de datos
@Repository
public class UsuarioDaoImpl implements IUsuarioDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Usuario> getUsuarios() {
        String hql = "FROM Usuario as u where u.status = 1";
        return (List<Usuario>) entityManager.createQuery(hql).getResultList();
    }

    @Override
    public Usuario getUsuarioById(long id) {
        return entityManager.find(Usuario.class, id);
    }

    @Override
    public Usuario registrar(Usuario user) {
        entityManager.merge(user);
        return user;
    }

    @Override
    public Usuario editar(Usuario user) {
        entityManager.merge(user);
        return user;
    }

    @Override
    public void eliminar(long id) {
        Usuario usuario = getUsuarioById(id);
        entityManager.remove(usuario);
    }

    @Override
    public Usuario getUsuarioLogin(String correo) {
        String hql = "FROM Usuario as u where u.correo = :correo and u.status = 1";
        //Usuario query = entityManager.createQuery(hql, Usuario.class).setParameter("correo",correo).getSingleResult();
        List<Usuario> resultado = entityManager.createQuery(hql.toString()).setParameter("correo", correo)
                                    .getResultList();
        if (resultado.size() == 0){// query.toString().isEmpty()){
            return null;
        }
        return resultado.get(0);
        //return entityManager.find(Usuario.class, correo);
    }

    @Override
    public Usuario getCurp(String curp) {
        String hql = "FROM Usuario as u where u.curp = :curp and u.status = 1";
        List<Usuario> resultado = entityManager.createQuery(hql.toString()).setParameter("curp", curp)
                .getResultList();
        if (resultado.size() == 0){// query.toString().isEmpty()){
            return null;
        }
        return resultado.get(0);
    }
}
