package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Exception.CreateException;
import com.thoughtworks.springbootemployee.Exception.FindException;
import com.thoughtworks.springbootemployee.Exception.UpdateException;
import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respority.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper = new EmployeeMapper();

    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        for (Employee employee : employeeList) {
            employeeResponses.add(employeeMapper.toEmployeeResponse(employee));
        }
        return employeeResponses;
    }

    public EmployeeResponse getEmployeeById(Integer id) {
        return employeeMapper.toEmployeeResponse(employeeRepository.findById(id).orElse(null));
    }

    public List<EmployeeResponse> getEmployeeByPage(int page, int page_size) {
        List<Employee> employeeList = employeeRepository.findAll(PageRequest.of(page-1, page_size)).toList();
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        for (Employee employee : employeeList) {
            employeeResponses.add(employeeMapper.toEmployeeResponse(employee));
        }
        return employeeResponses;
    }

    public List<EmployeeResponse> getEmployeeByGender(String gender) {
        List<Employee> employeeList = employeeRepository.findByGender(gender);
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        for (Employee employee : employeeList) {
            employeeResponses.add(employeeMapper.toEmployeeResponse(employee));
        }
        return employeeResponses;
    }

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) throws CreateException {
        Employee employee = employeeMapper.toEmployee(employeeRequest);
        if(getEmployeeById(employee.getId()) == null){
            return employeeMapper.toEmployeeResponse(employeeRepository.save(employee));
        }
        throw new CreateException("Create unsuccessfully!");
    }

    public EmployeeResponse updateEmployee(Integer id, EmployeeRequest employeeRequest) throws UpdateException {
        Employee employee = employeeMapper.toEmployee(employeeRequest);
        if(id != employee.getId()){
            throw new UpdateException("Update unsuccessfully!");
        }
        return employeeMapper.toEmployeeResponse(employeeRepository.save(employee));
    }

    public Boolean removeEmployee(Integer employeeId) throws FindException {
        if(getEmployeeById(employeeId) == null){
            throw new FindException("No Such Company!");
        }
        employeeRepository.deleteById(employeeId);
        return getEmployeeById(employeeId) == null;
    }
}
