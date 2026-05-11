package com.symphonize.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
	public ResponseEntity<ApiResponse<?>> createEmployee(@Valid @RequestBody EmployeeRequestDto e,
			BindingResult bindingResult) {

		// Validation handling
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			ApiResponse<List<String>> response = new ApiResponse<>("FAILED", "Validation Failed",null, errors);
			return ResponseEntity.badRequest().body(response);
		}

		try {

			EmployeeResponseDto savedEmployee = employeeService.saveUser(e);
			ApiResponse<EmployeeResponseDto> response = new ApiResponse<>("SUCCESS", "Employee Created Successfully",
					savedEmployee,null);
			return ResponseEntity.ok(response);

		} catch (Exception ex) {
			ApiResponse<String> response = new ApiResponse<>("FAILED", ex.getMessage(), null,List.of(ex.getMessage()));
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
