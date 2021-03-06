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
		if (request.getMethod().equals("GET")) {
			return "register.html";
		}
		Employee employeeInformation=(Employee)request.getSession().getAttribute("authenticate");
		if(employeeInformation==null){
			return "login.html";
		}
		
		if(employeeInformation.getEmployeeRole().getType().equals("EMPLOYEE")){
			return "404.html";
		}
			
			boolean createEmployee = EmployeeServiceAlpha.getInstance().createEmployee
					(new Employee(0,
									request.getParameter("firstName"),
									request.getParameter("lastName"),
									request.getParameter("username"),
									request.getParameter("password"),
									request.getParameter("email"),
									new EmployeeRole(1,"EMPLOYEE")
									));
			        if(createEmployee){
				      return new ClientMessage("REGISTRATION SUCCESSFUL");
			       }else{
				    return new ClientMessage("Fail");
			          }
			 
		     }
		
			
//			return new ClientMessage("Do not have the permission to register a Employee");

	

	@Override
	public Object updateEmployee(HttpServletRequest request) {
		Employee employeeInformation=(Employee)request.getSession().getAttribute("authenticate");
		if (request.getMethod().equals("GET")) {
			return "updateinformation.html";
		}
		if(employeeInformation==null){
			return "login.html";
		}

		 boolean updateEmployeeInformation = EmployeeServiceAlpha.getInstance().updateEmployeeInformation(
				new Employee(employeeInformation.getId(),
						request.getParameter("firstName"),
						request.getParameter("lastName"),
						request.getParameter("username"),
						request.getParameter("email")

				));
		 if(updateEmployeeInformation){
			 return new ClientMessage("Succefully Updated");
		 }else{
			 return new ClientMessage("Fails");
		 }
	}

	@Override
	public Object viewEmployeeInformation(HttpServletRequest request) {
		Employee employeeInformation=(Employee)request.getSession().getAttribute("authenticate");
		if(employeeInformation==null){
			return "login.html";
		}
		
         //Get the corresonding Empoyee id 
		if(request.getParameter("fetch") == null) {
			return "view-employee.html";
		}else{
		return  EmployeeServiceAlpha.getInstance().getEmployeeInformation(
				new Employee(employeeInformation.getId())
				);	
		}
	}

	@Override
	public Object viewAllEmployees(HttpServletRequest request) {
		Employee employeeInformation=(Employee)request.getSession().getAttribute("authenticate");

		if(employeeInformation==null){
			return "login.html";
		}
		
		if(employeeInformation.getEmployeeRole().getType().equals("EMPLOYEE")){
		return "404.html";
		}
		if(request.getParameter("fetch") == null) {
			return "viewAllEmployee.html";
		}
		return EmployeeServiceAlpha.getInstance().getAllEmployeesInformation();
		}
//		return new ClientMessage("Only Manager can see");
	

	@Override
	public Object usernameExists(HttpServletRequest request) {
		if(request.getMethod().equals("GET")){
			return "checkUsername.html";
		}
		
		
		if(EmployeeServiceAlpha.getInstance().isUsernameTaken(new Employee(request.getParameter("username")))){
		 return new ClientMessage("Username existed already");
	}else {
		 return new ClientMessage("Valid username");
	}
		
	}

}
