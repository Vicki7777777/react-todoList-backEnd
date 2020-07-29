package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respority.CompanyRepository;

import java.util.List;

public class CompanyService {
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.getAllCompanies();
    }

    public Company getCompanyById(int id) {
        return getAllCompanies().stream().filter(company -> company.getId() == id).findFirst().orElse(null);
    }

    public List<Employee> getCompanyEmployeesById(int id) {
        return getCompanyById(id).getEmployees();
    }

    public List<Company> getCompanyByPage(int page, int pageSize) {
        int begin;
        int end;
        int count = getAllCompanies().size();

        begin = (page - 1) * pageSize;
        if (count - begin < pageSize) {
            end = count - 1;
        } else {
            end = begin + pageSize - 1;
        }

        return getAllCompanies().subList(begin, end);
    }

    public Company createCompany(Company company) {
        List<Company> companies = getAllCompanies();
        companies.add(company);
        return companies.contains(company) ? company : null;
    }
}
