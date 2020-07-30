package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respority.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Integer id) {
        return companyRepository.findById(id).orElse(null);
    }

    public List<Employee> getCompanyEmployeesById(Integer id) {
        return getCompanyById(id).getEmployees();
    }

    //TODO
    public List<Company> getCompanyByPage(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page-1, pageSize)).toList();
    }

    public Company createCompany(Company company) {
        List<Employee> employees = company.getEmployees();
        company.setEmployees(null);
        company = companyRepository.save(company);
        if (Objects.nonNull(employees)) {
            int companyId = company.getCompanyId();
            employees.forEach(employee -> employee.setCompanyId(companyId));
            company.setEmployees(employees);
            company = companyRepository.save(company);
        }
        return company;
    }

    public Company updateCompany(Integer companyId, Company companyInfo) {
        Company company = getCompanyById(companyId);
        company.setEmployees(companyInfo.getEmployees());
        company.setCompanyName(companyInfo.getCompanyName());
        return companyRepository.save(company);
    }

    public Boolean removeCompany(Integer companyId) {
        companyRepository.deleteById(companyId);
        return getCompanyById(companyId) == null;
    }
}
