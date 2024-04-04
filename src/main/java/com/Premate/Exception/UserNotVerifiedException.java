package com.Premate.Exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserNotVerifiedException extends InternalAuthenticationServiceException {
 private String msg;

public UserNotVerifiedException(String message, Throwable cause) {
	super(message, cause);
	// TODO Auto-generated constructor stub
}

public UserNotVerifiedException(String message) {
	super(message);
	// TODO Auto-generated constructor stub
}
 
}
