package com.Premate.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {
    private final int status;
    private final String message;
    private final Object data;
  // Optional field for additional data
	public ApiResponse(String message, Object data) {
		super();
		this.status = 0;
		this.message = message;
		this.data = data;
	}

    // Constructor, getters, setters
}
