package com.thoughtworks.springbootemployee.respority;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Respority {
    private List<Company> companies = new ArrayList<>();

    public Respority() {
    }

    public List<Company> getAllCompanies() {
        return companies;
    }
}
