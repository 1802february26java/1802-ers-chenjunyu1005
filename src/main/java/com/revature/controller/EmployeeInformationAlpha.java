package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

import com.revature.ajax.ClientMessage;
import com.revature.model.Employee;
import com.revature.model.EmployeeRole;
import com.revature.service.EmployeeService;
import com.revature.service.EmployeeServiceAlpha;

public class EmployeeInformationAlpha implements EmployeeInformationController{
	/**
	 * Registers an employee.
	 * 
	 * This operation can only be performed by managers.
	 * 
	 * It should return a message stating whether the user was successfully
	 * registered or not.
	 */
	@Override
	public Object registerEmployee(HttpServletRequest request) {

		if(request.getParameter("userType").equals("MANAGER")){
			boolean createEmployee = EmployeeServiceAlpha.getInstance().createEmployee
					(
							new Employee(0,
									request.getParameter("firstname"),
									request.getParameter("lastname"),
									request.getParameter("username"),
									request.getParameter("password"),
									request.getParameter("email"),
									new EmployeeRole(1,"EMPLOYEE")
									));
			if(createEmployee){
				new ClientMessage("Succefull");
			}else{
				new ClientMessage("Fail");
			}
		}
		else{
			new ClientMessage("Do not have the permission to register a Employee");
		}
		return request;


		
	}

	@Override
	public Object updateEmployee(HttpServletRequest request) {
		
		 boolean updateEmployeeInformation = EmployeeServiceAlpha.getInstance().updateEmployeeInformation(
				new Employee(request.getParameter("firstname"),
						request.getParameter("lastname"),
						request.getParameter("username"),
						request.getParameter("email")
				));
		 if(updateEmployeeInformation){
			 return new ClientMessage("Succefully updated");
		 }else{
			 return new ClientMessage("Fails");
		 }
	}

	@Override
	public Object viewEmployeeInformation(HttpServletRequest request) {
		Employee employeeInformation = EmployeeServiceAlpha.getInstance().getEmployeeInformation(
				new Employee(Integer.parseInt(request.getParameter("id")))
				);
		return employeeInformation;
	}

	@Override
	public Object viewAllEmployees(HttpServletRequest request) {
		return EmployeeServiceAlpha.getInstance().getAllEmployeesInformation();
		
	}

	@Override
	public Object usernameExists(HttpServletRequest request) {
		
		boolean check= EmployeeServiceAlpha.getInstance().isUsernameTaken(new Employee(0
				,request.getParameter("firstname"),
				request.getParameter("lastname"),
				request.getParameter("username"),
				request.getParameter("password"),
				request.getParameter("email"),
				new EmployeeRole(request.getParameter("roletype"))
				));
		
		if(check){
			return new ClientMessage("Usename is valid");
		}else{
			return new ClientMessage("Name Existed");
		}
	}

}
