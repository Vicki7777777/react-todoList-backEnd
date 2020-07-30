package com.thoughtworks.springbootemployee.integrationTest;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respority.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

    @AfterEach
    public void delete(){
        companyRepository.deleteAll();
    }

    @Test
    void should_return_companies_when_hit_get_companies_endpoint_given_nothing() throws Exception {
        //given
        Company company = new Company("jessie", null);
        Company savedCompany = companyRepository.save(company);
        Employee employee = new Employee(1, "Hans", 24, "male", 5000);
        employee.setCompanyId(savedCompany.getCompanyId());
        savedCompany.setEmployees(asList(employee));
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].companyName").value("jessie"));
    }
}
