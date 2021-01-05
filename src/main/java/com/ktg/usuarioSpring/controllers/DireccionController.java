package com.ktg.usuarioSpring.controllers;

import com.ktg.usuarioSpring.model.entity.Direccion;
import com.ktg.usuarioSpring.services.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Anotación que indica que es un servicio REST
@RestController
//Direccion url por la que se va a consultar
@RequestMapping("direcciones")
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
        DireccionUserDTO direccion = direccionService.getFullDireccion(id);
        Map<String, Object> response = new HashMap<>();

        if(direccion == null){
            //Se agrega un elemento a el Map
            response.put("mensaje", "El usuario con id: " + id +" no existe en la base de datos.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<DireccionUserDTO>(direccion, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    //Los posibles errores se almacenarán en el parámetro de tipo BindingResult(resValida)
    ResponseEntity<?> registrar(@Valid @RequestBody Direccion direccion, BindingResult validaRespuesta){
        Map<String, Object> response = new HashMap<>();
        if (validaRespuesta.hasErrors()){
            response.put("mensaje", "Por favor llene todos los campos.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }else{
            Direccion dir = direccionService.registrar(direccion, validaRespuesta);
            return new ResponseEntity<Direccion>(dir, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> editar(@Valid @RequestBody Direccion direccion, BindingResult validaRespuesta){
        DireccionUserDTO obtenerDireccion = direccionService.getFullDireccion(direccion.getId());
        Map<String, Object> response = new HashMap<>();
        if (obtenerDireccion == null){
            response.put("mensaje", "El usuario con id " + direccion.getId() + " no se puede editar.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        if (validaRespuesta.hasErrors()){
            response.put("mensaje", "Por favor llene todos los campos.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }else{
            Direccion dir = direccionService.editar(direccion, validaRespuesta);
            return new ResponseEntity<Direccion>(dir, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    //ResponseEntity maneja respuestas HTTP
    ResponseEntity<?> eliminar(@PathVariable long id){
        DireccionUserDTO direccion = direccionService.getFullDireccion(id);
        Map<String, Object> response = new HashMap<>();
        if(direccion == null){
            //Se añade un elemento a el Map
            response.put("mensaje", "El usuario con id: " + id +" no existe.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        direccionService.eliminar(id);
        response.put("mensaje", "Usuario con id: " + id + " eliminado.");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
