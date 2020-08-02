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

import static java.util.Collections.emptyList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class employeeIntegrationTest {
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
    void should_return_employees_when_hit_get_employees_endpoint_given_nothing() throws Exception{
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(6)))
                .andExpect(jsonPath("$[0].id").value(employee1.getId()))
                .andExpect(jsonPath("$[0].name").value(employee1.getName()))
                .andExpect(jsonPath("$[0].gender").value(employee1.getGender()))
                .andExpect(jsonPath("$[0].age").value(employee1.getAge()))
                .andExpect(jsonPath("$[0].companyId").value(employee1.getCompanyId()));
    }
    @Test
    void should_return_specific_employee_when_hit_get_employees_endpoint_given_employee_id() throws Exception {
        mockMvc.perform(get("/employees/{id}", employee1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee1.getId()))
                .andExpect(jsonPath("$.name").value(employee1.getName()))
                .andExpect(jsonPath("$.gender").value(employee1.getGender()))
                .andExpect(jsonPath("$.age").value(employee1.getAge()))
                .andExpect(jsonPath("$.companyId").value(employee1.getCompanyId()));
    }
    @Test
    void should_return_employees_when_get_employees_by_page_given_page_and_page_size() throws Exception {
        mockMvc.perform(get("/employees?page=1&pageSize=5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id").value(employee1.getId()))
                .andExpect(jsonPath("$[4].id").value(employee5.getId()))
                .andExpect(jsonPath("$[0].name").value(employee1.getName()))
                .andExpect(jsonPath("$[0].gender").value(employee1.getGender()))
                .andExpect(jsonPath("$[0].age").value(employee1.getAge()))
                .andExpect(jsonPath("$[0].salary").value(employee1.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(employee1.getCompanyId()));
    }

    @Test
    void should_return_male_employees_when_find_employees_given_male() throws Exception {
        mockMvc.perform(get("/employees?gender=male").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(4)))
                .andExpect(jsonPath("$[0].id").value(employee1.getId()))
                .andExpect(jsonPath("$[3].id").value(employee6.getId()))
                .andExpect(jsonPath("$[0].name").value(employee1.getName()))
                .andExpect(jsonPath("$[0].gender").value(employee1.getGender()))
                .andExpect(jsonPath("$[0].age").value(employee1.getAge()))
                .andExpect(jsonPath("$[0].salary").value(employee1.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(employee1.getCompanyId()));
    }

    @Test
    void should_add_employee_when_create_employee_given_employee() throws Exception {
        //when
        mockMvc.perform(post("/employees/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{ \"id\": \"1000\",\"name\": \"test\", \"age\": 18, \"gender\": \"male\", \"salary\": 800000 ,\"company_Id\": %s}",company1.getCompanyId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.salary").value(800000));
    }
    @Test
    void should_updated_when_update_employee_given_employee_message() throws Exception {
        //when
        mockMvc.perform(put("/employees/{id}", employee1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{ \"id\": \"%s\",\"name\": \"test\", \"age\": 18, \"gender\": \"male\", \"salary\": 800000, \"company_Id\": %s }",
                        employee1.getId(),employee1.getCompanyId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee1.getId()))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.salary").value(800000))
                .andExpect(jsonPath("$.companyId").value(company1.getCompanyId()));
    }

    @Test
    void should_delete_employee_when_remove_employee_given_id() throws Exception {
        //when
        mockMvc.perform(delete("/employees/{id}", employee1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertNull(employeeRepository.findById(employee1.getId()).orElse(null));
    }

}
