package com.muyi.lukman.controller;

import com.muyi.lukman.model.Employee;
import com.muyi.lukman.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( path ="/v1/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @GetMapping(path = "/{emplId}")
    //@ResponseStatus(HttpStatus.OK)
    public Employee getEmployeeById(@PathVariable("emplId") String empId){
        return employeeService.getEmployeeById(empId);
    }

    @GetMapping(path = "/spring-fact")
    public Object getSpringFact(){
        return employeeService.getSpringFact();
    }

    @GetMapping()
    //@ResponseStatus(HttpStatus.OK)
    public List getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping(path = "/emp/{id}")
    public com.muyi.lukman.model.entity.Employee getEmployeeByIdDb(@PathVariable("id") Long id){
        return employeeService.findById(id);
    }

    @PostMapping(path = "/emp")
    public com.muyi.lukman.model.entity.Employee saveEmployeeDb(@RequestBody com.muyi.lukman.model.entity.Employee empl){
        return employeeService.createEmployee(empl);
    }

    @PutMapping(path = "/emp")
    public com.muyi.lukman.model.entity.Employee updateEmployeeDb(@RequestBody com.muyi.lukman.model.entity.Employee empl){
        return employeeService.updateEmployee(empl);
    }

    @DeleteMapping(path = "/emp/{id}")
    public void deleteEmployeeByIdDb(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
    }

    @GetMapping(path = "/emp")
    public List<com.muyi.lukman.model.entity.Employee> getAllEmployeeDb(){
        return employeeService.findAll();
    }

    @GetMapping(path = "/emp2/{employeeid}")
    public com.muyi.lukman.model.entity.Employee getEmployeeByEmployeeIdDb(@PathVariable("employeeid") String employeeid){
        return employeeService.findByEmployeeId(employeeid);
    }
}
