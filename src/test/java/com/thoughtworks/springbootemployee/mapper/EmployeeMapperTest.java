package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeMapperTest {
    @Test
    void should_get_employee_when_mapper_given_employeeRequest(){
        //given
        EmployeeMapper employeeMapper = new EmployeeMapper();
        EmployeeRequest employeeRequest = new EmployeeRequest(8, "cc", 23, "male", 10000,1);
        //when
        Employee employee = employeeMapper.toEmployee(employeeRequest);
        //then
        assertEquals(employeeRequest.getId(), employee.getId());
        assertEquals(employeeRequest.getAge(), employee.getAge());
        assertEquals(employeeRequest.getCompanyId(), employee.getCompanyId());
        assertEquals(employeeRequest.getGender(), employee.getGender());
        assertEquals(employeeRequest.getName(), employee.getName());
        assertEquals(employeeRequest.getSalary(), employee.getSalary());
    }

    @Test
    void should_get_employeeRequest_when_mapper_given_employee(){
        //given
        EmployeeMapper employeeMapper = new EmployeeMapper();
        Employee employee = new Employee(8, "cc", 23, "male", 10000,1);
        //when
        EmployeeRequest employeeRequest = employeeMapper.toEmployeeRequest(employee);
        //then
        assertEquals(employee.getId(),employeeRequest.getId());
        assertEquals(employee.getAge(), employeeRequest.getAge());
        assertEquals(employee.getCompanyId(), employeeRequest.getCompanyId());
        assertEquals(employee.getGender(), employeeRequest.getGender());
        assertEquals(employee.getName(), employeeRequest.getName());
        assertEquals(employee.getSalary(), employeeRequest.getSalary());
    }
}
