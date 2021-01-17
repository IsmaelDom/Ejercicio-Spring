package com.ktg.usuarioSpring.config.exceptions;

import lombok.extern.java.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.logging.Level;

@ControllerAdvice
@Log
public class ExceptionConfig {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {

        // get spring errors
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        // convert errors to standard string
        StringBuilder errorMessage = new StringBuilder();
        fieldErrors.forEach(f -> errorMessage.append(f.getDefaultMessage() +  " "));
        log.log(Level.SEVERE, "#### Errores-Metodo MethodArgumentNotValidException: " + fieldErrors.iterator().next().getDefaultMessage());

        // return error info object with standard json
        ErrorInfo errorInfo = new ErrorInfo(errorMessage.toString(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorInfo> MethodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException e) {

        // get spring errors
        String result = e.getMessage();

        // convert errors to standard string
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("El campo " + e.getName() + " no puede ser " + e.getValue());
        log.log(Level.SEVERE, "#### Error-Metodo MethodArgumentTypeMismatchException: " + result);
        log.log(Level.SEVERE, "#### Error-Causa más especifica: " + e.getCause());
        log.log(Level.SEVERE, "#### Error el " + e.getName() + " debe ser de tipo: " + e.getRequiredType());

        // return error info object with standard json
        ErrorInfo errorInfo = new ErrorInfo(errorMessage.toString(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DuplicateKeyException.class,
                        HttpRequestMethodNotSupportedException.class,
                        HttpMessageNotReadableException.class
                        })
    public ResponseEntity<ErrorInfo> DuplicateKeyException(HttpServletRequest request, Exception e) {

        // get spring errors
        String result = e.getMessage();

        // convert errors to standard string
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Error: Ha ocurrido un error por favor revise el log para más detalle: " + result);
        log.log(Level.SEVERE, "#### Error-Método DuplicateKeyException: " + result);
        log.log(Level.SEVERE, "#### Error-Causa: " + e.getCause());

        // return error info object with standard json
        ErrorInfo errorInfo = new ErrorInfo(errorMessage.toString(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataAccessException.class, HttpClientErrorException.class})
    public ResponseEntity<ErrorInfo> DataAccessException(HttpServletRequest request, Exception e) {

        // get spring errors
        String result = e.getMessage();

        // convert errors to standard string
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Error: " + e.getCause().getMessage() + ". Causa: " + e.getCause());
        log.log(Level.SEVERE, "#### Error-Método DataAccessException: " + result);
        log.log(Level.SEVERE, "#### Error-Causa más especifica: " + e.getLocalizedMessage());

        // return error info object with standard json
        ErrorInfo errorInfo = new ErrorInfo(errorMessage.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({HttpClientErrorException.Unauthorized.class,
                        AccessDeniedException.class})
    public ResponseEntity<ErrorInfo> Unauthorized(HttpServletRequest request, Exception e) {
        // convert errors to standard string
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Error: No esta autorizado para esta acción");
        log.log(Level.SEVERE, "#### Error-Método Unauthorized: De tipo Unauthorized o Acceso denegado");
        log.log(Level.SEVERE, "#### Error: No esta autorizado o no tiene privilegios para esta acción");

        // return error info object with standard json
        ErrorInfo errorInfo = new ErrorInfo(errorMessage.toString(), HttpStatus.UNAUTHORIZED.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorInfo> AuthenticationException(HttpServletRequest request, Exception e) {

        // get spring errors
        String result = e.getMessage();

        // convert errors to standard string
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Contraseña o correo incorrecto");
        log.log(Level.SEVERE, "#### Error-Método AuthenticationException: " + result);
        log.log(Level.SEVERE, "#### Error-Causa más especifica: " + e.getCause());
        log.log(Level.SEVERE, "#### Error de credenciales: Contraseña o correo incorrecto");

        // return error info object with standard json
        ErrorInfo errorInfo = new ErrorInfo(errorMessage.toString(), HttpStatus.UNAUTHORIZED.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> Exception(HttpServletRequest request, Exception e) {

        // get spring errors
        String result = e.getMessage();

        // convert errors to standard string
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Error: " + e.getCause());
        log.log(Level.SEVERE, "#### Error-Método Exception excepción de tipo Exception: " + result);
        log.log(Level.SEVERE, "#### Error-Causa más especifica: " + e.getCause());
        log.log(Level.SEVERE, "#### Error Localización: " + e.getLocalizedMessage());

        // return error info object with standard json
        ErrorInfo errorInfo = new ErrorInfo(errorMessage.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
