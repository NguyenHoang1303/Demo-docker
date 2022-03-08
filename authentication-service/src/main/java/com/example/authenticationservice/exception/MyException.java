package com.example.authenticationservice.exception;


import com.example.authenticationservice.response.RESTResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
@RestControllerAdvice
public class MyException {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handlerNotFoundException(NotFoundException ex, WebRequest req) {
        return new ResponseEntity<>(new RESTResponse.SimpleError()
                .setCode(HttpStatus.NOT_FOUND.value())
                .setMessage(ex.getMessage())
                .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handlerNotFoundException(BadRequestException ex, WebRequest req) {
        return new ResponseEntity<>(new RESTResponse.SimpleError()
                .setCode(HttpStatus.BAD_REQUEST.value())
                .setMessage(ex.getMessage())
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity handlerConflictException(ConflictException ex, WebRequest req) {
        return new ResponseEntity<>(new RESTResponse.SimpleError()
                .setCode(HttpStatus.CONFLICT.value())
                .setMessage(ex.getMessage())
                .build(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity handlerUnauthorizedException(UnauthorizedException ex, WebRequest req) {
        return new ResponseEntity<>(new RESTResponse.SimpleError()
                .setCode(HttpStatus.UNAUTHORIZED.value())
                .setMessage(ex.getMessage())
                .build(),
                HttpStatus.UNAUTHORIZED);
    }
}
