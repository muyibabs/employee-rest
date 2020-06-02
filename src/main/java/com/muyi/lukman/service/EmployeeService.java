package com.muyi.lukman.service;

import com.muyi.lukman.dao.EmployeeDao;
import com.muyi.lukman.model.Employee;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    List<Employee> allEmployees = new ArrayList<>();
    RestService restService;
    EmployeeDao employeeDao;

    public EmployeeService(RestService restService, EmployeeDao employeeDao) {
        this.restService = restService;
        this.employeeDao = employeeDao;
    }

    @PostConstruct
    public void initMe(){
        Employee e1 = new Employee("Emp001","Muyiron", 22);
        Employee e2 = new Employee("Emp002","Folasade", 52);
        allEmployees.add(e1);
        allEmployees.add(e2);

    }

    public List<Employee> getAllEmployees(){
        return allEmployees;
    }

    public Employee getEmployeeById(String empId){
        return allEmployees.stream()
                .filter(employee -> employee.getEmployeeId().equals(empId))
                .findAny().get();
    }

    public Object getSpringFact(){
        return restService.getRandomFact();
    }

    public com.muyi.lukman.model.entity.Employee createEmployee(com.muyi.lukman.model.entity.Employee employee){
        return employeeDao.save(employee);
    }

    public com.muyi.lukman.model.entity.Employee updateEmployee(com.muyi.lukman.model.entity.Employee employee){
        return employeeDao.save(employee);
    }

    public com.muyi.lukman.model.entity.Employee findById(Long id){
        return employeeDao.findById(id).get();
    }

    public void deleteEmployee(Long id){
        com.muyi.lukman.model.entity.Employee employee = this.findById(id);
        employeeDao.delete(employee);
    }

    public List<com.muyi.lukman.model.entity.Employee> findAll(){
        return employeeDao.findAll();
    }

    public com.muyi.lukman.model.entity.Employee findByEmployeeId(String employeeId){
        return employeeDao.findByEmployeeId(employeeId);
    }
}
