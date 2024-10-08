
package com.springboot.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
//The @ControllerAdvice annotation is used to apply global exception handling.
@ControllerAdvice
public class GlobalExceptionHandler {
	
    // The @ExceptionHandler annotation catches specific exceptions (ClientNotFoundException in this case).
@ExceptionHandler(ClientNotFoundException.class)
public ResponseEntity<String> handleClientNotFoundException(ClientNotFoundException ex){
	// Provide a custom response when ClientNotFoundException occurs.
	String errorMessage="Client not found" + ex.getMessage();
	 // Return an HTTP status 404 (Not Found) with the custom error message.
	return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
}
}
