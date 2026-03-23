package com.example.library.controller;

import com.example.library.response.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ControllerExceptionHandler {
    static Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        logger.error(e.getMessage(), e);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());

        if (e instanceof EntityNotFoundException) {
            errorResponse.setErrorCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if (e instanceof HttpMessageNotReadableException) {
            errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
