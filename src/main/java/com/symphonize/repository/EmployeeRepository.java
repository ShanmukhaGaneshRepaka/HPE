package com.symphonize.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.symphonize.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	boolean existsByEmail(String email);

	@Query(value = "CALL get_employees_by_department(:deptName)", nativeQuery = true)
	List<Employee> getEmployeesByDepartment(@Param("deptName") String deptName);

}
