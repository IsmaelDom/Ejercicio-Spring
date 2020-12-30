package com.ktg.usuarioSpring.controllers;

import com.ktg.usuarioSpring.model.entity.Direccion;
import com.ktg.usuarioSpring.services.DireccionService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Anotación que indica que es un servicio REST
@RestController
//Direccion url por la que se va a consultar
@RequestMapping("direcciones")
@Log
public class DireccionController {

    //Genera la inyección de dependencia de un Objeto
    @Autowired
    DireccionService direccionService;

    @GetMapping
    //@RequestMapping(value = "/", method = RequestMethod.GET)
    List<DireccionUserDTO> getAll(){
        return direccionService.getAll();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    Direccion getDireccionById(@PathVariable long id){
        return direccionService.getDireccionById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
     //ResponseEntity maneja respuestas HTTP
    ResponseEntity<?> getFullDireccionById(@PathVariable long id){
        DireccionUserDTO dir = direccionService.getFullDireccion(id);
        Map<String, Object> resp = new HashMap<>();

        if(dir == null){
            //Se agrega un elemento a el Map
            resp.put("mensaje", "El usuario con id: " + id +" no existe en la base de datos.");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<DireccionUserDTO>(dir, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    //Los posibles errores se almacenarán en el parámetro de tipo BindingResult(resValida)
    Direccion registrar(@Valid @RequestBody Direccion direccion, BindingResult resValida){
        return direccionService.registrar(direccion, resValida);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    Direccion editar(@Valid @RequestBody Direccion direccion, BindingResult resValida){
        return direccionService.editar(direccion, resValida);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    //ResponseEntity maneja respuestas HTTP
    ResponseEntity<?> eliminar(@PathVariable long id){
        DireccionUserDTO dir = direccionService.getFullDireccion(id);
        Map<String, Object> resp = new HashMap<>();
        if(dir == null){
            //Se añade un elemento a el Map
            resp.put("mensaje", "El usuario con id: " + id +" no existe.");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }
        direccionService.eliminar(id);
        resp.put("mensaje", "Usuario con id: " + id + " eliminado.");
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
    }
}
