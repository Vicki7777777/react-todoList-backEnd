package com.thoughtworks.springbootemployee.integrationTest;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respority.CompanyRepository;
import com.thoughtworks.springbootemployee.respority.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class companyIntegrationTest {
    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public CompanyRepository companyRepository;
    @Autowired
    public EmployeeRepository employeeRepository;

    @AfterEach
    public void delete(){
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void should_return_companies_when_hit_get_companies_endpoint_given_nothing() throws Exception {
        //given
        Company company = new Company("jessie", null);
        Company companyTwo = new Company("vicki", null);
        Company savedCompany = companyRepository.save(company);
        companyRepository.save(companyTwo);
        Employee employee = new Employee(1, "Hans", 24, "male", 5000);
        employee.setCompanyId(savedCompany.getCompanyId());
        savedCompany.setEmployees(asList(employee));
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].companyName").value("jessie"))
                .andExpect(jsonPath("$.[1].companyName").value("vicki"));
    }

    @Test
    void should_return_specific_company_when_hit_get_companies_endpoint_given_company_id() throws Exception {
        //given
        Company company = new Company("jessie", null);
        Company savedCompany = companyRepository.save(company);
        Employee employee = new Employee(1, "Hans", 24, "male", 5000,savedCompany.getCompanyId());
        employeeRepository.save(employee);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}",savedCompany.getCompanyId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("jessie"));
//                .andExpect(jsonPath("$.[0].employees.[0].name").value("Hans"));
    }
}
