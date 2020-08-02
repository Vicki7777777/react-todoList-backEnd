package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Exception.CreateException;
import com.thoughtworks.springbootemployee.Exception.FindException;
import com.thoughtworks.springbootemployee.Exception.UpdateException;
import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<EmployeeResponse> getEmployees(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {
        if (page != null && pageSize != null) {
            return employeeService.getEmployeeByPage(page, pageSize);
        }
        return employeeService.getAllEmployees();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(params = "gender")
    private List<EmployeeResponse> getEmployeesByGender(@RequestParam(required = false, defaultValue = "") String gender) {
        return employeeService.getEmployeeByGender(gender);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EmployeeResponse addEmployee(@RequestBody EmployeeRequest employeeRequest) throws CreateException {
        return employeeService.createEmployee(employeeRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public EmployeeResponse updateEmployee(@PathVariable int id, @RequestBody EmployeeRequest employeeRequest) throws UpdateException {
        return employeeService.updateEmployee(id, employeeRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public boolean deleteEmployee(@PathVariable int id) throws FindException {
        return employeeService.removeEmployee(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }
}
