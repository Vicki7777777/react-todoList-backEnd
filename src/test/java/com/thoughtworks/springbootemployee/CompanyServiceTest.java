package com.thoughtworks.springbootemployee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respority.Respority;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.LinkedList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyServiceTest {
    List<Company> companies = new LinkedList<>();

    @BeforeAll
    public void createCompanies() {
        companies.add(new Company(1, "OOCL"));
        companies.add(new Company(2, "CargoSmart"));
        companies.add(new Company(3, "Alibaba"));
        companies.add(new Company(4, "Gree"));
        companies.add(new Company(5, "Baidu"));
        for (Company company : companies) {
            List<Employee> employees = new LinkedList<>();
            employees.add(new Employee(1, "Hans", 24, "male", 5000));
            employees.add(new Employee(2, "Amy", 22, "female", 9000));
            employees.add(new Employee(3, "Ray", 28, "male", 10000));
            employees.add(new Employee(4, "Sky", 27, "female", 8000));
            employees.add(new Employee(5, "Hovees", 25, "male", 7000));
            employees.add(new Employee(6, "Mandy", 22, "male", 8888));
            employees.add(new Employee(7, "Ace", 23, "male", 9000));
            company.setEmployees(employees);
        }
    }


    @Test
    void should_return_companies_when_get_all_companyes_given_company_service() {
        //given
        Respority respority = mock(Respority.class);
        CompanyService companyService = new CompanyService(respority);
        given(respority.getAllCompanies()).willReturn(companies);

        given(respority.getAllCompanies()).willReturn(companies);

        //when
        List<Company> companyList = companyService.getAllCompanies();

        //then
        assertNotNull(companyList);
        assertEquals(companies.size(), companyList.size());
        assertEquals(companies, companyList);
    }

    @Test
    void should_return_specific_company_when_get_company_given_id() {
        //given
        int id = 1;
        Respority respority = mock(Respority.class);
        CompanyService companyService = new CompanyService(respority);
        given(respority.getAllCompanies()).willReturn(companies);

        //when
        Company company = companyService.getCompanyById(id);

        //then
        assertEquals(companies.get(0), company);
    }

    @Test
    void should_return_specific_company_employees_when_get_company_given_id() {
        //given
        int id = 1;
        Respority respority = mock(Respority.class);
        CompanyService companyService = new CompanyService(respority);
        given(respority.getAllCompanies()).willReturn(companies);

        //when
        List<Employee> employees = companyService.getCompanyEmployeesById(id);

        //then
        assertEquals(companies.get(0).getEmployees().size(), employees.size());
        assertEquals(companies.get(0).getEmployees(), employees);
    }

    @Test
    void should_return_specific_company_when_get_company_given_page_pageSize() {
        //given
        int PAGE = 1;
        int PAGE_SIZE = 5;
        Respority respority = mock(Respority.class);
        CompanyService companyService = new CompanyService(respority);
        given(respority.getAllCompanies()).willReturn(companies);

        //when
        List<Company> companyList = companyService.getCompanyByPage(PAGE, PAGE_SIZE);

        //then
        assertEquals(companies.subList(0, 4), companyList);
    }
}
