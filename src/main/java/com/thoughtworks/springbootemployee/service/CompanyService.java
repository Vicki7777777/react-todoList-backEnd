package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Exception.CreateException;
import com.thoughtworks.springbootemployee.Exception.FindException;
import com.thoughtworks.springbootemployee.Exception.UpdateException;
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

    public List<Employee> getCompanyEmployeesById(Integer id) throws FindException {
        if(getCompanyById(id) == null){
            throw new FindException("No Such Company!");
        }
        return getCompanyById(id).getEmployees();
    }

    public List<Company> getCompanyByPage(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page-1, pageSize)).toList();
    }

    public Company createCompany(int id,Company company) throws CreateException {
        if(getCompanyById(id) == null){
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
        throw new CreateException("Create unsuccessfully!");
    }

    public Company updateCompany(Integer companyId, Company company) throws UpdateException {
        if(companyId != company.getCompanyId() ){
            throw new UpdateException("Update unsuccessfully!");
        }
        return companyRepository.save(company);
    }

    public Boolean removeCompany(Integer companyId) throws FindException {
        if(getCompanyById(companyId) == null){
            throw new FindException("No Such Company!");
        }
        companyRepository.deleteById(companyId);
        return getCompanyById(companyId) == null;
    }
}
