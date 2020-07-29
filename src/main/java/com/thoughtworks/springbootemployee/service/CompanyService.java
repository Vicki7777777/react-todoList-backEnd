package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respority.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CompanyService {

/*    private Company company;*/
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

    public PageImpl<Company> getCompanyByPage(int page, int pageSize) {
        return (PageImpl<Company>) companyRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
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
