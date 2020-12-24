package com.ktg.usuarioSpring.controllers;

import com.ktg.usuarioSpring.model.entity.Usuario;
import com.ktg.usuarioSpring.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Indica que es un servicio REST
@RestController
//Direccion url por la que se va a consultar
@RequestMapping("user")
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
    Usuario getUsuarioById(@PathVariable long id){
        return usuarioService.getUsuarioById(id);
    }

    //Método para insertar un usuario
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    Usuario registrar(@RequestBody Usuario user){
        return usuarioService.registrar(user);
    }

    //Método para editar un usuario
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    Usuario editar(@RequestBody Usuario user){
        return usuarioService.editar(user);
    }

    //Método para eliminar un usuario
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    void eliminar(@PathVariable long id){
        usuarioService.eliminar(id);
    }
}
