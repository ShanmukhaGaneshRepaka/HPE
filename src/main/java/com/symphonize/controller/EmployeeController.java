package com.symphonize.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.symphonize.dto.ApiResponse;
import com.symphonize.dto.EmployeeRequestDto;
import com.symphonize.dto.EmployeeResponseDto;
import com.symphonize.entity.Employee;
import com.symphonize.service.EmployeeService;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	// To get All employees
	@GetMapping
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}

	// To create employee
	@PostMapping("create-employee")
	public ResponseEntity<ApiResponse<EmployeeResponseDto>> createEmployee(@Valid @RequestBody EmployeeRequestDto e) {

		try {

			EmployeeResponseDto savedEmployee = employeeService.saveUser(e);
			ApiResponse<EmployeeResponseDto> response = new ApiResponse<>(200, "Employee Created Successfully",
					savedEmployee);

			return ResponseEntity.ok(response);

		} catch (Exception ex) {
			ApiResponse<EmployeeResponseDto> response = new ApiResponse<>(500, ex.getMessage(), null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// Get employees by Id
	@GetMapping("/{id}")
	public Optional<Employee> getEmployeeById(@PathVariable int id) {
		return employeeService.getEmployeeById(id);
	}

	// delete employee byId
	@DeleteMapping("{id}")
	public String deleteEmployee(@PathVariable int id) {
		return employeeService.deleteEmployee(id);
	}

	// Update employee
	@PutMapping("{id}")
	public String updateEmployee(@PathVariable int id, @RequestBody Employee e) {
		return employeeService.updateEmployee(id, e);
	}

}
