package com.symphonize.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement
@Data
@NoArgsConstructor
public class EmployeeDetailsDto {
	
	@Min(value = 1, message = "Salary must be greater than 0")
	@NotNull(message = "Salary is required")
	private double employeeSalary;
	
	@NotBlank(message = "City is required")
	private String city;
	
	@NotBlank(message="department is required")
	private String department;
}
