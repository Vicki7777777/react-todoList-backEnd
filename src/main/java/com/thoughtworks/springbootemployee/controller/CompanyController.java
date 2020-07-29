package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    List<Company> companies = new LinkedList<>();

    public CompanyController() {
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

    @GetMapping
    public List<Company> getCompanies(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "0") int pageSize
    ) {
        if (page == 0) {
            return companies;
        } else {
            return getCompaniesByPage(page, pageSize);
        }
    }

    private List<Company> getCompaniesByPage(int page, int pageSize) {
        int begin;
        int end;
        int count = companies.size();

        begin = (page - 1) * pageSize;
        if (count - begin < pageSize) {
            end = count - 1;
        } else {
            end = begin + pageSize;
        }

        return companies.subList(begin, end);
    }

    @PostMapping
    //TODO code
    public Company addCompany(@RequestBody Company company) {
        companies.add(company);
        return companies.contains(company) ? company : null;
    }

    @GetMapping("/{id}")
    public Company getCompany(@PathVariable int id) {
        for(Company company : companies) {
            if(company.getId() == id) {
                return company;
            }
        }
        return null;
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getAllEmployees(@PathVariable int id) {
        Company company = companies.stream().filter(companyItem -> companyItem.getId() == id).findFirst().orElse(null);
        return company == null ? null : company.getEmployees();
    }

    @PutMapping("/{id}")
    //TODO PATCH
    public void updateCompany(@PathVariable int id, @RequestBody Company company) {
        for (Company companyItem : companies) {
            if (companyItem.getId() == id) {
                companyItem.setEmployees(company.getEmployees());
                companyItem.setId(company.getId());
            }
        }
    }

    @DeleteMapping("/{id}")
    public String deleteAllEmployees(@PathVariable int id) {
//        TODO
        companies.stream().filter(companyItem -> companyItem.getId() == id).findFirst().ifPresent(company -> company.getEmployees().clear());
        return "Delete Success";
    }
}

