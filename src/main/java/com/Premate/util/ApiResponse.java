package com.Premate.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int status;         // HTTP status code or custom code
    private String message;     // Response message
    private T data;             // Generic type for data payload
    private byte[] byteData;    // Optional field for byte data

    // Constructor for message and data
    public ApiResponse(String message, T data) {
        this.status = 0;        // Default status (can be updated if needed)
        this.message = message;
        this.data = data;
        this.byteData = null;
    }

    // Constructor for message and byte data
    public ApiResponse(String message, byte[] byteData) {
        this.status = 0;        // Default status (can be updated if needed)
        this.message = message;
        this.data = null;
        this.byteData = byteData;
    }

    // Constructor for status and message only
    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
        this.byteData = null;
    }

	public ApiResponse(int status, String message, T data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}
   
}
