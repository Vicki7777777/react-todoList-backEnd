package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Exception.CreateException;
import com.thoughtworks.springbootemployee.Exception.FindException;
import com.thoughtworks.springbootemployee.Exception.UpdateException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respority.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> getEmployeeByPage(int page, int page_size) {
        return employeeRepository.findAll(PageRequest.of(page-1, page_size)).toList();
    }

    public List<Employee> getEmployeeByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Employee createEmployee(Employee employee) throws CreateException {
        if(getEmployeeById(employee.getId()) == null){
            return employeeRepository.save(employee);
        }
        throw new CreateException("Create unsuccessfully!");
    }

    public Employee updateEmployee(Integer id, Employee employeeInfo) throws UpdateException {
        if(id != employeeInfo.getId()){
            throw new UpdateException("Update unsuccessfully!");
        }
        Employee employee = getEmployeeById(id);
        employee.setAge(employeeInfo.getAge());
        employee.setGender(employeeInfo.getGender());
        employee.setSalary(employeeInfo.getSalary());
        employee.setName(employeeInfo.getName());
        return employeeRepository.save(employee);
    }

    public Boolean removeEmployee(Integer employeeId) throws FindException {
        if(getEmployeeById(employeeId) == null){
            throw new FindException("No Such Company!");
        }
        employeeRepository.deleteById(employeeId);
        return getEmployeeById(employeeId) == null;
    }
}
