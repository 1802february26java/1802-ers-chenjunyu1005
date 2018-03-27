package com.revature.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.revature.ajax.ClientMessage;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;
import com.revature.model.ReimbursementType;
import com.revature.repository.EmployeeDAO;
import com.revature.service.ReimbursementSeriveAlpha;


/**
 * The ReimbursementController will consume the ReimbursementService
 * to provide CRUD operations for reimbursement requests.
 * 
 * Employees can submit requests, both regular and manager employees can see a 
 * specific reimbursement request, managers can see a whole list of reimbursements 
 * and finally, managers can approve or decline requests.
 * 
 * @author Revature LLC
 */
public class ReimbursementControllerAlpha implements ReimbursementController{
	private static Logger logger = Logger.getLogger(ReimbursementControllerAlpha.class);

	/**
	 * Creates a reimbursement request.
	 * 
	 * This operation is only performed by regular employees.
	 * 
	 * It should return a message stating whether the reimbursement request
	 * was successfully created or not.
	 */
	@Override
	public Object submitRequest(HttpServletRequest request) {
		
		Employee employeeInformation=(Employee)request.getSession().getAttribute("authenticate");

		if(employeeInformation==null){
			return "login.html";
		}
		
		if(employeeInformation.getEmployeeRole().getType().equals("EMPLOYEE")){
		boolean submitRequest = ReimbursementSeriveAlpha.getInstance().submitRequest(
				new Reimbursement(
						Double.parseDouble(request.getParameter("amount")),
						request.getParameter("description"),
						new Employee(employeeInformation.getId()),
						new ReimbursementType(request.getParameter("type"))
								));
		

		if(submitRequest){

			return 	new ClientMessage("Submit Success");
		}
		return new ClientMessage("Fails");
		}
		return new ClientMessage("Only Employee Can Submit");
	}

	/**
	 * Returns a single reimbursement request specified by the user.
	 * 
	 * This operation can be performed by regular and/or manager employees.
	 */

	@Override
	public Object singleRequest(HttpServletRequest request) {
		if(request.getMethod().equals("GET")){
			return "singleRequest.html";
		}
		
		return ReimbursementSeriveAlpha.getInstance().getSingleRequest(
				new Reimbursement(Integer.parseInt(request.getParameter("reimbursementId"))),
				new ReimbursementStatus(request.getParameter("status").toUpperCase()));

	}
	/**
	 * Returns a collection of reimbursement requests.
	 * 
	 * This operation can be performed by regular and/or manager employees.
	 */

	@Override
	public Object multipleRequests(HttpServletRequest request) {
		
		
	 Employee information = (Employee)request.getSession().getAttribute("authenticate");
	 
	
		if(information.getEmployeeRole().getType().equals("MANAGER")){
			if(request.getMethod().equals("GET")){
				 return "viewAllReim.html";
			 }

			if(request.getParameter("status").toUpperCase().equals("PENDING"))
				return ReimbursementSeriveAlpha.getInstance().getAllPendingRequests(
						);
			else if(request.getParameter("status").toUpperCase().equals("RESOVLED")){
				return ReimbursementSeriveAlpha.getInstance().getAllResolvedRequests();
			}
			return new ClientMessage("Not Valid");

		}else if(information.getEmployeeRole().getType().equals("EMPLOYEE")){
			if(request.getMethod().equals("GET")){
				 return "employeeViewReim.html";
			 }

			if(request.getParameter("status").toUpperCase().equals("PENDING")){
				return ReimbursementSeriveAlpha.getInstance().getUserPendingRequests(

						new Employee(Integer.parseInt(request.getParameter("id"))
								));
			}else if(request.getParameter("status").toUpperCase().equals("RESOVLED")){
				return ReimbursementSeriveAlpha.getInstance().getUserFinalizedRequests(new Employee(Integer.parseInt(request.getParameter("id")
						)));	

			}	
			return new ClientMessage("Not Valid");


		}
		return null;

	}

	/**
	 * Updates the status of a reimbursement request.
	 * 
	 * This operation can only be performed by managers.
	 * 
	 * It should return a message stating that the reimbursement request
	 * was successfully updated or not.
	 */
	@Override
	public Object finalizeRequest(HttpServletRequest request) {
		 Employee information = (Employee)request.getSession().getAttribute("authenticate");
//			System.out.println(request.getParameter("status").toUpperCase());

		if(information.getEmployeeRole().getType().equals("MANAGER")){
			

			boolean finalizeRequest = ReimbursementSeriveAlpha.getInstance().finalizeRequest(
					new Reimbursement(Integer.parseInt(request.getParameter("reimbursementId")),
							new Employee(information.getId()),
							new ReimbursementStatus(request.getParameter("reimbursementstatus").toUpperCase())
							));
			if(finalizeRequest)
				return new ClientMessage("Update Reimbursement Successfully");
			return new ClientMessage("Fails");
		}
		return new  ClientMessage("Do not have authority to do it ");
	}


	/**
	 * Returns a collection of reimbursement types thats is displayed
	 * in a drop down on the view.
	 */
	@Override
	public Object getRequestTypes(HttpServletRequest request) {


		return ReimbursementSeriveAlpha.getInstance().getReimbursementTypes();
	}

}
