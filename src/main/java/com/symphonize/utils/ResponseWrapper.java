package com.symphonize.utils;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.symphonize.dto.ApiResponse;

public class ResponseWrapper {

	public static <T> ResponseEntity<ApiResponse<?>> success(String message, T data) {
		ApiResponse<T> response = new ApiResponse<>("SUCCESS", message, data, null);
		return ResponseEntity.ok(response);
	}

	public static <T> ResponseEntity<ApiResponse<?>> error(String message, List<String> errors) {
		ApiResponse<T> response = new ApiResponse<>("FAILED", message, null, errors);
		return ResponseEntity.status(500).body(response);
	}

	public static ResponseEntity<ApiResponse<?>> requestValidator(String message, List<String> errorMessages) {
		ApiResponse<?> response = new ApiResponse<>("FAILED", message, null, errorMessages);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

}
