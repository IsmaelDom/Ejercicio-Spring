package com.ktg.usuarioSpring.services.estado;

import com.ktg.usuarioSpring.model.entity.Estado;
import com.ktg.usuarioSpring.model.entity.Municipio;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IMunicipioService {
    @Transactional(readOnly = true)
    List<Municipio> findAll();
    Municipio findById(int id);
    Municipio findByIdAll(int id);
    List<Municipio> findAllEstados(int id);
}