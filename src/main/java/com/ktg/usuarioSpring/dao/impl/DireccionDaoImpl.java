package com.ktg.usuarioSpring.dao.impl;

import com.ktg.usuarioSpring.dao.IDireccionDao;
import com.ktg.usuarioSpring.model.entity.Direccion;
import com.ktg.usuarioSpring.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//Los métodos se van a ejecutar en una transacción
@Transactional
//Se utiliza para clases que acceden directamente a la base de datos
@Repository
public class DireccionDaoImpl implements IDireccionDao {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public List<Direccion> getAll() {
        String hql = "FROM Direccion as d where d.status = 1";
        return (List<Direccion>) entityManager.createQuery(hql).getResultList();
    }

    @Override
    public Direccion getDireccionById(long id) {
        return entityManager.find(Direccion.class, id);
    }

    @Override
    public Direccion getDireccionByIdAndStatus(long id) {
        String hql = "FROM Direccion as d where d.status = 1 and d.id = :id";
        List<Direccion> resultado = entityManager.createQuery(hql.toString()).setParameter("id", id).getResultList();
        if (resultado.size() == 0){
            return null;
        }
        return resultado.get(0);
    }

    @Override
    public Direccion registrar(Direccion direccion) {
        //Se encripta la contraseña
        String pass = encoder.encode(direccion.getUsuario().getPassword());
        //Se settea la contraseña encriptada
        direccion.getUsuario().setPassword(pass);
        entityManager.merge(direccion);
        return direccion;
    }

    @Override
    public Direccion editar(Direccion direccion) {
        //Se encripta la contraseña
        //String pass = encoder.encode(direccion.getUsuario().getPassword());
        //Se settea la contraseña encriptada
        //direccion.getUsuario().setPassword(pass);
        entityManager.merge(direccion);
        return direccion;
    }

    @Override
    //falta actualizar en usuario tambien
    public void eliminar(long id) {
        Direccion dir = getDireccionById(id);
        entityManager.remove(dir);
    }

    @Override
    public String eliminacionLogica(long id) {
        /*String hqlUpdateDireccion = "update Direccion d set d.status = 0, d.usuario.status = 0 where d.id = :id";
        List<Usuario> resultado = entityManager.createQuery(hqlUpdateDireccion.toString()).setParameter("id", id)
                .getResultList();
        if (resultado.size() == 0){// query.toString().isEmpty()){
            return null;
        }*/
        Direccion direccion = getDireccionByIdAndStatus(id);
        direccion.setStatus("0");
        direccion.getUsuario().setStatus("0");
        entityManager.merge(direccion);
        return "Usuario " + direccion.getUsuario().getNombre() + ' ' + direccion.getUsuario().getApellido() + " eliminado.";
    }
}