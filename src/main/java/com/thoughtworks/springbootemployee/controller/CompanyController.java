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
        if (count - begin > pageSize) {
            end = count - 1;
        } else {
            end = begin + pageSize - 1;
        }

        return companies.subList(begin, end);
    }

    @PostMapping
    public void addCompany(@RequestBody Company company) {
        companies.add(company);
    }

    @GetMapping("/{id}")
    public Company getCompany(@PathVariable int id) {
        return (Company) companies.stream().filter(company -> company.getId() == id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getAllEmployees(@PathVariable int id) {
        Company company = companies.stream().filter(companyItem -> companyItem.getId() == id).findFirst().orElse(null);
        return company == null ? null : company.getEmployees();
    }

    @PutMapping("/{id}")
    public void updateCompany(@PathVariable int id, @RequestBody Company company) {
        for (Company companyItem : companies) {
            if (companyItem.getId() == id) {
                companyItem.setEmployees(company.getEmployees());
                companyItem.setId(company.getId());
            }
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAllEmployees(@PathVariable int id) {
        companies.stream().filter(companyItem -> companyItem.getId() == id).findFirst().ifPresent(company -> company.getEmployees().clear());
    }
}

