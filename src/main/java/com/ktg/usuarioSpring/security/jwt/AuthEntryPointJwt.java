package com.ktg.usuarioSpring.security.jwt;

import lombok.extern.java.Log;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;

@Log
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    //Detecta si hay un error de autenticación.
    //Este método se activará cada vez que un usuario no autenticado solicite un recurso HTTP seguro.
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.log(Level.SEVERE,"No tiene autorización, error: " + e.getMessage());
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}
