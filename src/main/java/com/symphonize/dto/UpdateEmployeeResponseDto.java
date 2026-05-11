package com.symphonize.dto;

import com.symphonize.enums.Role;

import lombok.Data;

@Data
public class UpdateEmployeeResponseDto {

	private String name;	
	private EmployeeDetailsDto employeeDetails;
	private Role role;
}
