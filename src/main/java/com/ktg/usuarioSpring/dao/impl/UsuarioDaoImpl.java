package com.ktg.usuarioSpring.dao.impl;

import com.ktg.usuarioSpring.dao.IUsuarioDao;
import com.ktg.usuarioSpring.model.entity.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    public List<Usuario> getUsuarios(){
        String hql = "FROM Usuario as u";
        return (List<Usuario>) entityManager.createQuery(hql).getResultList();
    }

    @Override
    public Usuario getUsuarioById(long id){
        return entityManager.find(Usuario.class, id);
    }

    @Override
    public Usuario registrar(Usuario user){
        entityManager.merge(user);
        return user;
    }

    @Override
    public Usuario editar(Usuario user){
        entityManager.merge(user);
        return user;
    }

    @Override
    public void eliminar(long id){
        Usuario usuario = getUsuarioById(id);
        entityManager.remove(usuario);
    }

    @Override
    public Usuario login(Usuario dto) {
        boolean isAuthenticated = false;

        String hql = "FROM Usuario as u WHERE u.password is not null and u.correo = :correo";

        List<Usuario> result = entityManager.createQuery(hql.toString())
                .setParameter("correo", dto.getCorreo())
                .getResultList();
        if (result.size() == 0) { return null; }

        Usuario user = result.get(0);
        isAuthenticated = true;

        if (!StringUtils.isEmpty(dto.getPassword())) {
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            isAuthenticated = argon2.verify(user.getPassword(), dto.getPassword());
        }
        if (isAuthenticated) {
            return user;
        }
        return null;
    }
}
