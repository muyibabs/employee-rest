package com.muyi.lukman.dao;

import com.muyi.lukman.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeDao extends JpaRepository<Employee, Long> {
    Employee findByEmployeeId(String employeeId);

    @Query("SELECT e FROM Employee e WHERE e.employeeId = ?1")
    Employee findByEmployeeId2(String employeeId);
}
