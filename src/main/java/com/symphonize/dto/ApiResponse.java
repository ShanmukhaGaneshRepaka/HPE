package com.symphonize.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private String status;
    private int statusCode;
    private String message;
    private T data;
    private List<String> errors;

}