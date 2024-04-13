package org.example.repository;

import org.example.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Created by Suresh Stalin on 20 / Oct / 2020.
 */

public interface EmployeeRepository extends JpaRepository<Employee, Long>  {

        Employee findByEmployeeCode(String employeeCode);
}
