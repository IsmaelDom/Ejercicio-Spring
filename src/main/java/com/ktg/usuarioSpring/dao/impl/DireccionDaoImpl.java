package com.ktg.usuarioSpring.dao.impl;

import com.ktg.usuarioSpring.dao.IDireccionDao;
import com.ktg.usuarioSpring.model.entity.Direccion;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class DireccionDaoImpl implements IDireccionDao {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public List<Direccion> getDireccion() {
        String hql = "FROM Direccion as d";
        return (List<Direccion>) entityManager.createQuery(hql).getResultList();
    }

    @Transactional
    @Override
    public Direccion getDireccionById(long id) {
        return entityManager.find(Direccion.class, id);
    }

    @Transactional
    @Override
    public Direccion registrar(Direccion direccion) {
        entityManager.merge(direccion);
        return direccion;
    }

    @Transactional
    @Override
    public Direccion editar(Direccion direccion) {
        entityManager.merge(direccion);
        return direccion;
    }

    @Transactional
    @Override
    public void eliminar(long id) {
        Direccion dir = getDireccionById(id);
        entityManager.remove(dir);
    }
}
