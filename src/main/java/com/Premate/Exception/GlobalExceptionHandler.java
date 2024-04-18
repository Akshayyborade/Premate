package com.Premate.Exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.Premate.util.ApiResponse;


@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String massage= ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(massage,false);
		return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);	
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, List<String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, List<String>> errors = new HashMap<>();
	    ex.getBindingResult().getFieldErrors().forEach((error) -> {
	        String field = error.getField();
	        List<String> fieldErrors = errors.getOrDefault(field, new ArrayList<>());
	        fieldErrors.add(error.getDefaultMessage());
	        errors.put(field, fieldErrors);
	    });

	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

}
