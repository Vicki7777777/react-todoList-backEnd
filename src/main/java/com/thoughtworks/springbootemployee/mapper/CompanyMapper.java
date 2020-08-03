package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public Company toCompany(CompanyRequest companyRequest) {
        return new Company(companyRequest.getCompanyId(),companyRequest.getCompanyName(),companyRequest.getEmployees());
    }

    public CompanyResponse toCompanyResponse(Company company) {
        if(company == null){
            return null;
        }
        return new CompanyResponse(company.getCompanyId(),company.getCompanyName(),company.getEmployees());
    }
}
