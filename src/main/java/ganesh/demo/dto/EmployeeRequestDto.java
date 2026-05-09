package ganesh.demo.dto;

import lombok.Data;


public class EmployeeRequestDto {
	
	private int id;
	
	private String name;
	
	private EmployeeDetailsDto employeeDetails;

	public EmployeeRequestDto(int id, String name, EmployeeDetailsDto employeeDetails) {
		super();
		this.id = id;
		this.name = name;
		this.employeeDetails = employeeDetails;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EmployeeDetailsDto getEmployeeDetails() {
		return employeeDetails;
	}

	public void setEmployeeDetails(EmployeeDetailsDto employeeDetails) {
		this.employeeDetails = employeeDetails;
	}

}
