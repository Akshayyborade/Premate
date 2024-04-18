package com.Premate.util;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseBuilder {
    private int status = HttpStatus.OK.value();
    private String message;
    private Object data;
    private byte[] byteData;
    // Added method to set message
    public ApiResponseBuilder setMessage(String message) {
        this.message = message;
        return this; // Allow chaining of methods
    }

    // Methods to set status and data (assuming they're not implemented yet)
    public ApiResponseBuilder setStatus(HttpStatus status) {
        this.status = status.value();
        return this;
    }

    public ApiResponseBuilder setData(Object data) {
        this.data = data;
        return this;
    }

    // Method to build the ApiResponse object
    public ApiResponse build() {
        return new ApiResponse(status, message, data, byteData );
    }

	
}
