package com.symphonize.dto;

import com.symphonize.enums.Role;

import lombok.Data;

@Data
public class EmployeeResponseDto {
	private int id;
	private String name;
	private String email;
	private Role role;
	private EmployeeDetailsDto employeeDetails;
}
