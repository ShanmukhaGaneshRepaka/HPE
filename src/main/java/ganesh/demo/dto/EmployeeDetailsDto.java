package ganesh.demo.dto;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement
public class EmployeeDetailsDto {
	
	private int employeeSalary;
	
	private String city;
	
	
	public EmployeeDetailsDto() {
	}
	public EmployeeDetailsDto(int employeeSalary, String city, String department) {
		super();
		this.employeeSalary = employeeSalary;
		this.city = city;
		this.department = department;
	}

	public int getEmployeeSalary() {
		return employeeSalary;
	}

	@Override
	public String toString() {
		return "EmployeeDetailsDto [employeeSalary=" + employeeSalary + ", city=" + city + ", department=" + department
				+ "]";
	}

	public void setEmployeeSalary(int employeeSalary) {
		this.employeeSalary = employeeSalary;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	private String department ;

}
