package com.paypal.bfs.test.employeeserv.api.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.service.impl.AddressServiceImpl;
import com.paypal.bfs.test.employeeserv.service.impl.EmployeeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {
	
	@Mock
     JdbcTemplate jdbcTemplate;
	
	@Mock
	 AddressServiceImpl addressServiceImpl;
	
	@InjectMocks
	EmployeeServiceImpl employeeServiceImpl;
	
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testFindEmployeeById() {
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
		Map<String, Object> result = new HashMap<>();
		result.put("FIRST_NAME", employee.getFirst_name());
		result.put("LAST_NAME", employee.getLast_name());
		result.put("DOB", employee.getDob());
		result.put("ADDRESSID", address.getAddressId());

        Mockito.when(jdbcTemplate.queryForMap("SELECT * FROM employee where id = ?", 
        		new Long(10))).thenReturn(result);
        employeeServiceImpl.findEmployeeById(new Long(10));
		assertEquals("first_name", employeeServiceImpl.findEmployeeById(new Long(10)).getFirst_name());

	}
	
	@Test
	public void testUpdateEmployee(){
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
		employeeServiceImpl.updateEmployee(employee, new Long(10));	
	}
	
	@Test
	public void testCreateEmployee() {
		Employee employee = new Employee();
		employee.setId(10);
		employee.setAddress(null);
		employee.setDob("09/23/1989");
		employee.setFirst_name("first_name");
		employee.setLast_name("last_name");
		Map<String, Object> result = new HashMap<>();
		result.put("FIRST_NAME", employee.getFirst_name());
		result.put("LAST_NAME", employee.getLast_name());
		result.put("DOB", employee.getDob());
        Mockito.when(jdbcTemplate.queryForMap("SELECT * FROM employee where id = ?", 
        		new Long(10))).thenReturn(result);
        employeeServiceImpl.findEmployeeById(new Long(10));
        Mockito.when(jdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), 
        		Mockito.any(KeyHolder.class))).thenReturn(1);
		employeeServiceImpl.createEmployee(employee);
		
	}

}
