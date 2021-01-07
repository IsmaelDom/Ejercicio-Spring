package com.ktg.usuarioSpring.config.exceptions;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
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
        log.log(Level.SEVERE, "#### Errores-Clase Exception: " + fieldErrors.iterator().next().getDefaultMessage());

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
        errorMessage.append("Error el " + e.getName() + " no puede ser '" + e.getValue() + "' ");
        log.log(Level.SEVERE, "#### Error-Clase Exception: " + result);
        log.log(Level.SEVERE, "#### Error en el método: " + e.getParameter() + ". Causa: " + e.getCause());

        // return error info object with standard json
        ErrorInfo errorInfo = new ErrorInfo(errorMessage.toString(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
