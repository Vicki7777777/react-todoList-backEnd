package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respority.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeServiceTest {
    List<Employee> employees = new LinkedList<>();

    @BeforeAll
    public void createCompanies() {
        employees.add(new Employee(1, "Hans", 24, "male", 5000));
        employees.add(new Employee(2, "Amy", 22, "female", 9000));
        employees.add(new Employee(3, "Ray", 28, "male", 10000));
        employees.add(new Employee(4, "Sky", 27, "female", 8000));
        employees.add(new Employee(5, "Hovees", 25, "male", 7000));
        employees.add(new Employee(6, "Mandy", 22, "male", 8888));
        employees.add(new Employee(7, "Ace", 23, "male", 9000));
    }

    @Test
    void should_return_employees_when_get_all_employees_given_no_paramters() {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        given(employeeRepository.findAll()).willReturn(employees);

        //when
        List<Employee> selectedEmployees = employeeService.getAllEmployees();

        //then
        assertEquals(employees, selectedEmployees);
    }

    @Test
    void should_return_specific_employee_when_get_employee_given_id() {
        //given
        Integer id = 1;
        Employee expectedEmployee = employees.get(0);
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        given(employeeRepository.findById(id)).willReturn(java.util.Optional.ofNullable(expectedEmployee));

        //when
        Employee selectedEmployees = employeeService.getEmployeeById(id);

        //then
        assertEquals(expectedEmployee, selectedEmployees);
    }

    @Test
    void should_return_specific_employee_when_get_employee_given_page_pageSize() {
        //given
        int PAGE = 1;
        int PAGE_SIZE = 5;
        PageImpl<Employee> result = new PageImpl<>(employees.subList(0, 4));
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        given(employeeRepository.findAll(PageRequest.of(PAGE, PAGE_SIZE))).willReturn((PageImpl<Employee>) result);

        //when
        Page foundEmployees = employeeService.getEmployeeByPage(PAGE, PAGE_SIZE);

        //then
        assertEquals(result, foundEmployees);
    }

//    @Test
//    void should_return_male_employee_when_find_employee_given_page_pageSize()
}
