package ganesh.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository repo;
	public void saveUser(Employee e) {
		
		repo.save(e);
	}
	public Optional<Employee> getEmployeeById(int id) {
		
	Optional<Employee> employee = 	repo.findById(id);
	
	return employee;
		
	}
	public List<Employee> getApllEmployees() {

		
		return repo.findAll();
	}
	public String deleteEmployee(int id) {
		if(repo.existsById(id)) {
			repo.deleteById(id);
			return "Employee deleted sucessfully";
		}
		
		
		return "Employee Not found with the id "+id;
		
	}
	public String updateEmployee(int id, Employee emp) {
		
		
	Optional<Employee> emp1 = 	repo.findById(id);
	
	if(emp1.isPresent()) {
		Employee old = emp1.get();
		old.setName(emp.getName());
		repo.save(old);
		return "Updated employee with the id "+id;
	}
	return "No employe found with the id "+ id;
	}

}
