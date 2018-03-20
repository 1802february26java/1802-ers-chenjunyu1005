package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Utility class to obtain a connection object
 * 
 *
 */
public class ConnectionUtil {
	private static Logger logger = Logger.getLogger(ConnectionUtil.class);
	//Whoever calls this method handle the Exception
	private static String url;
	private static String username;
	private static String password;
	private static  Connection con;
	private ConnectionUtil(){};
	static{
		try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		 url="jdbc:oracle:thin:@myinstancedbs.cuii5qzoodlu.us-east-1.rds.amazonaws.com:1521:ORCL";
		 username = "REIMBURSEMENT_DB";
		 password = "p4ssw0rd";
		 con =DriverManager.getConnection(url, username, password);
		}catch(SQLException s){
			logger.error("Something wrong"+s);

		} catch (ClassNotFoundException e) {
			
			logger.warn("Exception thrown adding oracle driver.", e);
		}
	}
	public static Connection getConnection(){
	 //locally jdbc:oracle:thin:@localhost:1521:xe
	
	 return con;
	}
	
	
}
