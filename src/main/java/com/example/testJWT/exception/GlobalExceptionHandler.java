package com.example.testJWT.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.testJWT.dto.DetailError;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DetailError> manejarResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
        DetailError detailError = new DetailError(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(detailError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAppException.class)
    public ResponseEntity<DetailError> manejarBlogAppException(BlogAppException exception, WebRequest webRequest){
        DetailError detailError = new DetailError(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(detailError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DetailError> manejarGlobalException(Exception exception, WebRequest webRequest){
        DetailError detailError = new DetailError(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(detailError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String nombreCampo = ((FieldError)error).getField();
            String mensaje = error.getDefaultMessage();

            errors.put(nombreCampo, mensaje);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
