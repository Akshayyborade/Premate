package com.Premate.Exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String resourceName;
private String fieldName;
private int fieldValue;
private String stringValue;
public ResourceNotFoundException(String resourceName, String fieldName, int fieldValue) {
	super("%s not found with %s : %s ".formatted(resourceName, fieldName, fieldValue));
	this.resourceName = resourceName;
	this.fieldName = fieldName;
	this.fieldValue = fieldValue;
}
public ResourceNotFoundException(String resourceName2, String fieldName2, String email) {
	// TODO Auto-generated constructor stub
	super("%s not found with %s : %s ".formatted(resourceName2, fieldName2, email));
	this.resourceName=resourceName2;
	this.fieldName=fieldName2;
	this.stringValue=email;
	
}
public ResourceNotFoundException(String stringValue) {
	super();
	this.stringValue = stringValue;
}



}
