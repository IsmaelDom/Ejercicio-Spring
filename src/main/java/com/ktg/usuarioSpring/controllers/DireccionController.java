package com.ktg.usuarioSpring.controllers;

import com.ktg.usuarioSpring.model.DireccionUserVO;
import com.ktg.usuarioSpring.model.entity.Direccion;
import com.ktg.usuarioSpring.services.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//Anotación que indica que es un servicio REST
@RestController
//URL
@RequestMapping("direccion")

public class DireccionController {

    @Autowired
    DireccionService direccionService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping //Tambien se puede usar pero toma la url del RequestMapping
    //@RequestMapping(value = "/", method = RequestMethod.GET)
    List<Direccion> getDirecciones(){
        return direccionService.getDireccion();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    List<DireccionUserVO> getAll(){
        return direccionService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    Direccion getDireccionById(@PathVariable long id){
        Optional<Direccion> optionalDireccion = Optional.ofNullable(direccionService.getDireccionById(id));
        if (optionalDireccion.isPresent()){
            return optionalDireccion.get();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    DireccionUserVO getFullDireccionById(@PathVariable long id){
        return direccionService.getFullDireccion(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    Direccion registrar(@RequestBody Direccion direccion){
        return direccionService.registrar(direccion);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    Direccion editar(@RequestBody Direccion direccion){
        return direccionService.editar(direccion);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    void eliminar(@PathVariable long id){
        direccionService.eliminar(id);
    }
}
