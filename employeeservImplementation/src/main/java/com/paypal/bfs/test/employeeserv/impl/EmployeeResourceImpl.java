package com.paypal.bfs.test.employeeserv.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exception.EmployeeProgramException;
import com.paypal.bfs.test.employeeserv.service.impl.EmployeeServiceImpl;
import com.paypal.bfs.test.employeeserv.util.EmployeeValidationUtils;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

	@Autowired
	EmployeeServiceImpl employeeServiceImpl;
	
    @Override
    public ResponseEntity<Employee> findEmployeeById(Long id) {
    	Employee employee = new Employee();
       try{
           employee = employeeServiceImpl.findEmployeeById(id);
           if(employee.getId() == null) {
        	   return new ResponseEntity<>(employee, HttpStatus.NOT_FOUND);
           }
       } catch(Exception e){
    	   throw new ResponseStatusException(
    		          HttpStatus.NOT_FOUND, e.getMessage());

       }
       
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

	@Override
	public ResponseEntity createEmployee(Employee employee) {
		try {
		EmployeeValidationUtils.validateEmployee(employee);//Need to validate the date format as
		//well but due to time constraint leaving it
		} catch(EmployeeProgramException e){
			 throw new EmployeeProgramException(e.getMessage()); 
		}
		employeeServiceImpl.createEmployee(employee);
		return new ResponseEntity(HttpStatus.OK);
	}
}
