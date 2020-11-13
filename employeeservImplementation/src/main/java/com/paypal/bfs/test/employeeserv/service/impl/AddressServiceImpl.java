package com.paypal.bfs.test.employeeserv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.serivce.AddressService;

@Service
public class AddressServiceImpl implements AddressService{
	
	private static String updateAddressSql = "UPDATE address set line1 = ?, line2 = ?, city = ?, country = ?, zip_code = ? where addressId = ?";
	private static String selectAddressSql= "SELECT * FROM address where addressId = ?";

	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public void updateAddress(Address address) {
		Object[] params = new Object[] { address.getLine1(), 
        		address.getLine2(), address.getCity(), address.getCountry(), address.getZip_code(), address.getAddressId() };
        		jdbcTemplate.update(updateAddressSql, params);
	}
	
	@Override
	public Address findAddressById(Long id) {
		Object[] params = new Object[] { id };
        return jdbcTemplate.queryForObject(selectAddressSql, params,new BeanPropertyRowMapper<>(Address.class));
	}

}
