package com.symphonize.service;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.symphonize.dto.EmployeeDetailsDto;
import com.symphonize.dto.EmployeeRequestDto;
import com.symphonize.dto.EmployeeResponseDto;
import com.symphonize.entity.Employee;
import com.symphonize.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository repo;

	public EmployeeResponseDto saveUser(EmployeeRequestDto dto) {

		EmployeeDetailsDto employeeDetails = dto.getEmployeeDetails();

		// Conversion java Object to xml String
		String xml = convertToXml(employeeDetails);

		Employee emp = new Employee();
		emp.setName(dto.getName());
		emp.setEmployeeDetails(xml);
		emp.setRole(dto.getRole());

		// Save in DB
		Employee savedEmployee = repo.save(emp);

		// Prepare response DTO
		EmployeeResponseDto response = new EmployeeResponseDto();
		response.setId(savedEmployee.getId());
		response.setName(savedEmployee.getName());
		response.setRole(savedEmployee.getRole());
		response.setEmployeeDetails(employeeDetails);
		return response;
	}

	// To convert java object to xml string
	public String convertToXml(Object obj) {

		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());

			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// To remove XML header
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);

			return writer.toString();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// To convert XML String to Java Object
	public EmployeeDetailsDto convertXmlToObject(String xml) {

		try {

			JAXBContext context = JAXBContext.newInstance(EmployeeDetailsDto.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			return (EmployeeDetailsDto) unmarshaller.unmarshal(reader);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// To get single employee
	public Optional<Employee> getEmployeeById(int id) {
		Optional<Employee> employee = repo.findById(id);
		return employee;
	}

	// To get all employees
	public List<Employee> getAllEmployees() {
		return repo.findAll();
	}

	// To delete an employee
	public String deleteEmployee(int id) {
		if (repo.existsById(id)) {
			repo.deleteById(id);
			return "Employee deleted sucessfully";
		}
		return "Employee Not found with the id " + id;
	}

	// To update an employee
	public String updateEmployee(int id, Employee emp) {
		Optional<Employee> emp1 = repo.findById(id);

		// To check whether employee is present or not in Database
		if (emp1.isPresent()) {
			Employee old = emp1.get();
			old.setName(emp.getName());
			repo.save(old);
			return "Updated employee with the id " + id;
		}
		return "No employee found with the id " + id;
	}

}
