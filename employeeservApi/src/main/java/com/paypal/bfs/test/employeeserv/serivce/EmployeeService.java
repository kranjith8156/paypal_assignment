package com.paypal.bfs.test.employeeserv.serivce;

import com.paypal.bfs.test.employeeserv.api.model.Employee;

public interface EmployeeService {
	Employee findEmployeeById(Long id);
	void createEmployee(Employee  employee);
	void updateEmployee(Employee  employee, Long addressId);

}
