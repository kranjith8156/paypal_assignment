package com.paypal.bfs.test.employeeserv.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.serivce.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private static String selectEmployeeSql = "SELECT * FROM employee where id = ?";
	private static String insertEmployeeSql = "INSERT INTO employee (id, first_name, last_name, dob, addressId) "
			+ "VALUES (?, ?, ?, ?, ?)";
	private static String insertAddressSql = "INSERT INTO address (line1, line2, city, state, country, zip_code) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	
	private static String updateEmployeeSql = "UPDATE employee set first_name = ?, last_name = ?, dob = ?, addressId = ? where id = ? ";


	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AddressServiceImpl addressServiceImpl;

	@Override
	public Employee findEmployeeById(Long id) {
		Object[] params = new Object[] { id };
		
        return retrieveEmployee(params);
	}


	@Override
	public void createEmployee(Employee employee) {
		Long addressId = null;
		Employee existing =   findEmployeeById(employee.getId().longValue());

		if(employee.getAddress() != null && existing.getAddress() == null ) {
			addressId = createAddress( employee.getAddress());
		}

		   if(existing.getId() != null) {
			   
			   if(employee.getAddress() == null){
				   existing.setAddress(null);
			   }else if(existing.getAddress() != null) {
				   Address existingAddress = employee.getAddress();
				   addressId = existingAddress.getAddressId().longValue();
				   existingAddress.setLine1(employee.getAddress().getLine1());
				   existingAddress.setLine2(employee.getAddress().getLine2());
				   existingAddress.setCity(employee.getAddress().getCity());
				   existingAddress.setState(employee.getAddress().getState());
				   existingAddress.setCountry(employee.getAddress().getCountry());
				   existingAddress.setZip_code(employee.getAddress().getZip_code());
				   addressServiceImpl.updateAddress(existingAddress);
			   }
			   
			   existing.setFirst_name(employee.getFirst_name());
			   existing.setLast_name(employee.getLast_name());
			   existing.setDob(employee.getDob());
			   updateEmployee(employee,addressId);

		   return;
		   }

		
        Object[] params = new Object[] { employee.getId(), employee.getFirst_name(), 
        		employee.getLast_name(), employee.getDob(), addressId};
         
        int[] types = new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER };
		jdbcTemplate.update(insertEmployeeSql, params, types);
		
	}
	
	public Long createAddress(Address address) {

    	KeyHolder holder = new GeneratedKeyHolder();

    	jdbcTemplate.update(new PreparedStatementCreator() {           

    	                @Override
    	                public PreparedStatement createPreparedStatement(Connection connection)
    	                        throws SQLException {
    	                    PreparedStatement ps = connection.prepareStatement(insertAddressSql.toString(),
    	                        Statement.RETURN_GENERATED_KEYS); 
    	                    ps.setString(1, address.getLine1());
    	                    ps.setString(2, address.getLine2());
    	                    ps.setString(3, address.getCity());
    	                    ps.setString(4, address.getState());
    	                    ps.setString(5, address.getCountry());
    	                    ps.setInt(6, address.getZip_code());
    	                    return ps;
    	                }
    	            }, holder);

    	Long addressId = holder.getKey().longValue();
    	System.out.println("new Address Id: "+addressId);
    	return addressId;
	}

	@Override
	public void updateEmployee(Employee employee, Long addressId) {
		Object[] params = new Object[] { employee.getFirst_name(), 
        		employee.getLast_name(), employee.getDob(),addressId, employee.getId() };
		jdbcTemplate.update(updateEmployeeSql, params);

	}
	
	public Employee retrieveEmployee( Object[] params) {
		Map<String, Object> result = new HashMap<>();
		Employee employee = new Employee();

		try {
			result = (Map<String, Object>) jdbcTemplate
                .queryForMap(selectEmployeeSql, params);
		result.forEach((key, value) -> {
        	if(key.equals("FIRST_NAME"))
        		employee.setFirst_name(value.toString());
        	if(key.equals("ID"))
        		employee.setId(Integer.parseInt(value.toString()));
        	if(key.equals("LAST_NAME"))
        		employee.setLast_name(value.toString());
        	if(key.equals("DOB"))
        		employee.setDob(value.toString());
        	if(key.equals("ADDRESSID") && value != null) {
        		Address address = addressServiceImpl.findAddressById(Long.parseLong(value.toString()));
        		employee.setAddress(address);
        	}
        });
		} catch(DataAccessException de) {
			return new Employee();
		}
        return employee;
	}
	
}
