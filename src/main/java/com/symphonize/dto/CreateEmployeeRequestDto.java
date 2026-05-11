package com.symphonize.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.symphonize.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateEmployeeRequestDto {

	private int id;
	
	@NotBlank(message = "Name is required")
	private String name;
	
	@Valid
	@NotNull(message = "Employee details are required")
	private EmployeeDetailsDto employeeDetails;
    
	@NotNull(message = "Role is required")
	private Role role;

}
