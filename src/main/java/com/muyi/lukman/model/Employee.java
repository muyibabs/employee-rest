package com.muyi.lukman.model;

public class Employee {
    private String employeeId;
    private String name;
    private Integer age;

    public Employee() {
    }

    public Employee(String employeeId, String name, int age) {
        this.employeeId = employeeId;
        this.name = name;
        this.age = age;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
