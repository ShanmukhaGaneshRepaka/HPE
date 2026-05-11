package com.symphonize.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.symphonize.dto.CreateEmployeeRequestDto;
import com.symphonize.dto.EmployeeDetailsDto;
import com.symphonize.dto.EmployeeResponseDto;
import com.symphonize.dto.UpdateEmployeeRequestDto;
import com.symphonize.dto.UpdateEmployeeResponseDto;
import com.symphonize.entity.Employee;
import com.symphonize.repository.EmployeeRepository;
import com.symphonize.utils.XmlUtil;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	public EmployeeResponseDto createEmployee(CreateEmployeeRequestDto dto) {

		EmployeeDetailsDto employeeDetails = dto.getEmployeeDetails();

		// Conversion java Object to xml String
		String xml = XmlUtil.convertToXml(employeeDetails);

		Employee emp = new Employee();
		emp.setName(dto.getName());
		emp.setEmployeeDetails(xml);
		emp.setRole(dto.getRole());

		// Save in DB
		Employee savedEmployee = employeeRepository.save(emp);

		// Prepare response DTO
		EmployeeResponseDto response = new EmployeeResponseDto();
		response.setName(savedEmployee.getName());
		response.setRole(savedEmployee.getRole());
		response.setEmployeeDetails(employeeDetails);
		return response;
	}

	// To get single employee
	public EmployeeResponseDto getEmployeeById(int id) {

		Employee emp = employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found with id " + id));

		EmployeeResponseDto empResponse = new EmployeeResponseDto();
		empResponse.setName(emp.getName());
		empResponse.setRole(emp.getRole());

		// Convert xml to java Object for Employee Details
		EmployeeDetailsDto employeeDetails = XmlUtil.convertXmlToObject(emp.getEmployeeDetails());
		empResponse.setEmployeeDetails(employeeDetails);
		return empResponse;

	}

	// To get all employees
	public List<EmployeeResponseDto> getAllEmployees() {
		List<Employee> allEmployees = employeeRepository.findAll();
		List<EmployeeResponseDto> responseList = new ArrayList<>();
		
		for (Employee emp : allEmployees) {
			EmployeeResponseDto dto = new EmployeeResponseDto();
			dto.setId(emp.getId());
			dto.setName(emp.getName());
			dto.setRole(emp.getRole());
			// XML → Object conversion
			EmployeeDetailsDto details = XmlUtil.convertXmlToObject(emp.getEmployeeDetails());
			dto.setEmployeeDetails(details);
			responseList.add(dto);
		}
		return responseList;
	}

	// To delete an employee
	public void deleteEmployee(int id) {
		// checking employee exists or not

		Employee employee = employeeRepository.findById(id)
				.orElseThrow();

		employeeRepository.delete(employee);
	}	

	// To update an employee
	public UpdateEmployeeResponseDto updateEmployee(int id, UpdateEmployeeRequestDto emp) {
		Employee old = employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found with id " + id));

		// To check whether employee is present or not in Database
		if (emp.getName() != null) {
			old.setName(emp.getName());
		}
		if (emp.getRole() != null) {
			old.setRole(emp.getRole());
		}
		// Convert XML → Object
		EmployeeDetailsDto existingDetails = XmlUtil.convertXmlToObject(old.getEmployeeDetails());

		// Apply PATCH updates
		if (emp.getEmployeeDetails() != null) {
			if (emp.getEmployeeDetails().getEmployeeSalary() != 0) {
				existingDetails.setEmployeeSalary(emp.getEmployeeDetails().getEmployeeSalary());
			}
			if (emp.getEmployeeDetails().getCity() != null) {
				existingDetails.setCity(emp.getEmployeeDetails().getCity());
			}
			if (emp.getEmployeeDetails().getDepartment() != null) {
				existingDetails.setDepartment(emp.getEmployeeDetails().getDepartment());
			}
		}
		// Convert Object → XML again
		String updatedXml = XmlUtil.convertToXml(existingDetails);
		old.setEmployeeDetails(updatedXml);
		Employee saved = employeeRepository.save(old);
		// Convert Entity → DTO
		UpdateEmployeeResponseDto response = new UpdateEmployeeResponseDto();
		response.setName(saved.getName());
		response.setRole(saved.getRole());
		response.setEmployeeDetails(existingDetails);
		return response;
	}

}
