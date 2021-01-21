package com.ktg.usuarioSpring.dao;

import com.ktg.usuarioSpring.model.entity.Municipio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IMunicipioDao extends CrudRepository<Municipio, Integer> {

    @Query("from Municipio as m where m.estado.id = :id")
    List<Municipio> findAllEstados(int id);
}
