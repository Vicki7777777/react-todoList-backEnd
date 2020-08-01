package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Exception.CreateException;
import com.thoughtworks.springbootemployee.Exception.FindException;
import com.thoughtworks.springbootemployee.Exception.UpdateException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompanies(int page, int pageSize
    ) {
        return companyService.getCompanyByPage(page, pageSize);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public List<Company> getCompanies() {
        return companyService.getAllCompanies();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Company getCompany(@PathVariable Integer id) {
        return companyService.getCompanyById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/employees")
    public List<Employee> getAllEmployees(@PathVariable Integer id) throws FindException {
        return companyService.getCompanyEmployeesById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable int id, @RequestBody Company company) throws UpdateException {
        return companyService.updateCompany(id, company);
    }


    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@PathVariable int id,@RequestBody Company company) throws CreateException {
        return companyService.createCompany(id,company);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteAllEmployees(@PathVariable int id) throws FindException {
        companyService.removeCompany(id);
    }
}

