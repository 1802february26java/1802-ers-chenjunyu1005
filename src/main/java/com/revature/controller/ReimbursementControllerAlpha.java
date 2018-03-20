package com.revature.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import com.revature.ajax.ClientMessage;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;
import com.revature.model.ReimbursementType;
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
		boolean submitRequest = ReimbursementSeriveAlpha.getInstance().submitRequest(
				new Reimbursement(Integer.parseInt(request.getParameter("id")), 
						LocalDateTime.parse(request.getParameter("requestTime"), DateTimeFormatter.ISO_DATE_TIME),
						LocalDateTime.parse(request.getParameter("requestTime"), DateTimeFormatter.ISO_DATE_TIME),
						Double.parseDouble(request.getParameter("amount")),
						request.getParameter("description"),
						new Employee(Integer.parseInt(request.getParameter("employeeId"))),
						new ReimbursementStatus(Integer.parseInt(request.getParameter("statusid")),request.getParameter("status")),
						new ReimbursementType(Integer.parseInt(request.getParameter("typeid")),request.getParameter("type"))
						)

				);

		return submitRequest;
	}

	/**
	 * Returns a single reimbursement request specified by the user.
	 * 
	 * This operation can be performed by regular and/or manager employees.
	 */

	@Override
	public Object singleRequest(HttpServletRequest request) {
		return ReimbursementSeriveAlpha.getInstance().getSingleRequest(
				new Reimbursement(Integer.parseInt(request.getParameter("reimbursementId"))));

	}
	/**
	 * Returns a collection of reimbursement requests.
	 * 
	 * This operation can be performed by regular and/or manager employees.
	 */

	@Override
	public Object multipleRequests(HttpServletRequest request) {
		if(request.getParameter("roles").equals("MANAGER")){

			if(request.getParameter("managerstatus").equals("PENDING"))
				return ReimbursementSeriveAlpha.getInstance().getAllPendingRequests(
						);
			else if(request.getParameter("status").equals("APPROVED")|request.getParameter("status").equals("DECLINED")){
				return ReimbursementSeriveAlpha.getInstance().getAllResolvedRequests();
			}

		}else{

			if(request.getParameter("employeestatus").equals("PENDING")){
				return ReimbursementSeriveAlpha.getInstance().getUserPendingRequests(

						new Employee(Integer.parseInt(request.getParameter("id"))
								));
			}else if(request.getParameter("employeestatus").equals("APPROVED")|request.getParameter("employeestatus").equals("DECLINED")){
				return ReimbursementSeriveAlpha.getInstance().getUserFinalizedRequests(new Employee(Integer.parseInt(request.getParameter("id")
						)));

			}
			

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
		if(request.getParameter("roles").equals("MANAGER")){
			ReimbursementSeriveAlpha.getInstance().finalizeRequest(
					new Reimbursement(new ReimbursementStatus(request.getParameter("status"))));
			return new ClientMessage("Success");
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
