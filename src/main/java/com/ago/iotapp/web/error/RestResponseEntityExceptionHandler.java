package com.ago.iotapp.web.error;

import com.ago.iotapp.web.service.exception.ItemAlreadyExists;
import com.ago.iotapp.web.service.exception.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage>ItemNotFoundException(ItemNotFoundException exception)
    {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(message.getStatus()).body(message);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorMessage>ItemAlreadyExistsException(ItemAlreadyExists exception)
    {
        ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT,exception.getMessage());
        return ResponseEntity.status(message.getStatus()).body(message);
    }
}
