package com.revature.service;

import java.util.Set;

import com.revature.model.Employee;
import com.revature.model.EmployeeToken;
import com.revature.repository.EmployeeDAO;

public class EmployeeServiceAlpha implements EmployeeService {
     
	private static EmployeeServiceAlpha employeeService = new EmployeeServiceAlpha();
	private EmployeeServiceAlpha(){}
	public static EmployeeService getInstance(){
		return employeeService;
	}
	@Override
	public Employee authenticate(Employee employee) {
		
	 Employee selectUser = EmployeeDAO.getInstance().select(employee.getUsername());
	if( selectUser.getPassword().equals(EmployeeDAO.getInstance().getPasswordHash(employee))){
		return selectUser;
	
	}	
	return null;
	}

	@Override
	public Employee getEmployeeInformation(Employee employee) {
		
		return EmployeeDAO.getInstance().select(employee.getId());
	}

	@Override
	public Set<Employee> getAllEmployeesInformation() {
		
		return EmployeeDAO.getInstance().selectAll();
	}

	@Override
	public boolean createEmployee(Employee employee) {

		return EmployeeDAO.getInstance().insert(employee);
	}

	@Override
	public boolean updateEmployeeInformation(Employee employee) {
		
		return EmployeeDAO.getInstance().update(employee);
	}

	@Override
	public boolean updatePassword(Employee employee) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUsernameTaken(Employee employee) {
//		return EmployeeDAO.getInstance().insert(employee);
		if(EmployeeDAO.getInstance().select(employee.getUsername())==null){
			return false;
		}
		return true;
	}

	@Override
	public boolean createPasswordToken(Employee employee) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletePasswordToken(EmployeeToken employeeToken) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTokenExpired(EmployeeToken employeeToken) {
		// TODO Auto-generated method stub
		return false;
	}

}
