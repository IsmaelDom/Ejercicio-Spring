package com.ktg.usuarioSpring.controllers;

import com.ktg.usuarioSpring.model.entity.Direccion;
import com.ktg.usuarioSpring.services.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("direccion")

public class DireccionController {

    @Autowired
    DireccionService direccionService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<Direccion> getDirecciones(){
        return direccionService.getDireccion();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Direccion getDireccionById(@PathVariable long id){
        return direccionService.getDireccionById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    Direccion registrar(@RequestBody Direccion direccion){
        return direccionService.registrar(direccion);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    Direccion editar(@RequestBody Direccion direccion){
        return direccionService.editar(direccion);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    void eliminar(@PathVariable long id){
        direccionService.eliminar(id);
    }
}
