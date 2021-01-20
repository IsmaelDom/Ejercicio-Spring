package com.ktg.usuarioSpring.services.estado;

import com.ktg.usuarioSpring.dao.IEstadoDao;
import com.ktg.usuarioSpring.model.entity.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstadoServiceImpl implements IEstadoService{

    @Autowired
    private IEstadoDao estadoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Estado> findAll() {
        return (List<Estado>) estadoDao.findAll();
    }
}
