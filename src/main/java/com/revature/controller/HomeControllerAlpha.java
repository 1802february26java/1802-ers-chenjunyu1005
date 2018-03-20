package com.revature.controller;

import javax.servlet.http.HttpServletRequest;

import com.revature.model.Employee;
import com.revature.service.EmployeeService;
import com.revature.service.EmployeeServiceAlpha;

public class HomeControllerAlpha implements HomeController{

	@Override
	public String showEmployeeHome(HttpServletRequest request) {
		Employee employeeInformation = EmployeeServiceAlpha.getInstance().getEmployeeInformation(new Employee
				(Integer.parseInt(request.getParameter("userTypeID"))));

		
		if(employeeInformation.getEmployeeRole().getType().equals("EMPLOYEE")){
			return "home1.html";
		}
		return "home2.html";
	}

}
