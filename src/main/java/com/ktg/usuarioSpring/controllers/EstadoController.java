package com.ktg.usuarioSpring.controllers;

import com.ktg.usuarioSpring.model.entity.Estado;
import com.ktg.usuarioSpring.services.estado.IEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private IEstadoService estadoService;

    @GetMapping
    public List<Estado> estados(){
        return estadoService.findAll();
    }
}
