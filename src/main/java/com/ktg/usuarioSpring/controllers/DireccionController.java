package com.ktg.usuarioSpring.controllers;

import com.ktg.usuarioSpring.model.entity.Direccion;
import com.ktg.usuarioSpring.services.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Anotación que indica que es un servicio REST
@RestController
//Direccion url por la que se va a consultar
@RequestMapping("direcciones")
//@CrossOrigin(origins = {"http://localhost:8081"})
@CrossOrigin
public class DireccionController {

    //Genera la inyección de dependencia de un Objeto
    @Autowired
    DireccionService direccionService;

    @GetMapping
    //@RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<?> getAll(){
        List<UsuariosDTO> direcciones = direccionService.getAll();
        Map<String, Object> response = new HashMap<>();
        if (direcciones.size() == 0){
            response.put("mensaje", "Aún no hay datos por mostrar.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<List<UsuariosDTO>>(direcciones, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    ResponseEntity<?> getDireccionById(@PathVariable long id){
        Direccion direccion = direccionService.getDireccionById(id);
        Map<String, Object> response = new HashMap<>();

        if(direccion == null){
            //Se agrega un elemento a el Map
            response.put("mensaje", "El usuario con id: " + id +" no existe.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Direccion>(direccion, HttpStatus.OK);
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
    ResponseEntity<?> registrar(@RequestBody Direccion direccion){
        Map<String, Object> dir =  direccionService.registrar(direccion);

        if(dir.equals("usuario") || dir.containsKey("usuario")){
            return new ResponseEntity<Map<String, Object>>(dir, HttpStatus.CREATED);
        }
        return new ResponseEntity<Map<String, Object>>(dir, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> editar(@RequestBody Direccion direccion){
        DireccionUserDTO obtenerDireccion = direccionService.getFullDireccion(direccion.getId());
        Map<String, Object> response = new HashMap<>();
        if (obtenerDireccion == null){
            response.put("mensaje", "El usuario con id " + direccion.getId() + " no existe.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        Map<String, Object> dir = direccionService.editar(direccion);

        if(dir.containsKey("usuario")){
            return new ResponseEntity<Map<String, Object>>(dir, HttpStatus.OK);
        }

        return new ResponseEntity<Map<String, Object>>(dir, HttpStatus.BAD_REQUEST);
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
