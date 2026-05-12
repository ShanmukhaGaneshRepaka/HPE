package com.symphonize.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.symphonize.dto.ApiResponse;
import com.symphonize.dto.CreateEmployeeRequestDto;
import com.symphonize.dto.EmployeeResponseDto;
import com.symphonize.dto.UpdateEmployeeRequestDto;
import com.symphonize.dto.UpdateEmployeeResponseDto;
import com.symphonize.service.EmployeeService;
import com.symphonize.utils.ResponseWrapper;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	// To create employee
	@PostMapping("create-employee")
	public ResponseEntity<ApiResponse<?>> createEmployee(@Valid @RequestBody CreateEmployeeRequestDto e,
			BindingResult bindingResult) {

		// Validation handling @BindingResult triggers with @Valid then spring checks
		// and collect all messages and converting them as list.
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			return ResponseWrapper.requestValidator("Validation Failed", errors);
		}

		try {
			EmployeeResponseDto savedEmployee = employeeService.createEmployee(e);
			return ResponseWrapper.success("Employee Created Sucessfully", savedEmployee);

		} catch (Exception ex) {
			return ResponseWrapper.error("Failed to create Employee ", List.of(ex.getMessage()));
		}
	}

	// Get employee by Id
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<?>> fetchEmployeeById(@PathVariable int id) {

		EmployeeResponseDto employeedto = employeeService.getEmployeeById(id);
		// To check whether employee is exists or not
		if (employeedto == null) {
			return ResponseWrapper.success("Employee not found", Collections.emptyList());
		}
		return ResponseWrapper.success("Employee Fetched Sucessfully", employeedto);
	}

	// To get All employees
	@GetMapping
	public ResponseEntity<ApiResponse<?>> fetchAllEmployees() {
		List<EmployeeResponseDto> employees = employeeService.getAllEmployees();
		return ResponseWrapper.success("All employees Fetched sucessfully", employees);
	}

	// For update employee used patch mapping because user may want to update
	// partial data then no need to give full request.
	@PatchMapping("{id}")
	public ResponseEntity<ApiResponse<?>> updateEmployee(@PathVariable int id,
			@Valid @RequestBody UpdateEmployeeRequestDto e) {

		UpdateEmployeeResponseDto updated = employeeService.updateEmployee(id, e);

		return ResponseWrapper.success("Employee updated successfully", updated);
	}

	// deletes employee byId
	@DeleteMapping("{id}")
	public ResponseEntity<ApiResponse<?>> deleteEmployee(@PathVariable int id) {
		return ResponseWrapper.success("Employee deleted successfully", null);
	}
}
