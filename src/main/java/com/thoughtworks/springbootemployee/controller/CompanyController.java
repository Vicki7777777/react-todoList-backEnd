package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    List<Company> companies = new LinkedList<>();

    @GetMapping
    public List<Company> getCompanies() {
        return companies;
    }

    @PostMapping
    public void addCompany(@RequestBody Company company) {
        companies.add(company);
    }

    @GetMapping("/{id}")
    public Company getCompany(@PathVariable int id) {
        Company company = null;
        for(Company companyItem : companies) {
            if(companyItem.getId() == id) {
                company = companyItem;
            }
        }
        return company;
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getAllEmployees(@PathVariable int id) {
        List<Employee> employees = null;
        for(Company company : companies) {
            if(company.getId() == id) {
                employees = company.getEmployees();
            }
        }
        return employees;
    }

    @PutMapping("/{id}")
    public void updateCompany(@PathVariable int id, @RequestBody Company company) {
        for(Company companyItem : companies) {
            if(companyItem.getId() == id) {
                companyItem.setEmployees(company.getEmployees());
                companyItem.setId(company.getId());
            }
        }
    }

    @PutMapping("/{id}")
    public void deleteAllEmployees(@PathVariable int id, @RequestBody Company company) {
        for(Company companyItem : companies) {
            if(companyItem.getId() == id) {
                companyItem.getEmployees().clear();
            }
        }
    }
}

