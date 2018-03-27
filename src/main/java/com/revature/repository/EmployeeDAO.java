package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.model.EmployeeRole;
import com.revature.model.EmployeeToken;
import com.revature.util.ConnectionUtil;

public class EmployeeDAO implements EmployeeRepository {
	private static Logger logger = Logger.getLogger(EmployeeDAO.class);

	private static Connection connection =ConnectionUtil.getConnection();
	
	private static EmployeeDAO employeeDao = new EmployeeDAO();
	
	private EmployeeDAO(){};
	
	public static EmployeeRepository getInstance(){
		
		return employeeDao;
	}
	
	
	
	@Override
	public boolean insert(Employee employee) {
		// TODO Auto-generated method stub
		try {
			String sql ="INSERT INTO USER_T VALUES (NULL,?,?,?,?,?,?)";
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setString(1, employee.getFirstName());
			prepareStatement.setString(2, employee.getLastName());
			prepareStatement.setString(3, employee.getUsername());
			prepareStatement.setString(4, employee.getPassword());
			prepareStatement.setString(5,employee.getEmail());
			prepareStatement.setInt(6,employee.getEmployeeRole().getId());
			int executeUpdate = prepareStatement.executeUpdate();
			if(executeUpdate>0){
				return true;
			}
		} catch (SQLException e) {
			
			logger.warn("Exception Inserting New Customer", e);
		}

		return false;
	}

	@Override
	public boolean update(Employee employee) {
		try {
			int parameterIndex =0;
			String sql="UPDATE USER_T SET U_FIRSTNAME=? , U_LASTNAME=?, U_USERNAME=? , U_EMAIL=? Where U_ID=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(++parameterIndex, employee.getFirstName());
			statement.setString(++parameterIndex, employee.getLastName());
			statement.setString(++parameterIndex, employee.getUsername());
			statement.setString(++parameterIndex, employee.getEmail());
			statement.setInt(++parameterIndex, employee.getId());
			int executeUpdate = statement.executeUpdate();
			if(executeUpdate>0){
				return true;
			}
		} catch (SQLException e) {
			logger.warn("Exception When Updating customer information", e);

		}
		return false;
	}

	@Override
	public Employee select(int employeeId) {
		try {
			String sql="SELECT U.U_FIRSTNAME,U.U_LASTNAME,U.U_USERNAME,U.U_EMAIL FROM USER_T U, USER_ROLE R WHERE U.UR_ID=R.UR_ID AND U.U_ID = ?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, employeeId);
			ResultSet rs= statement.executeQuery();
			if(rs.next()){
//				EmployeeRole employeeRole= new EmployeeRole(rs.getInt("UR_ID"),rs.getString("UR_TYPE"));

				return new Employee(rs.getString("U_FIRSTNAME"),rs.getString("U_LASTNAME")
						,rs.getString("U_USERNAME"),rs.getString("U_EMAIL"));

			}
		} catch (SQLException e) {
			
			logger.warn("Exception selecting specific customer", e);

		}
		return null;
	}

	@Override
	public Employee select(String username) {
		// TODO Auto-generated method stub

		try {

			String sql="SELECT U.U_ID,U.U_FIRSTNAME,U.U_LASTNAME,U.U_USERNAME,U.U_PASSWORD,U.U_EMAIL,R.UR_TYPE FROM USER_T U, USER_ROLE R WHERE U.UR_ID=R.UR_ID AND U.U_USERNAME =?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet rs= statement.executeQuery();
			if(rs.next()){
//id and role maybe useful when login to different roles
				return new Employee(rs.getInt("U_ID"),rs.getString("U_FIRSTNAME"),
						rs.getString("U_LASTNAME"),
						rs.getString("U_USERNAME"),
						rs.getString("U_PASSWORD"),
						rs.getString("U_EMAIL"),
						new EmployeeRole(rs.getString("UR_TYPE"))
						);

			}
		} catch (SQLException e) {
			logger.warn("Exception selecting customer by username", e);

		}
		return null;
	}

	@Override
	public Set<Employee> selectAll() {
		// TODO Auto-generated method stub
		try {
			String sql = "SELECT * FROM USER_T U,USER_ROLE R WHERE U.UR_ID=R.UR_ID AND R.UR_ID=1";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			Set<Employee> set = new HashSet<>();
			while(rs.next()){
				EmployeeRole role = new EmployeeRole(rs.getInt("UR_ID"),rs.getString("UR_TYPE"));

				set.add(new Employee(rs.getInt("U_ID"),
						rs.getString("U_FIRSTNAME"),
						rs.getString("U_LASTNAME"),
						rs.getString("U_USERNAME"),
						rs.getString("U_PASSWORD"),
						rs.getString("U_EMAIL"),
						role));
			}
			return set;
		} catch (SQLException e) {
			logger.warn("Exception when select all employees", e);

		}
		return null;
	}

	@Override
	public String getPasswordHash(Employee employee) {
	//Only hash based on password
		try {
			
			String sql= "select GET_HASH(?)AS HASH from dual";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, employee.getPassword());
			ResultSet rs = statement.executeQuery();
		
			if(rs.next()){

				return rs.getString("HASH");
			}
		} catch (SQLException e) {
			logger.warn("Exception get PasswordHash", e);

		}
		return new String();


	}

	@Override
	public boolean insertEmployeeToken(EmployeeToken employeeToken) {
		// TODO Auto-generated method stub
		try {

			String sql ="INSERT INTO PASSWORD_RECOVERY VALUES(?,?,?) ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, employeeToken.getToken());
			statement.setTimestamp(2, Timestamp.valueOf(employeeToken.getCreationDate()));
			Employee requester = employeeToken.getRequester();
			statement.setObject(3, requester.getPassword());
			int executeUpdate = statement.executeUpdate();
			if(executeUpdate>0){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteEmployeeToken(EmployeeToken employeeToken) {
		try {
			String sql ="DELETE ? FROM PASSWORD_RECOVERY WHERE U_USERNAME=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, employeeToken.getRequester().getPassword());
			statement.setString(2, employeeToken.getRequester().getUsername());
			Employee requester = employeeToken.getRequester();
			statement.setObject(3, requester.getPassword());
			int executeUpdate = statement.executeUpdate();
			if(executeUpdate>0){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		return false;

	}
	@Override
	public EmployeeToken selectEmployeeToken(EmployeeToken employeeToken) {

		String sql="SELECT P.PR_TOKEN FROM  USER_T U,PASSWORD_RECOVERY P WHERE U.U_ID =P.U_ID";

		return null;
	}

}
