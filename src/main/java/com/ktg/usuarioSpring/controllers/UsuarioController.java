package com.ktg.usuarioSpring.controllers;

import com.ktg.usuarioSpring.model.entity.Usuario;
import com.ktg.usuarioSpring.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Indica que es un servicio REST
@RestController
//Direccion url por la que se va a consultar
@RequestMapping("users")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    //Método para obtener todos los usuarios
    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<Usuario> getUsuarios(){
        return usuarioService.getUsuarios();
    }

    //Método para obtener un usuario por su id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<?> getUsuarioById(@PathVariable long id){
        Usuario user = usuarioService.getUsuarioById(id);
        Map<String, Object> resp = new HashMap<>();

        if(user == null){
            resp.put("mensaje", "El usuario con id: " + id +" no existe.");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }

    //Método para insertar un usuario
    @RequestMapping(value = "/", method = RequestMethod.POST)
    Usuario registrar(@Valid @RequestBody Usuario user, BindingResult respuesta){
        return usuarioService.registrar(user, respuesta);
    }

    //Método para editar un usuario
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    Usuario editar(@Valid @RequestBody Usuario user, BindingResult respuesta){
        return usuarioService.editar(user, respuesta);
    }

    //Método para eliminar un usuario
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> eliminar(@PathVariable long id){
        Usuario user = usuarioService.getUsuarioById(id);
        Map<String, Object> resp = new HashMap<>();

        if(user == null){
            resp.put("mensaje", "El usuario con id: " + id +" no existe.");
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }
        usuarioService.eliminar(id);
        resp.put("mensaje", "Usuario con id: " + id + " eliminado.");
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
    }
}
