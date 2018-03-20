package com.revature.repository;

import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;

public class Test {
public static void main(String[] args) {
//	System.out.println(EmployeeDAO.getInstance().select(1));
//	System.out.println(EmployeeDAO.getInstance().select("JUNYUCHEN"));
//	System.out.println(EmployeeDAO.getInstance().selectAll());
//	System.out.println(ReimbursementDao.getInstance().select(1));
	
	System.out.println(ReimbursementDao.getInstance().update(
			
			new Reimbursement
			(29,new Employee(21),new ReimbursementStatus(29,"APPROVED"))))	;
}
}
