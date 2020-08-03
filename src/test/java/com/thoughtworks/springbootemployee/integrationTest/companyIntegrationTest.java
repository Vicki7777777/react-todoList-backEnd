package com.thoughtworks.springbootemployee.integrationTest;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respority.CompanyRepository;
import com.thoughtworks.springbootemployee.respority.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void deleteAllData() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    Company company1 = new Company("OOCL", null);
    Company company2 = new Company("CargoSmart", null);
    Company company3 = new Company("Alibaba", null);
    Company company4 = new Company("Gree", null);
    Company company5 = new Company("Baidu", null);


    Employee employee1;
    Employee employee2;
    Employee employee3;
    Employee employee4;
    Employee employee5;
    Employee employee6;

    @BeforeEach
    public void createCompanies() {
        company1 = companyRepository.save(company1);
        company2 = companyRepository.save(company2);
        company3 = companyRepository.save(company3);
        company4 = companyRepository.save(company4);
        company5 = companyRepository.save(company5);

        employee1 = new Employee(1, "Hans", 24, "male", 5000, company1.getCompanyId());
        employee2 = new Employee(2, "Amy", 22, "female", 9000, company1.getCompanyId());
        employee3 = new Employee(3, "Ray", 28, "male", 10000, company2.getCompanyId());
        employee4 = new Employee(4, "Sky", 27, "female", 8000, company3.getCompanyId());
        employee5 = new Employee(5, "Hovees", 25, "male", 7000, company4.getCompanyId());
        employee6 = new Employee(6, "Mandy", 22, "male", 8888, company5.getCompanyId());

        company1= companyRepository.save(company1);
        company2=companyRepository.save(company2);
        company3=companyRepository.save(company3);
        company4=companyRepository.save(company4);
        company5=companyRepository.save(company5);
        employee1=employeeRepository.save(employee1);
        employee2=employeeRepository.save(employee2);
        employee3=employeeRepository.save(employee3);
        employee4=employeeRepository.save(employee4);
        employee5=employeeRepository.save(employee5);
        employee6=employeeRepository.save(employee6);
    }

    @Test
    void should_return_companies_when_hit_get_companies_endpoint_given_nothing() throws Exception {
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(5)))
                .andExpect(jsonPath("$[1].companyName").value(company2.getCompanyName()))
                .andExpect(jsonPath("$[1].employees[0].id").value(employee3.getId()))
                .andExpect(jsonPath("$[1].employees[0].name").value(employee3.getName()))
                .andExpect(jsonPath("$[1].employees[0].gender").value(employee3.getGender()))
                .andExpect(jsonPath("$[1].employees[0].age").value(employee3.getAge()))
                .andExpect(jsonPath("$[1].employees[0].companyId").value(employee3.getCompanyId()));
    }

    @Test
    void should_return_specific_company_when_hit_get_companies_endpoint_given_company_id() throws Exception {
        mockMvc.perform(get("/companies/{id}", company1.getCompanyId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employees",hasSize(2)))
                .andExpect(jsonPath("$.companyName").value(company1.getCompanyName()))
                .andExpect(jsonPath("$.employees[0].id").value(employee1.getId()))
                .andExpect(jsonPath("$.employees[0].name").value(employee1.getName()))
                .andExpect(jsonPath("$.employees[0].gender").value(employee1.getGender()))
                .andExpect(jsonPath("$.employees[0].age").value(employee1.getAge()))
                .andExpect(jsonPath("$.employees[0].companyId").value(employee1.getCompanyId()));
    }

    @Test
    void should_return_specific_company_employees_when_hit_get_companies_endpoint_given_company_id() throws Exception {
        mockMvc.perform(get("/companies/{id}/employees", company2.getCompanyId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(employee3.getName()))
                .andExpect(jsonPath("$[0].age").value(employee3.getAge()))
                .andExpect(jsonPath("$[0].gender").value(employee3.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee3.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(employee3.getCompanyId()));
    }

    @Test
    void should_return_companies_when_get_companies_by_page_given_page_and_page_size() throws Exception {
        mockMvc.perform(get("/companies?page=1&pageSize=5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].companyName").value(company1.getCompanyName()))
                .andExpect(jsonPath("$[1].companyName").value(company2.getCompanyName()))
                .andExpect(jsonPath("$[2].companyName").value(company3.getCompanyName()))
                .andExpect(jsonPath("$[3].companyName").value(company4.getCompanyName()))
                .andExpect(jsonPath("$[4].companyName").value(company5.getCompanyName()))
                .andExpect(jsonPath("$[0].employees[0].id").value(employee1.getId()))
                .andExpect(jsonPath("$[0].employees[0].name").value(employee1.getName()))
                .andExpect(jsonPath("$[0].employees[0].gender").value(employee1.getGender()))
                .andExpect(jsonPath("$[0].employees[0].age").value(employee1.getAge()))
                .andExpect(jsonPath("$[0].employees[0].salary").value(employee1.getSalary()))
                .andExpect(jsonPath("$[0].employees[0].companyId").value(employee1.getCompanyId()));
    }

    @Test
    void should_new_company_when_add_company_given_one_company() throws Exception {
        //given
        String companyBody = "{\n" +
                "        \"companyName\": \"cc\",\n" +
                "         \"companyId\":1000\n" +
                "}";
        mockMvc.perform(post("/companies/").contentType(MediaType.APPLICATION_JSON).content(companyBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyName").value("cc"));
    }


    @Test
    void should_updated_when_update_employee_given_employee_message() throws Exception {
        //given
        mockMvc.perform(put("/companies/{id}",company1.getCompanyId()).contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{ \"companyId\": %s, \"companyName\": \"1\" }",
                        company1.getCompanyId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("1"));
    }

    @Test
    void should_delete_employee_when_remove_employee_given_id() throws Exception {
        mockMvc.perform(delete("/companies/"+company1.getCompanyId()))
                .andExpect(status().isOk());
    }

}
