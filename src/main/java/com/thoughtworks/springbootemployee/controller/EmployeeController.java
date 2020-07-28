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
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "") int pageSize,
            @RequestParam(required = false, defaultValue = "") String gender) {
        List<Employee> employeesList = new LinkedList<>();

        if (page == 0 && gender.equals("")) {
            employeesList = employees;
        } else if (!gender.equals("")) {
            getEmployeesByGender(gender, employeesList);
        } else if (page > 0) {
            return getEmployeesByPage(page, pageSize);
        }

        return employeesList;
    }

    private List<Employee> getEmployeesByPage(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "") int pageSize) {
        int begin;
        int end;
        int count = employees.size();

        begin = (page - 1) * pageSize + 1;
        if (count - begin > pageSize) {
            end = begin + pageSize - 1;
        } else {
            end = count - 1;
        }

        return employees.subList(begin, end);
    }

    private void getEmployeesByGender(@RequestParam(required = false, defaultValue = "") String gender, List<Employee> employeesList) {
        for (Employee employee : employees) {
            if (employee.getGender().equals(gender)) {
                employeesList.add(employee);
            }
        }
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
        employees.removeIf(employee -> employee.getId() == id);
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
