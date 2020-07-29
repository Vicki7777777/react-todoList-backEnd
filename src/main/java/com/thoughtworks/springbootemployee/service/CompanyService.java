package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.respority.Respority;

import java.util.List;

public class CompanyService {
    private Respority respority = new Respority();

    public List<Company> getAllCompanies() {
        return respority.getAllCompanies();
    }

    public Company getCompanyById(int id) {
        return getAllCompanies().stream().filter(company -> company.getId() == id).findFirst().orElse(null);
    }
}
