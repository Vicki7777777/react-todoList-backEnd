package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.Exception.CreateException;
import com.thoughtworks.springbootemployee.Exception.FindException;
import com.thoughtworks.springbootemployee.Exception.UpdateException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.respority.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;

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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    List<Employee> employees = new LinkedList<>();

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

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
        Page<Employee> result = new PageImpl<>(employees.subList(0, 4));
        given(employeeRepository.findAll(PageRequest.of(PAGE, PAGE_SIZE))).willReturn((Page<Employee>) result);

        //when
        List<Employee> foundEmployees = employeeService.getEmployeeByPage(PAGE+1, PAGE_SIZE);

        //then
        assertEquals(result.toList(), foundEmployees);
    }

    @Test
    void should_return_male_employees_when_find_employees_given_male() {
        //given
        String gender = "male";
        List<Employee> expectedEmployees = employees.stream().filter(employee -> employee.getGender().equals("male")).collect(Collectors.toList());
        given(employeeRepository.findByGender(gender)).willReturn(expectedEmployees);

        //when
        List<Employee> employeeList = employeeService.getEmployeeByGender(gender);

        //then
        assertEquals(expectedEmployees, employeeList);

    }

    @Test
    void should_add_employee_when_create_employee_given_employee() throws CreateException {
        //given
        Employee expectedEmployee = new Employee(8, "Ace", 23, "male", 9900);
        given(employeeRepository.save(expectedEmployee)).willReturn(expectedEmployee);

        //when
        Employee createdEmployee = employeeService.createEmployee(expectedEmployee);

        //then
        assertEquals(expectedEmployee, createdEmployee);
    }

    @Test
    void should_updated_when_update_employee_given_employee_message() throws UpdateException {
        //given
        Employee employeeInfo = new Employee(1, "Hans", 29, "female", 50000);
        Integer employeeId = 1;
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employeeInfo));
        given(employeeRepository.save(employeeInfo)).willReturn(employeeInfo);

        //when
        Employee employee = employeeService.updateEmployee(employeeId, employeeInfo);

        //then
        assertEquals(employee, employeeInfo);
    }

    @Test
    void should_delete_employee_when_remove_employee_given_id() throws CreateException, FindException {
        //given
        int employeeId = 20;
        Employee expectedEmployees = new Employee(20, "cc", 23, "male", 9900);
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(expectedEmployees));
        //when
        employeeService.removeEmployee(employeeId);
        //then
        verify(employeeRepository, times(1)).deleteById(eq(employeeId));
    }

    @Test
    void should_return_wrong_message_when_create_employee_given_existent_employeeId() throws CreateException {
        //given
        Employee employee = new Employee(8, "cc", 23, "male", 9000);
        int id = 8;
        given(employeeRepository.findById(id)).willReturn(Optional.of(employee));
        //when
        Throwable exception = assertThrows(CreateException.class,
                () -> employeeService.createEmployee(employee));
        //then
        assertEquals(new FindException("Create unsuccessfully!").getMessage(),exception.getMessage());

    }

    @Test
    void should_return_wrong_message_when_delete_employee_given_non_existent_employeeId() throws FindException {
        //given
        Integer id = 100000;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());
        //when
        Throwable exception = assertThrows(FindException.class,
                () -> employeeService.removeEmployee(id));
        //then
        assertEquals(new FindException("No Such Company!").getMessage(),exception.getMessage());

    }

    @Test
    void should_return_wrong_message_when_update_employee_given_error_employeeId()  throws UpdateException {
        //given
        Employee employee = new Employee(8, "cc", 23, "male", 9000);
        Integer id = 21;
        //when
        Throwable exception = assertThrows(UpdateException.class,
                () -> employeeService.updateEmployee(id,employee));
        //then
        assertEquals(new UpdateException("Update unsuccessfully!").getMessage(),exception.getMessage());

    }

}
