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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.service.impl.AddressServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceImplTest {

	@InjectMocks
	AddressServiceImpl addressServiceImpl;
	
	@Mock
	JdbcTemplate jdbcTemplate;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testUpdateAddress() {
		Address address = new Address();
		address.setAddressId(1);
		address.setCity("test");
		address.setLine1("line1");
		address.setLine2("line2");
		address.setState("state");
		address.setCountry("country");
		address.setZip_code(00000);	
		addressServiceImpl.updateAddress(address);
	}
	
	@Test
	public void testfindAddressById(){
		Address address = new Address();
		address.setAddressId(1);
		address.setCity("test");
		address.setLine1("line1");
		address.setLine2("line2");
		address.setState("state");
		address.setCountry("country");
		address.setZip_code(00000);	
		Mockito.when(jdbcTemplate.queryForObject(Mockito.anyString(), 
				Mockito.any(Object[].class),Mockito.any(BeanPropertyRowMapper.class))).thenReturn(address);
		Address result = addressServiceImpl.findAddressById(address.getAddressId().longValue());
		assertEquals("test", result.getCity());

	}
}
