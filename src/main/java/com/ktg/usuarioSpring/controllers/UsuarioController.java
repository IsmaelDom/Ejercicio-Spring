package com.ktg.usuarioSpring.controllers;

import com.ktg.usuarioSpring.model.entity.Usuario;
import com.ktg.usuarioSpring.services.UsuarioService;
import com.ktg.usuarioSpring.utils.JWTUtil;
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
@CrossOrigin
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private JWTUtil jwtUtil;

    //Método para obtener todos los usuarios
    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<Usuario> getUsuarios(){
        return usuarioService.getUsuarios();
    }

    //Método para obtener un usuario por su id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<?> getUsuarioById(@PathVariable long id){
        Usuario user = usuarioService.getUsuarioById(id);
        Map<String, Object> response = new HashMap<>();

        if(user == null){
            response.put("mensaje", "El usuario con id: " + id +" no existe.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
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
        Map<String, Object> response = new HashMap<>();

        if(user == null){
            response.put("mensaje", "El usuario con id: " + id +" no existe.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        usuarioService.eliminar(id);
        response.put("mensaje", "Usuario con id: " + id + " eliminado.");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    ResponseEntity<?> login(@RequestBody Usuario dto) {
        Usuario user = usuarioService.login(dto);

        Map<String, Object> result = new HashMap<>();
        if (user != null) {
            String token = jwtUtil.create(String.valueOf(user.getId()), user.getCorreo());
            result.put("token", token);
            result.put("user", user);
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }else{
            result.put("mensaje", "Usuario o Contraseña incorrecto");
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.NOT_FOUND);
        }
    }
}
