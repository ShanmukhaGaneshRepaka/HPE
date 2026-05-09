package com.symphonize.service;

import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.symphonize.repository.EmployeeRepository;
import com.symphonize.dto.EmployeeDetailsDto;
import com.symphonize.dto.EmployeeRequestDto;
import com.symphonize.entity.Employee;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository repo;

	public void saveUser(EmployeeRequestDto dto) {
		
		EmployeeDetailsDto employeeDetails = dto.getEmployeeDetails();
		String xml = convertToXml(employeeDetails);
		Employee emp = new Employee();
		emp.setName(dto.getName());
		emp.setEmployeeDetails(xml);
		repo.save(emp);
	}

	// To convert java object to xml string
	public String convertToXml(Object obj) {

		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());

			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);

			return writer.toString();

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
		
		//To check whether employee is present or not in Database
		if (emp1.isPresent()) {
			Employee old = emp1.get();
			old.setName(emp.getName());
			repo.save(old);
			return "Updated employee with the id " + id;
		}
		return "No employee found with the id " + id;
	}

}
