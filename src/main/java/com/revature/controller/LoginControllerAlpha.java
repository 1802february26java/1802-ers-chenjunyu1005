package com.revature.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.revature.ajax.ClientMessage;
import com.revature.model.Employee;
import com.revature.service.EmployeeServiceAlpha;

public class LoginControllerAlpha implements LoginController {

	@Override
	public Object login(HttpServletRequest request) {
		if(request.getMethod().equals("GET")){
		return "login.html";
		}
//		boolean usernameTaken = EmployeeServiceAlpha.getInstance().isUsernameTaken(new Employee(
//				request.getParameter("username")
//				));
		Employee authenticate = EmployeeServiceAlpha.getInstance().authenticate(new Employee(
				request.getParameter("username"),
				request.getParameter("password"))
				);
		
		if(authenticate==null){
			return new ClientMessage("AUTHENTICATION FAILED");
		}
		
		request.getSession().setAttribute("authenticate",authenticate);
		return authenticate;
	}

	@Override
	public String logout(HttpServletRequest request) {

		request.getSession().invalidate();
//		request.getSession().setAttribute("authenticate", null);
		return "login.html";
	}

}
