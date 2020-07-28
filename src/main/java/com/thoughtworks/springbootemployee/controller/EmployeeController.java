package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    List<Employee> employees = new LinkedList<>();

    @GetMapping
    public List<Employee> getEmployees(
            @RequestParam(required = false, defaultValue = "") String gender) {
        List<Employee> employeesList = new LinkedList<>();
        for (Employee employee : employees) {
            if (employee.getGender().equals(gender)) {
                employeesList.add(employee);
            }
        }
        return employeesList;
    }

    @PostMapping
    public void addEmployee(@RequestBody Employee employee) {
        employees.add(employee);
    }

    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        for (Employee employeeItem : employees) {
            if (employeeItem.getId() == id) {
                employeeItem.setGender(employee.getGender());
            }
        }
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employees.remove(employee);
            }
        }
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        Employee employee = null;
        for (Employee employeeItem : employees) {
            if (employeeItem.getId() == id) {
                employee = employeeItem;
            }
        }
        return employee;
    }
}
