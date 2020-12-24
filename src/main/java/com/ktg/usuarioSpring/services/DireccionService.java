package com.ktg.usuarioSpring.services;

import com.ktg.usuarioSpring.dao.IDireccionDao;
import com.ktg.usuarioSpring.dao.IUsuarioDao;
import com.ktg.usuarioSpring.model.UserVO;
import com.ktg.usuarioSpring.model.entity.Direccion;
import com.ktg.usuarioSpring.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionService {

    @Autowired
    IDireccionDao direccionDao;
    IUsuarioDao usuarioDao;

    public List<Direccion> getDireccion(){
        return direccionDao.getDireccion();
    }

    public UserVO getFullDireccion(long id){
        UserVO dto = null;

        Direccion dir = direccionDao.getDireccionById(id);
        Usuario user = dir.getUsuario();

        dto = new UserVO();
        dto.setUsuario("Nombre: " + user.getNombre() + " " + user.getApellido());
        //dto.setDireccion("Dirección: " + dir.getCalle() + ", " + dir.getNo_exterior() + ", " + dir.getCp()
        //                 ". Estado: " + dir.getEstado() + ". Referencia: " + dir.getReferencia());
        dto.setDireccion("Dirección: " + dir.getCalle() + ", " + dir.getNo_exterior() + ", " + dir.getCp());
        dto.setEstado(dir.getEstado());
        dto.setReferencia(dir.getReferencia());

        return dto;
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

    public void eliminar(long id){
        direccionDao.eliminar(id);
    }
}
