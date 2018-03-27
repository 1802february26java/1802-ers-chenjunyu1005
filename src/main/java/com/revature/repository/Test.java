package com.revature.repository;

import java.sql.Timestamp;

import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;
import com.revature.model.ReimbursementType;

public class Test {
public static void main(String[] args) {
//	System.out.println(EmployeeDAO.getInstance().select(1));
	System.out.println(EmployeeDAO.getInstance().select("1sadsa"));
//	System.out.println(EmployeeDAO.getInstance().selectAll());
//	System.out.println(ReimbursementDao.getInstance().selectAllPending());
	
//	System.out.println(ReimbursementDao.getInstance().selectPending(1));

	
	/*System.out.println(ReimbursementDao.getInstance().update(
			
			new Reimbursement
			(29,new Employee(21),new ReimbursementStatus(29,"APPROVED"))))	;*/
//	System.out.println(new Timestamp(System.currentTimeMillis()));
//	System.out.println(new Employee());
	
//	System.out.println(ReimbursementDao.getInstance().insert(new Reimbursement(30.0,"SSS",new Employee(21),new ReimbursementType("PENDING"))));
}
}
