package com.ktg.usuarioSpring.services.estado;

import com.ktg.usuarioSpring.dao.IMunicipioDao;
import com.ktg.usuarioSpring.model.entity.Municipio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class MunicipioServiceImpl implements IMunicipioService{
    @Autowired
    private IMunicipioDao municipioDao;

    @Override
    @Transactional(readOnly = true)
    public List<Municipio> findAll() {
        return (List<Municipio>) municipioDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Municipio findById(int id) {
        return municipioDao.findById(id).orElse(null);
    }

    @Override
    public Municipio findByIdAll(int id) {
        return (Municipio) municipioDao.findAllById(Collections.singleton(id));
    }

    @Override
    public List<Municipio> findAllEstados(int id) {
        List<Municipio> municipios = municipioDao.findAllEstados(id);
        if (municipios.size() == 0){
            return null;
        }
        return municipios;
    }
}
