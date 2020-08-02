package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.model.Company;

public class CompanyMapper {
    public Company toCompany(CompanyRequest companyRequest) {
        return new Company(companyRequest.getCompanyId(),companyRequest.getCompanyName(),companyRequest.getEmployees());
    }

    public CompanyRequest toCompanyRequest(Company company) {
        return new CompanyRequest(company.getCompanyId(),company.getCompanyName(),company.getEmployees());
    }
}
