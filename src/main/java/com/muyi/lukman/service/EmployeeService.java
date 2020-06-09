package com.muyi.lukman.service;

import com.muyi.lukman.dao.EmployeeDao;
import com.muyi.lukman.model.Employee;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    List<Employee> allEmployees = new ArrayList<>();
    RestService restService;
    EmployeeDao employeeDao;
    ModelMapper modelMapper;

    public EmployeeService(RestService restService, EmployeeDao employeeDao, ModelMapper modelMapper) {
        this.restService = restService;
        this.employeeDao = employeeDao;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void initMe(){
        Employee e1 = new Employee("Emp001","Muyiron", 22);
        Employee e2 = new Employee("Emp002","Folasade", 52);
        allEmployees.add(e1);
        allEmployees.add(e2);

    }

//    public List<Employee> getAllEmployees(){
//        return allEmployees;
//    }
//
//    public Employee getEmployeeById(String empId){
//        return allEmployees.stream()
//                .filter(employee -> employee.getEmployeeId().equals(empId))
//                .findAny().get();
//    }

    public Object getSpringFact(){
        return restService.getRandomFact();
    }

    public Employee createEmployee(Employee employeeModel){
        com.muyi.lukman.model.entity.Employee employeeEntity = null;
        employeeEntity = modelMapper.map(employeeModel, com.muyi.lukman.model.entity.Employee.class);
        employeeEntity = employeeDao.save(employeeEntity);
        employeeModel = modelMapper.map(employeeEntity, com.muyi.lukman.model.Employee.class);
        return employeeModel;
    }

    public void updateEmployee(Employee employeeModel){
        com.muyi.lukman.model.entity.Employee employeeEntity =
                modelMapper.map(employeeModel, com.muyi.lukman.model.entity.Employee.class);
        employeeDao.save(employeeEntity);
    }

    public Employee findById(Long id){
        Employee employeeModel = null;
        try {
            com.muyi.lukman.model.entity.Employee employeeEntity = employeeDao.findById(id).get();
            employeeModel = modelMapper.map(employeeEntity, Employee.class);
        }catch (Exception e){}
        return employeeModel;
    }

    public void deleteEmployee(Long id){
        com.muyi.lukman.model.entity.Employee employee = employeeDao.findById(id).get();
        employeeDao.delete(employee);
    }

    public List<Employee> findAll(){
        List<com.muyi.lukman.model.entity.Employee> employeeEntityList = employeeDao.findAll();
        //return modelMapper.map(employeeEntityList, new TypeToken<List<Employee>>() {}.getType());
        if(employeeEntityList==null){
            System.out.println("================ employeeEntityList is nulllllllllll");
        }
        if(employeeEntityList.size()==0){
            System.out.println("================ employeeEntityList size is 0");
        }
        if(employeeEntityList==null || employeeEntityList.size()==0){
            return null;
        }
        return employeeEntityList.stream()
                .map(empEntity -> modelMapper.map(empEntity, Employee.class))
                .collect(Collectors.toList());

    }

    public Employee findByEmployeeId(String employeeId){
        Employee emp = null;
        try {
            com.muyi.lukman.model.entity.Employee employeeEntity = employeeDao.findByEmployeeId(employeeId);
            emp = modelMapper.map(employeeEntity, Employee.class);
        }catch (Exception e){}
        return emp;
    }
}
