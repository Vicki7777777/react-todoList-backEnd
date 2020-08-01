package com.thoughtworks.springbootemployee;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.thoughtworks.springbootemployee.Exception.CreateException;
import com.thoughtworks.springbootemployee.Exception.FindException;
import com.thoughtworks.springbootemployee.Exception.UpdateException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respority.CompanyRepository;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    List<Company> companies = new LinkedList<>();

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

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
        given(companyRepository.findAll()).willReturn(companies);

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
        Integer id = 1;
        Company expectedCompany = companies.get(0);
        given(companyRepository.findById(id)).willReturn(java.util.Optional.ofNullable(expectedCompany));

        //when
        Company selectedCompany = companyService.getCompanyById(id);

        //then
        assertEquals(expectedCompany, selectedCompany);
    }

    @Test
    void should_return_specific_company_employees_when_get_company_given_id() throws FindException {
        //given
        Integer id = 1;
        Company expectedCompany = companies.get(0);
        List<Employee> expectedEmployees = expectedCompany.getEmployees();
        given(companyRepository.findById(id)).willReturn(java.util.Optional.of(expectedCompany));

        //when
        List<Employee> employees = companyService.getCompanyEmployeesById(id);

        //then
        assertNotNull(employees);
        assertEquals(expectedCompany.getEmployees().size(), employees.size());
        for (int i = 0; i < employees.size(); i++) {
            assertEquals(expectedCompany.getEmployees().get(i), employees.get(i));
        };
    }
    @Test
    void should_return_wrong_message_when_find_specific_company_employees_given_non_existent_id() throws FindException {
        //given
        Integer id = 100000;
        when(companyRepository.findById(id)).thenReturn(Optional.empty());
        //when
        Throwable exception = assertThrows(FindException.class,
                () -> companyService.getCompanyEmployeesById(id));
        //then
        assertEquals(new FindException("No Such Company!").getMessage(),exception.getMessage());

    }

    @Test
    void should_return_wrong_message_when_create_company_given_existent_companyId() throws CreateException {
        //given
        Company company = new Company(20, "HansOOCL");
        Integer id = 20;
        given(companyRepository.findById(id)).willReturn(Optional.of(company));
        //when
        Throwable exception = assertThrows(CreateException.class,
                () -> companyService.createCompany(id,company));
        //then
        assertEquals(new FindException("Create unsuccessfully!").getMessage(),exception.getMessage());

    }

    @Test
    void should_return_wrong_message_when_delete_company_given_non_existent_companyId() throws FindException {
        //given
        Integer id = 100000;
        when(companyRepository.findById(id)).thenReturn(Optional.empty());
        //when
        Throwable exception = assertThrows(FindException.class,
                () -> companyService.removeCompany(id));
        //then
        assertEquals(new FindException("No Such Company!").getMessage(),exception.getMessage());

    }

    @Test
    void should_return_wrong_message_when_update_company_given_error_companyId()  throws UpdateException{
        //given
        Company company = new Company(20, "HansOOCL");
        Integer id = 21;
        //when
        Throwable exception = assertThrows(UpdateException.class,
                () -> companyService.updateCompany(id,company));
        //then
        assertEquals(new UpdateException("Update unsuccessfully!").getMessage(),exception.getMessage());

    }


    //todo
    @Test
    void should_return_specific_company_when_get_company_given_page_pageSize() {
        //given
        int PAGE = 1;
        int PAGE_SIZE = 5;
        Page<Company> result = new PageImpl<>(companies.subList(0, 4));
        given(companyRepository.findAll(PageRequest.of(PAGE, PAGE_SIZE))).willReturn(result);

        //when
        List<Company> foundCompanies = companyService.getCompanyByPage(PAGE+1, PAGE_SIZE);

        //then
        assertEquals(result.toList(), foundCompanies);
    }

    @Test
    void should_new_company_when_add_company_given_one_company() throws CreateException {
        //given
        Company company = new Company(20, "HansOOCL");
        given(companyRepository.save(company)).willReturn(company);

        //when
        Company createdCompany = companyService.createCompany(20,company);

        //then
        assertEquals(company, createdCompany);
    }

    @Test
    void should_updated_when_update_employee_given_employee_message() throws UpdateException {
        //given
        int companyId = 1;
        Company companyInfo = new Company(companyId, "HansOOCL2");
        companyInfo.setEmployees(Arrays.asList(
                new Employee(1, "Hans", 24, "male", 5000),
                new Employee(2, "Amy", 22, "female", 9000)));
        given(companyRepository.findById(companyId)).willReturn(java.util.Optional.of(companyInfo));
        given(companyRepository.save(companyInfo)).willReturn(companyInfo);

        //when
        Company company = companyService.updateCompany(companyId, companyInfo);

        //then
        assertEquals(company,companyInfo);
    }

    @Test
    void should_delete_employee_when_remove_employee_given_id() throws CreateException, FindException {
        //given
        Integer companyId = 25;
        Company expectedCompany = new Company(25, "VickiOOCL");
        companyService.createCompany(25,expectedCompany);

        //when
        Boolean result = companyService.removeCompany(companyId);

        //then
        assertTrue(result);
    }
}
