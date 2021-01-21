package com.ktg.usuarioSpring.controllers;

import com.ktg.usuarioSpring.model.entity.Estado;
import com.ktg.usuarioSpring.model.entity.Municipio;
import com.ktg.usuarioSpring.services.estado.IEstadoService;
import com.ktg.usuarioSpring.services.estado.IMunicipioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin()
@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private IEstadoService estadoService;

    @Autowired
    private IMunicipioService municipioService;

    @GetMapping
    public List<Estado> estados(){
        return estadoService.findAll();
    }

    @RequestMapping(value = "/municipios", method = RequestMethod.GET)
    ResponseEntity<?> getAll(){
        List<Municipio> municipios = municipioService.findAll();
        Map<String, Object> response = new HashMap<>();
        if (municipios.size() == 0){
            response.put("mensaje", "Aún no hay datos por mostrar.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<List<Municipio>>(municipios, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<?> getById(@PathVariable int id){
        List<Municipio> municipio = municipioService.findAllEstados(id);
        Map<String, Object> response = new HashMap<>();
        if (municipio == null){
            response.put("error", "No existe el estado con id " + id);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<List<Municipio>>(municipio, HttpStatus.OK);
        }
    }
}
