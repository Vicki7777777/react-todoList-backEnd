package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Employee;

public class EmployeeMapper {
    public Employee toEmployee(EmployeeRequest employeeRequest) {
        return new Employee(employeeRequest.getId(),employeeRequest.getName(),employeeRequest.getAge(),employeeRequest.getGender(),employeeRequest.getSalary(),employeeRequest.getCompanyId());
    }

    public EmployeeResponse toEmployeeResponse(Employee employee) {
        if(employee == null){
            return null;
        }
        return new EmployeeResponse(employee.getId(),employee.getName(),employee.getAge(),employee.getGender(),employee.getSalary(),employee.getCompanyId());
    }
}
