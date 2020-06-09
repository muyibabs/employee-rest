package com.muyi.lukman.controller;

import com.muyi.lukman.exception.BadRequestException;
import com.muyi.lukman.exception.ConflictException;
import com.muyi.lukman.exception.NotFoundException;
import com.muyi.lukman.model.Employee;
import com.muyi.lukman.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping( path ="/v1/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

//    @GetMapping(path = "/{emplId}")
//    //@ResponseStatus(HttpStatus.OK)
//    public Employee getEmployeeById(@PathVariable("emplId") String empId){
//        return employeeService.getEmployeeById(empId);
//    }

    @GetMapping(path = "/spring-fact")
    public Object getSpringFact(){
        return employeeService.getSpringFact();
    }

//    @GetMapping()
//    //@ResponseStatus(HttpStatus.OK)
//    public List getAllEmployees(){
//        return employeeService.getAllEmployees();
//    }

    @GetMapping(path = "/emp/{id}")
    public Employee getEmployeeByIdDb(@PathVariable("id") Long id){
        if(id==null || id<0)
            throw new BadRequestException("101","Invalid id");
        Employee emp = employeeService.findById(id);
        if(emp==null)
            throw new NotFoundException("404", "Employee not found with id: "+id);
        return emp;
    }

    @PostMapping(path = "/emp")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployeeDb(@RequestBody @Valid Employee empl){
        if(employeeService.findByEmployeeId(empl.getEmployeeId()) != null)
            throw new ConflictException("123","Employee with EmployeeId: "+empl.getEmployeeId()+" already exist.");
        return employeeService.createEmployee(empl);
    }

    @PutMapping(path = "/emp")
    public void updateEmployeeDb(@RequestBody @Valid Employee empl){
        if(empl.getId()==null || empl.getId()<1)
            throw new BadRequestException("333","Invalid id");
        Employee employee = employeeService.findById(empl.getId());
        if(employee==null)
            throw new NotFoundException("212","Record not found");
        employeeService.updateEmployee(empl);
    }

    @DeleteMapping(path = "/emp/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeByIdDb(@PathVariable("id") Long id){
        if(id<1)
            throw new BadRequestException("345","Invalid id.");
        Employee employee = employeeService.findById(id);
        if(employee==null)
            throw new NotFoundException("222","Employee with id: "+id+" not found");
        employeeService.deleteEmployee(id);
    }

    @GetMapping(path = "/emp")
    public List<Employee> getAllEmployeeDb(){
        List<Employee> emps = employeeService.findAll();
        if(emps==null)
            throw new NotFoundException("212","No record found");
        return emps;
    }

    @GetMapping(path = "/emp2/{employeeid}")
    public Employee getEmployeeByEmployeeIdDb(@PathVariable("employeeid") String employeeid){
        if(employeeid.trim().equals(""))
            throw new BadRequestException("345","employeeid cannot be empty.");
        Employee employee = employeeService.findByEmployeeId(employeeid);
        if(employee==null)
            throw new NotFoundException("222","Employee with employeeId: "+employeeid+" not found");
        return employee;
    }

    //Steps to use a controlleradvice
    //1 Create Exception classes extending RuntimeException
    //2 Create a pojo class to handle the exception details which will be passed back to client
    //3 Create Exception handler class(ControllerAdvice) - to handle custom and existing exceptions - this returns a responseentity of the exception pojo, status-code

}
