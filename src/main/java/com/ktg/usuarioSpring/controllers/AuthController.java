package com.ktg.usuarioSpring.controllers;

import com.ktg.usuarioSpring.model.entity.Direccion;
import com.ktg.usuarioSpring.payload.request.LoginRequest;
import com.ktg.usuarioSpring.payload.response.JwtResponse;
import com.ktg.usuarioSpring.payload.response.MessageResponse;
import com.ktg.usuarioSpring.security.jwt.JwtUtils;
import com.ktg.usuarioSpring.security.services.UserDetailsImpl;
import com.ktg.usuarioSpring.services.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin()
@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    DireccionService direccionService;

    @Autowired
    PasswordEncoder encoder;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> response = new HashMap<>();
        if(loginRequest.getCorreo().isEmpty() || loginRequest.getPassword().isEmpty()){
            if (loginRequest.getCorreo().isEmpty()){
                response.put("correo", "El correo no debe estar vacio.");
            }
            if (loginRequest.getPassword().isEmpty()){
                response.put("contraseña", "La contraseña no debe estar vacia.");
            }
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getCorreo(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getCorreo(),
                userDetails.getNombre(),
                userDetails.getApellido()
                ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Direccion direccion) {
        /*if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }*/

        // Create new user's account
        String pass = encoder.encode(direccion.getUsuario().getPassword());
        direccion.getUsuario().setPassword(pass);
        Direccion dir = (Direccion) direccionService.registrar(direccion);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
