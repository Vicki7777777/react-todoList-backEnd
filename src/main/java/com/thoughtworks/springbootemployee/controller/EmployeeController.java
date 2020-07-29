package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    List<Employee> employees = new LinkedList<>();

    public EmployeeController() {
        employees.add(new Employee(1, "Hans", 24, "male", 5000));
        employees.add(new Employee(2, "Amy", 22,"female", 9000));
        employees.add(new Employee(3, "Ray", 28,"male", 10000));
        employees.add(new Employee(4, "Sky", 27,"female", 8000));
        employees.add(new Employee(5, "Hovees", 25,"male", 7000));
        employees.add(new Employee(6, "Mandy", 22,"male", 8888));
        employees.add(new Employee(7, "Ace", 23,"male", 9000));
    }

    @GetMapping
    public List<Employee> getEmployees(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int pageSize,
            @RequestParam(required = false, defaultValue = "") String gender) {
        List<Employee> employeesList = new LinkedList<>();

        if (page == 0 && gender.equals("")) {
            employeesList = employees;
        } else if (!gender.equals("")) {
            employeesList = getEmployeesByGender(gender);
        } else if (page > 0) {
            employeesList = getEmployeesByPage(page, pageSize);
        }

        return employeesList;
    }

    private List<Employee> getEmployeesByPage(int page, int pageSize) {
        int begin;
        int end;
        int count = employees.size();

        begin = (page - 1) * pageSize;
        if (count - begin < pageSize) {
            end = count - 1;
        } else {
            end = begin + pageSize;
        }

        return employees.subList(begin, end);
    }

    private List<Employee> getEmployeesByGender(String gender) {
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
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        for (Employee employeeItem : employees) {
            if (employeeItem.getId() == id) {
                employeeItem.setGender(employee.getGender());
            }
        }
        return employee;
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id) {
        employees.removeIf(employee -> employee.getId() == id);
        return "Delete Employee id = " + id + " Success";
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
