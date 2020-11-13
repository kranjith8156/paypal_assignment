package com.paypal.bfs.test.employeeserv.util;

import org.apache.commons.lang.StringUtils;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exception.EmployeeProgramException;

public class EmployeeValidationUtils {
	
	public static void validateEmployee( Employee field) throws EmployeeProgramException{
		validateFirstName(field);
		validateLastName(field);
		validateDob(field);
		if(field.getAddress() != null)
		  validateAddress(field.getAddress());
	}
	
	private static void validateAddress(Address address) throws EmployeeProgramException {
		validateLine1(address);
		validateCity(address);
		validateCountry(address);
		validateZipCode(address);
	}
	
	private static void validateFirstName( Employee field) throws EmployeeProgramException {
		if(StringUtils.isEmpty(field.getFirst_name()))
			throw new EmployeeProgramException("First name field is missing");
	}
	
	private static void validateLastName(  Employee field) throws EmployeeProgramException {
		if(StringUtils.isEmpty(field.getLast_name()))
			throw new EmployeeProgramException("Last name field is missing");
	}
	
	private static void validateDob(  Employee field) throws EmployeeProgramException {
		if(StringUtils.isEmpty(field.getDob()))
			throw new EmployeeProgramException("Date of birth field is missing");
	}
	
	private static void validateLine1(Address field) throws EmployeeProgramException {
		if(StringUtils.isEmpty(field.getLine1()))
			throw new EmployeeProgramException("Line1 field is missing");
	}
	
	private static void validateCity(  Address field) throws EmployeeProgramException {
		if(StringUtils.isEmpty(field.getCity()))
			throw new EmployeeProgramException("City field is missing");
	}
	
	private static void validateCountry(  Address field) throws EmployeeProgramException {
		if(StringUtils.isEmpty(field.getCountry()))
			throw new EmployeeProgramException("Country field is missing");
	}
	
	private static void validateZipCode(  Address field) throws EmployeeProgramException {
		if(StringUtils.isEmpty(field.getZip_code().toString()))
			throw new EmployeeProgramException("Zip code field is missing");
	}
	

}
