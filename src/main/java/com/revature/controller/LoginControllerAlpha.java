package com.revature.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.revature.model.Employee;
import com.revature.service.EmployeeServiceAlpha;

public class LoginControllerAlpha implements LoginController {

	@Override
	public Object login(HttpServletRequest request) {
		/*if(request.getMethod().equals("Get")){
		return "login.html";
		}*/
		Employee authenticate = EmployeeServiceAlpha.getInstance().authenticate(new Employee(
				request.getParameter("username"),
				request.getParameter("password"))
				);
		
		if(authenticate==null){
			System.out.println("Fails");
			return "login.html";
		}
		request.getSession().setAttribute("authenticate",authenticate);
		return "home.html";
//		if(authenticate.getEmployeeRole().getType().equals("EMPLOYEE")){
//		return "home.html";
//		}
//		else {
//		 return"home2.html";
//		}
//		return authenticate;
	}

	@Override
	public String logout(HttpServletRequest request) {

		request.getSession().invalidate();
		return "login.html";
	}

}
