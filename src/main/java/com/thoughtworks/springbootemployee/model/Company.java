package com.thoughtworks.springbootemployee.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int companyId;
    private String companyName;
    //todo
    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "companyId")
    private List<Employee> employees;

    public Company() {
    }

    public Company(int companyId, String name,List<Employee> employees) {
        this.companyName = name;
        this.companyId = companyId;
        this.employees = employees;
    }

    public Company(String name,List<Employee> employees) {
        this.companyName = name;
        this.employees = employees;
    }

    public Company(int companyId, String name) {
        this.companyName = name;
        this.companyId = companyId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return companyId == company.companyId &&
                Objects.equals(companyName, company.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, companyName, employees);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
