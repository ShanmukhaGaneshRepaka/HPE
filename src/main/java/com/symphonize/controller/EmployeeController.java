package com.symphonize.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/employee")
@Tag(name = "Employee APIs", description = "Operations related to employees")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	// To create employee
	@PostMapping()
	@Operation(summary = "Create new employee", description = "Returns employee details with proper response structure")
	public ResponseEntity<ApiResponse<?>> createEmployee(@Valid @RequestBody CreateEmployeeRequestDto e,
			BindingResult bindingResult) {
		/*
		 * Validation handling @BindingResult triggers with @Valid then spring checks
		 * and collect all messages and converting them as list.
		 */
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			return ResponseWrapper.requestValidator("Validation Failed", HttpStatus.BAD_REQUEST.value(), errors);
		}

		try {
			EmployeeResponseDto savedEmployee = employeeService.createEmployee(e);
			return ResponseWrapper.success("Employee Created Sucessfully", HttpStatus.CREATED.value(), savedEmployee);

		} catch (Exception ex) {
			return ResponseWrapper.error(ex.getMessage(), HttpStatus.OK.value(), List.of(ex.getMessage()));
		}
	}

	// Get employee by Id
	@GetMapping("/{id}")
	@Operation(summary = "Fetch employee by id", description = "Returns employee details based on employee id")
	public ResponseEntity<ApiResponse<?>> fetchEmployeeById(@PathVariable int id) {

		if (id <= 0) {
			return ResponseWrapper.error("Inavlid id", HttpStatus.BAD_REQUEST.value(), null);
		}

		try {
			EmployeeResponseDto employeedto = employeeService.getEmployeeById(id);
			return ResponseWrapper.success("Employee Fetched Sucessfully", HttpStatus.OK.value(), employeedto);

		} catch (RuntimeException ex) {

			return ResponseWrapper.error(ex.getMessage(), HttpStatus.NOT_FOUND.value(), null);

		} catch (Exception ex) {

			return ResponseWrapper.error("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
		}
	}

	// To get All employees
	@GetMapping
	@Operation(summary = "To Fetch All employees", description = "Returns all employees from database")
	public ResponseEntity<ApiResponse<?>> fetchAllEmployees() {

		try {
			List<EmployeeResponseDto> employees = employeeService.getAllEmployees();
			return ResponseWrapper.success("All employees Fetched sucessfully", HttpStatus.OK.value(), employees);
		} catch (RuntimeException ex) {
			return ResponseWrapper.success(ex.getMessage(), HttpStatus.OK.value(), null);
		} catch (Exception ex) {
			return ResponseWrapper.error("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
		}
	}

	// For update employee used patch mapping because user may want to update
	// partial data then no need to give full request.
	@PatchMapping("{id}")
	@Operation(summary = "Update employee  by id", description = "Returns employee updated details with proper response structure and data")
	public ResponseEntity<ApiResponse<?>> updateEmployee(@PathVariable int id,
			@Valid @RequestBody UpdateEmployeeRequestDto e) {

		if (id <= 0) {
			return ResponseWrapper.error("Invalid id", HttpStatus.BAD_REQUEST.value(), null);
		}
		try {
			UpdateEmployeeResponseDto updated = employeeService.updateEmployee(id, e);
			return ResponseWrapper.success("Employee updated successfully", HttpStatus.OK.value(), updated);
		} catch (Exception e1) {
			return ResponseWrapper.error(e1.getMessage(), HttpStatus.OK.value(), null);
		}

	}

	// deletes employee byId
	@DeleteMapping("{id}")
	@Operation(summary = "Delete employee by id", description = "It deletes employee by Id and returns emloyee deleted sucessfully with proper response structure")
	public ResponseEntity<ApiResponse<?>> deleteEmployee(@PathVariable int id) {

		if (id <= 0) {
			return ResponseWrapper.error("Invalid id", HttpStatus.BAD_REQUEST.value(), null);
		}

		try {
			employeeService.deleteEmployee(id);
			return ResponseWrapper.success("Employee deleted successfully", HttpStatus.OK.value(), null);

		} catch (Exception e) {
			return ResponseWrapper.error(e.getMessage(), HttpStatus.OK.value(), null);

		}
	}
}
