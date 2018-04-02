package com.revature.controller;

import javax.servlet.http.HttpServletRequest;


import com.revature.model.Employee;

public class HomeControllerAlpha implements HomeController{

	@Override
	public String showEmployeeHome(HttpServletRequest request) {

           
		Employee employeeInformation=(Employee)request.getSession().getAttribute("authenticate");
		if(employeeInformation==null){
			return "login.html";
		}
		if(employeeInformation.getEmployeeRole().getType().equals("EMPLOYEE")){
			return "employeeHome.html";
		}else {
		return "managerHome.html";
		}
		
	}

}
