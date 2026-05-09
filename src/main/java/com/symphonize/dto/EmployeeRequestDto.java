package com.symphonize.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeRequestDto {

	private int id;
	private String name;
	private EmployeeDetailsDto employeeDetails;

}
