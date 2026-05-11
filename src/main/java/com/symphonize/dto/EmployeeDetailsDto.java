package com.symphonize.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement
@Data
@NoArgsConstructor
public class EmployeeDetailsDto {
	
	@NotNull(message = "Salary is required")
	private int employeeSalary;
	
	@NotBlank(message = "City is required")
	private String city;
	
	@NotBlank(message="department is required")
	private String department;
}
