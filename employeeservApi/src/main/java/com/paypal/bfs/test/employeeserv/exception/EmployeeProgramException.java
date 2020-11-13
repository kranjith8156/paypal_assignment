package com.paypal.bfs.test.employeeserv.exception;

public class EmployeeProgramException  extends RuntimeException{
	

	private static final long serialVersionUID = 1L;

	public EmployeeProgramException(String message){
		super(message);
	}
	
	public EmployeeProgramException(String message, Throwable throwable){
		super(message,throwable);
	}

}
