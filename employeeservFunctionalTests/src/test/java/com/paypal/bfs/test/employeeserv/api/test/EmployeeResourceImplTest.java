package com.paypal.bfs.test.employeeserv.api.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exception.EmployeeProgramException;
import com.paypal.bfs.test.employeeserv.impl.EmployeeResourceImpl;
import com.paypal.bfs.test.employeeserv.service.impl.EmployeeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeResourceImplTest {
	
	@InjectMocks
	EmployeeResourceImpl employeeResourceImpl;
	
	@Mock
	EmployeeServiceImpl employeeServiceImpl;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testFindEmployeeById(){
		Employee employee = new Employee();
		employee.setId(1);
		Mockito.when(employeeServiceImpl.findEmployeeById(Mockito.anyLong())).
		thenReturn(employee);
		assertEquals(HttpStatus.OK, employeeResourceImpl.findEmployeeById(Mockito.anyLong()).getStatusCode());
	}
	
	@Test
	public void testFindEmployeeByIdEmpty(){
		Employee employee = new Employee();
		Mockito.when(employeeServiceImpl.findEmployeeById(Mockito.anyLong())).
		thenReturn(employee);
		assertEquals(HttpStatus.NOT_FOUND, employeeResourceImpl.findEmployeeById(Mockito.anyLong()).getStatusCode());
	}

	@Test(expected=ResponseStatusException.class)
	public void testFindEmployeeByIdException(){
		Mockito.when(employeeServiceImpl.findEmployeeById(Mockito.anyLong())).
		thenReturn(null);
		assertEquals(HttpStatus.NOT_FOUND, employeeResourceImpl.findEmployeeById(Mockito.anyLong()).getStatusCode());
	}
	
	@Test
	public void testCreateEmployee() {
		Address address = new Address();
		address.setAddressId(1);
		address.setCity("test");
		address.setLine1("line1");
		address.setLine2("line2");
		address.setState("state");
		address.setCountry("country");
		address.setZip_code(00000);
		Employee employee = new Employee();
		employee.setAddress(address);
		employee.setDob("09/23/1989");
		employee.setFirst_name("first_name");
		employee.setLast_name("last_name");
		Mockito.doNothing().when(employeeServiceImpl).createEmployee(employee);
		assertEquals(HttpStatus.OK, employeeResourceImpl.createEmployee(employee).getStatusCode());

	}
	
	@Test(expected=EmployeeProgramException.class)
	public void testCreateEmployeeException() {
		Address address = new Address();
		address.setAddressId(1);
		address.setZip_code(00000);
		Employee employee = new Employee();
		employee.setAddress(address);
		employee.setDob("09/23/1989");
		employee.setFirst_name("first_name");
		employee.setLast_name("last_name");
		Mockito.doNothing().when(employeeServiceImpl).createEmployee(employee);

	}
}
