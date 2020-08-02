package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.Exception.CreateException;
import com.thoughtworks.springbootemployee.Exception.FindException;
import com.thoughtworks.springbootemployee.Exception.UpdateException;
import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
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
    public List<CompanyResponse> getCompanies(int page, int pageSize
    ) {
        return companyService.getCompanyByPage(page, pageSize);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CompanyResponse> getCompanies() {
        return companyService.getAllCompanies();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CompanyResponse getCompany(@PathVariable Integer id) throws FindException {
        return companyService.getCompanyById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/employees")
    public List<EmployeeResponse> getAllEmployees(@PathVariable Integer id) throws FindException {
        return companyService.getCompanyEmployeesById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public CompanyResponse updateCompany(@PathVariable int id, @RequestBody CompanyRequest companyRequest) throws UpdateException {
        return companyService.updateCompany(id, companyRequest);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse createCompany(@RequestBody CompanyRequest companyRequest) throws CreateException, FindException {
        return companyService.createCompany(companyRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public boolean deleteAllEmployees(@PathVariable int id) throws FindException {
        return companyService.removeCompany(id);
    }
}

