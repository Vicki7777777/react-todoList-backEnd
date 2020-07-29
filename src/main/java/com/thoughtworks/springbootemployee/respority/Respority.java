package com.thoughtworks.springbootemployee.respority;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Respority {
    private List<Company> companies = new ArrayList<>();

    public Respority() {
        this.companies.add(new Company(1, "OOCL"));
        this.companies.add(new Company(2, "CargoSmart"));
        this.companies.add(new Company(3, "Alibaba"));
        this.companies.add(new Company(4, "Gree"));
        this.companies.add(new Company(5, "Baidu"));
        for (Company company : companies) {
            List<Employee> employees = new LinkedList<>();
            employees.add(new Employee(1, "Hans", 24, "male", 5000));
            employees.add(new Employee(2, "Amy", 22,"female", 9000));
            employees.add(new Employee(3, "Ray", 28,"male", 10000));
            employees.add(new Employee(4, "Sky", 27,"female", 8000));
            employees.add(new Employee(5, "Hovees", 25,"male", 7000));
            employees.add(new Employee(6, "Mandy", 22,"male", 8888));
            employees.add(new Employee(7, "Ace", 23,"male", 9000));
            company.setEmployees(employees);
        }
    }

    public List<Company> getAllCompanies() {
        return companies;
    }
}
