package com.epam.booking.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(NonExistentUserException.class)
    public ResponseEntity<Map<String,String>> handleNonExistentUserException(NonExistentUserException e) {
        log.warn(e.toString());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body( Map.of("Error","Bad Request - Non Existent User Exception"));
    }

    @ExceptionHandler(NonExistentEventException.class)
    public ResponseEntity<Map<String,String>> handleNonExistentEventException(NonExistentEventException e) {
        log.warn(e.toString());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body( Map.of("Error","Bad Request - Non Existent Event Exception"));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleUserNotFoundException(UserNotFoundException e) {
        log.warn(e.toString());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error","User Not Found"));
    }


    @ExceptionHandler(UserNameTakenException.class)
    public ResponseEntity<Map<String,String>> handleUserNameTakenException(UserNameTakenException e) {
        log.warn(e.toString());
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("Error","User Name Taken"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        log.error(e.toString());
        return  ResponseEntity.internalServerError().build();
    }


}
