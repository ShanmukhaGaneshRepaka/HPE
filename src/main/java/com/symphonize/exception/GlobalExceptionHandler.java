//package com.symphonize.exception;
//
//import java.util.List;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//
//import com.symphonize.dto.ApiResponse;
//import com.symphonize.utils.ResponseWrapper;
//@RestController
//public class GlobalExceptionHandler {
//	
//	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
//	public ResponseEntity<ApiResponse<?>> handleTypeMismatch(
//	        MethodArgumentTypeMismatchException ex) {
//
//	    return ResponseWrapper.requestValidator(
//	            "Validation Failed",
//	            HttpStatus.BAD_REQUEST.value(),
//	            List.of("Id must be a valid number"));
//	}
//
//}
