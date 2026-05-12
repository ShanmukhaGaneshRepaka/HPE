package com.symphonize.utils;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.symphonize.dto.ApiResponse;

public class ResponseWrapper {
	
	public static <T> ResponseEntity<ApiResponse<?>> success(String message, int statusCode, T data) {
		ApiResponse<T> response = new ApiResponse<>("SUCCESS", statusCode, message, data, null);
		return ResponseEntity.ok(response);
	}

	public static <T> ResponseEntity<ApiResponse<?>> error(String message, int statusCode, List<String> errors) {
		ApiResponse<T> response = new ApiResponse<>("FAILED", statusCode, message, null, errors);
		return ResponseEntity.status(statusCode).body(response);
	}

	public static ResponseEntity<ApiResponse<?>> requestValidator(String message, int statusCode,
			List<String> errorMessages) {
		ApiResponse<?> response = new ApiResponse<>("FAILED", statusCode, message, null, errorMessages);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
}
