package com.symphonize.dto;

import javax.validation.constraints.NotNull;

import com.symphonize.enums.Role;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class UpdateEmployeeRequestDto {
	@NotNull(message="id is required to delete user")
	private int id;
	private String name;
	private String email;
	private EmployeeDetailsDto employeeDetails;
	private Role role;

}
