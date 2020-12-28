package com.ktg.usuarioSpring.controllers;

import com.ktg.usuarioSpring.model.DireccionUserVO;
import com.ktg.usuarioSpring.model.entity.Direccion;
import com.ktg.usuarioSpring.services.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

import java.util.List;

//Anotación que indica que es un servicio REST
@RestController
//URL
@RequestMapping("direccion")

public class DireccionController {

    @Autowired
    DireccionService direccionService;

    /*@InitBinder
    public void binder(WebDataBinder bind){
        System.out.println("Entro a el metodo binder");
        StringTrimmerEditor espacios = new StringTrimmerEditor(true);
        bind.registerCustomEditor(String.class, espacios);
    }*/

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    //@RequestMapping(value = "/", method = RequestMethod.GET)
    List<DireccionUserVO> getAll(){
        return direccionService.getAll();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    Direccion getDireccionById(@PathVariable long id){
        return direccionService.getDireccionById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    DireccionUserVO getFullDireccionById(@PathVariable long id){
        return direccionService.getFullDireccion(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    Direccion registrar(@Valid @RequestBody Direccion direccion, BindingResult resValida){
        return direccionService.registrar(direccion, resValida);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    Direccion editar(@Valid @RequestBody Direccion direccion, BindingResult resValida){
        return direccionService.editar(direccion, resValida);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    void eliminar(@PathVariable long id){
        direccionService.eliminar(id);
    }
}
