package com.thoughtworks.springbootemployee.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

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

import java.util.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    List<Company> companies = new LinkedList<>();

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;
    private CompanyMapper companyMapper = new CompanyMapper();
    private EmployeeMapper employeeMapper = new EmployeeMapper();

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
    void should_return_companies_when_get_all_companies_given_company_service() {
        //given
        given(companyRepository.findAll()).willReturn(companies);

        //when
        List<CompanyResponse> companyList = companyService.getAllCompanies();

        //then
        assertNotNull(companyList);
        assertEquals(companies.size(), companyList.size());
    }

    @Test
    void should_return_specific_company_when_get_company_given_id() throws FindException {
        //given
        Integer id = 1;
        CompanyResponse expectedCompany = companyMapper.toCompanyResponse(companies.get(0));
        given(companyRepository.findById(id)).willReturn(Optional.ofNullable(companies.get(0)));

        //when
        CompanyResponse selectedCompany = companyService.getCompanyById(id);

        //then
        assertEquals(expectedCompany.getCompanyId(), selectedCompany.getCompanyId());
        assertEquals(expectedCompany.getCompanyName(), selectedCompany.getCompanyName());
        assertEquals(expectedCompany.getEmployees(), selectedCompany.getEmployees());
    }

    @Test
    void should_return_specific_company_employees_when_get_company_given_id() throws FindException {
        //given
        Integer id = 1;
        CompanyResponse expectedCompany = companyMapper.toCompanyResponse(companies.get(0));
        List<Employee> expectedEmployees = expectedCompany.getEmployees();
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        for (Employee employee : expectedEmployees) {
            employeeResponses.add(employeeMapper.toEmployeeResponse(employee));
        }
        given(companyRepository.findById(id)).willReturn(java.util.Optional.of(companies.get(0)));

        //when
        List<EmployeeResponse> employees = companyService.getCompanyEmployeesById(id);

        //then
        assertNotNull(employees);
        assertEquals(expectedCompany.getEmployees().size(), employees.size());
        for (int i = 0; i < employees.size(); i++) {
            assertEquals(employeeResponses.get(i).getId(), employees.get(i).getId());
            assertEquals(employeeResponses.get(i).getAge(), employees.get(i).getAge());
            assertEquals(employeeResponses.get(i).getCompanyId(), employees.get(i).getCompanyId());
            assertEquals(employeeResponses.get(i).getGender(), employees.get(i).getGender());
            assertEquals(employeeResponses.get(i).getName(), employees.get(i).getName());
            assertEquals(employeeResponses.get(i).getSalary(), employees.get(i).getSalary());
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
        CompanyRequest companyRequest = new CompanyRequest(20, "HansOOCL");
        Integer id = 20;
        given(companyRepository.findById(id)).willReturn(Optional.of(companyMapper.toCompany(companyRequest)));
        //when
        Throwable exception = assertThrows(CreateException.class,
                () -> companyService.createCompany(companyRequest));
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
        CompanyRequest companyRequest = new CompanyRequest(20, "HansOOCL");
        Integer id = 21;
        //when
        Throwable exception = assertThrows(UpdateException.class,
                () -> companyService.updateCompany(id,companyRequest));
        //then
        assertEquals(new UpdateException("Update unsuccessfully!").getMessage(),exception.getMessage());

    }


    //todo
    @Test
    void should_return_specific_company_when_get_company_given_page_pageSize() {
        //given
        int PAGE = 1;
        int PAGE_SIZE = 5;
        Page<Company> result = new PageImpl<>(companies.subList(0, 5));
        given(companyRepository.findAll(PageRequest.of(PAGE, PAGE_SIZE))).willReturn(result);
        List<CompanyResponse> companyResponseTest = new ArrayList<>();
        for (Company company : result.toList()) {
            companyResponseTest.add(companyMapper.toCompanyResponse(company));
        }
        //when
        List<CompanyResponse> foundCompanies = companyService.getCompanyByPage(PAGE+1, PAGE_SIZE);

        //then
        assertNotNull(foundCompanies);
        assertEquals(companyResponseTest.size(), foundCompanies.size());
        assertEquals(companyResponseTest.get(0).getCompanyId(), foundCompanies.get(0).getCompanyId());
        assertEquals(companyResponseTest.get(4).getCompanyId(), foundCompanies.get(4).getCompanyId());
    }

    @Test
    void should_new_company_when_add_company_given_one_company() throws CreateException {
        //given
        CompanyRequest company = new CompanyRequest(20, "HansOOCL");
        given(companyRepository.save(companyMapper.toCompany(company))).willReturn(companyMapper.toCompany(company));
        //when
        CompanyResponse createdCompany = companyService.createCompany(company);
        //then
        assertEquals(companyMapper.toCompanyResponse(companyMapper.toCompany(company)).getCompanyId(), createdCompany.getCompanyId());
        assertEquals(companyMapper.toCompanyResponse(companyMapper.toCompany(company)).getCompanyName(), createdCompany.getCompanyName());
        assertEquals(companyMapper.toCompanyResponse(companyMapper.toCompany(company)).getEmployees(), createdCompany.getEmployees());
    }

    @Test
    void should_updated_when_update_employee_given_employee_message() throws UpdateException {
        //given
        int companyId = 1;
        CompanyRequest companyInfo = new CompanyRequest(companyId, "HansOOCL2");
        companyInfo.setEmployees(Arrays.asList(
                new Employee(1, "Hans", 24, "male", 5000),
                new Employee(2, "Amy", 22, "female", 9000)));
        given(companyRepository.findById(companyId)).willReturn(java.util.Optional.of(companyMapper.toCompany(companyInfo)));
        given(companyRepository.save(companyMapper.toCompany(companyInfo))).willReturn(companyMapper.toCompany(companyInfo));

        //when
        CompanyResponse company = companyService.updateCompany(companyId, companyInfo);

        //then
        assertEquals(companyMapper.toCompanyResponse(companyMapper.toCompany(companyInfo)).getCompanyId(),company.getCompanyId());
        assertEquals(companyMapper.toCompanyResponse(companyMapper.toCompany(companyInfo)).getCompanyName(),company.getCompanyName());
        assertEquals(companyMapper.toCompanyResponse(companyMapper.toCompany(companyInfo)).getEmployees(),company.getEmployees());
    }

    @Test
    void should_delete_employee_when_remove_employee_given_id() throws CreateException, FindException {
        //given
        Integer companyId = 25;
        Company expectedCompany = new Company(25, "VickiOOCL");
        given(companyRepository.findById(companyId)).willReturn(Optional.of(expectedCompany));
        //when
        companyService.removeCompany(companyId);

        //then
        verify(companyRepository, times(1)).deleteById(eq(companyId));
    }
}
