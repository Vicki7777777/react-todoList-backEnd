package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Exception.CreateException;
import com.thoughtworks.springbootemployee.Exception.FindException;
import com.thoughtworks.springbootemployee.Exception.UpdateException;
import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respority.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    private CompanyMapper companyMapper = new CompanyMapper();
    private EmployeeMapper employeeMapper = new EmployeeMapper();

    public List<CompanyResponse> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream().map(company -> companyMapper.toCompanyResponse(company)).collect(Collectors.toList());
    }

    public CompanyResponse getCompanyById(Integer id) {
        return companyMapper.toCompanyResponse(companyRepository.findById(id).orElse(null));
    }

    public List<EmployeeResponse> getCompanyEmployeesById(Integer id) throws FindException {
        if(getCompanyById(id) == null){
            throw new FindException("No Such Company!");
        }
        List<Employee> employeeList = getCompanyById(id).getEmployees();
        List<EmployeeResponse>employeeResponses = new ArrayList<>();
        for (Employee employee : employeeList) {
            employeeResponses.add(employeeMapper.toEmployeeResponse(employee));
        }
        return employeeResponses;
    }

    public List<CompanyResponse> getCompanyByPage(int page, int pageSize) {
        List<Company> companies = companyRepository.findAll(PageRequest.of(page-1, pageSize)).toList();
        List<CompanyResponse> companyResponses = new ArrayList<>();
        for (Company company : companies) {
            companyResponses.add(companyMapper.toCompanyResponse(company));
        }
        return companyResponses;
    }

    public CompanyResponse createCompany(CompanyRequest companyRequest) throws CreateException{
        Company company = companyMapper.toCompany(companyRequest);
        if(getCompanyById(company.getCompanyId()) == null){
            List<Employee> employees = company.getEmployees();
            company.setEmployees(null);
            company = companyRepository.save(company);
            if (Objects.nonNull(employees)) {
                int companyId = company.getCompanyId();
                employees.forEach(employee -> employee.setCompanyId(companyId));
                company.setEmployees(employees);
                company = companyRepository.save(company);
            }
            return companyMapper.toCompanyResponse(company);
        }
        throw new CreateException("Create unsuccessfully!");
    }

    public CompanyResponse updateCompany(Integer companyId, CompanyRequest companyRequest) throws UpdateException {
        Company company = companyMapper.toCompany(companyRequest);
        if(companyId != company.getCompanyId() ){
            throw new UpdateException("Update unsuccessfully!");
        }
        return companyMapper.toCompanyResponse(companyRepository.save(company));
    }

    public boolean removeCompany(Integer companyId) throws FindException {
        if(getCompanyById(companyId) == null){
            throw new FindException("No Such Company!");
        }
        companyRepository.deleteById(companyId);
        getCompanyById(companyId);
        return getCompanyById(companyId) == null;
    }
}
