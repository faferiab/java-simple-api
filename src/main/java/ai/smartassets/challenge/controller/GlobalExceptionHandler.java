package ai.smartassets.challenge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ai.smartassets.challenge.exception.NotFoundFieldException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = NotFoundFieldException.class)
    public ResponseEntity<?> handleException(NotFoundFieldException ex) {
        return new ResponseEntity<>(new ErrorResponseEntity(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { HttpMessageNotReadableException.class, MissingServletRequestParameterException.class })
    public ResponseEntity<?> handleException2(Exception ex) {
        return new ResponseEntity<>(new ErrorResponseEntity("Request Error"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponseEntity("Internal Error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    class ErrorResponseEntity {
        private String error;

        public ErrorResponseEntity(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}
