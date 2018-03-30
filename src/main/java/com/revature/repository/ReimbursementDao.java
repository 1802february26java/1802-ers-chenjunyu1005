package com.revature.repository;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;
import com.revature.model.ReimbursementType;
import com.revature.util.ConnectionUtil;

public class ReimbursementDao implements ReimbursementRepository{
	private static final Connection connection =ConnectionUtil.getConnection();
	private static final Logger logger=Logger.getLogger(ReimbursementDao.class);
	private static ReimbursementDao reimbursement= new ReimbursementDao();
	private ReimbursementDao(){}
	public static ReimbursementRepository getInstance(){
		return reimbursement;
	}
	@Override
	public boolean insert(Reimbursement reimbursement) {
		try {
			//Reimbersement managerID null, if null is pending
			int parameterIndex=0;
			String sql ="INSERT INTO reimbursement VALUES(NULL,?,NULL,?,?,?,?,NULL,?,(SELECT RT_ID FROM reimbursement_type WHERE RT_TYPE = ? ))";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setTimestamp(++parameterIndex,new Timestamp(System.currentTimeMillis()));
			statement.setDouble(++parameterIndex, reimbursement.getAmount());
			statement.setString(++parameterIndex, reimbursement.getDescription());
			statement.setBinaryStream(++parameterIndex,new ByteArrayInputStream( reimbursement.getReceipt()),reimbursement.getReceipt().length);
			statement.setInt(++parameterIndex, reimbursement.getRequester().getId());
			statement.setInt(++parameterIndex,1);
			statement.setString(++parameterIndex,reimbursement.getType().getType().toUpperCase());

			int executeUpdate = statement.executeUpdate();
			if(executeUpdate>0){
				logger.info("Submitting Reimbursement Successful");
				return true;
			}
		} catch (SQLException e) {
			logger.warn("Error Inserting"+e);

		}

		return false;
	}
	/**
	 * Manager's reponsibility
	 */
	@Override
	public boolean update(Reimbursement reimbursement) {
		try {
			String sql="UPDATE reimbursement r "+
					"SET r.RS_ID = (SELECT s.rs_id FROM reimbursement_status s WHERE rs_status = ?), "
					+ "R_resolved = CURRENT_TIMESTAMP, "
					+ "Manager_id =? "
					+ "WHERE r_id =?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, reimbursement.getStatus().getStatus());
			statement.setInt(2, reimbursement.getApprover().getId());
			statement.setInt(3, reimbursement.getId());
			int executeUpdate = statement.executeUpdate();
			if(executeUpdate>0){
				logger.info("update success");
				return true;
			}

		} catch (SQLException e) {
			logger.warn("Error when updating");
		}
		return false;
	}

	/**
	 * Returns a single reimbursement request.
	 * 
	 * A join should be performed with respective reimbursement status and type.
	 */
	@Override
	public Reimbursement select(int reimbursementId, ReimbursementStatus status) {
		try {
			String sql="SELECT *FROM REIMBURSEMENT R, REIMBURSEMENT_STATUS A,REIMBURSEMENT_TYPE T WHERE R.RS_ID=A.RS_ID AND R.RT_ID=T.RT_ID AND R.R_ID=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, reimbursementId);
		
			ResultSet rs = statement.executeQuery();

			if(rs.next()){
				if(status.getStatus().equals("PENDING")){
//					Blob image =rs.getBlob("R_RECEIPT");
//					byte[] imageData= image.getBytes(1,(int) image.length() );
//					System.out.println(new String(imageData));
					
				return new Reimbursement(
						rs.getTimestamp("R_REQUESTED").toLocalDateTime(),
						rs.getDouble("R_AMOUNT"),
						rs.getString("R_DESCRIPTION"),
						new ReimbursementStatus(rs.getString("RS_STATUS")),
						new ReimbursementType(rs.getString("RT_TYPE"))
						);
				}else{
					return new Reimbursement(
							rs.getTimestamp("R_REQUESTED").toLocalDateTime(),
							rs.getTimestamp("R_RESOLVED").toLocalDateTime(),
							rs.getDouble("R_AMOUNT"),
							rs.getString("R_DESCRIPTION"),
							new ReimbursementStatus(rs.getString("RS_STATUS")),
							new ReimbursementType(rs.getString("RT_TYPE"))
							);
				}
			}
			

		} catch (SQLException e) {
	
			e.printStackTrace();
		}
		return null;
	
	}
	/**
	 * Returns a set of PENDING requests of a specific employee.
	 * 
	 * A join should be performed with respective reimbursement status and type.
	 */
	@Override
	public Set<Reimbursement> selectPending(int employeeId) {

		Set<Reimbursement> set = new HashSet<>();
		try {
			String sql="SELECT *FROM USER_T U,REIMBURSEMENT R, REIMBURSEMENT_STATUS A,REIMBURSEMENT_TYPE T WHERE U.U_ID= R.EMPLOYEE_ID And R.RS_ID=A.RS_ID AND R.RT_ID=T.RT_ID AND A.RS_STATUS='PENDING' AND R.EMPLOYEE_ID =?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, employeeId);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				set.add(	
						new Reimbursement(rs.getInt("U_ID"),
								rs.getTimestamp("R_REQUESTED").toLocalDateTime(),
								rs.getDouble("R_AMOUNT"),
								rs.getString("R_DESCRIPTION"),
								new ReimbursementStatus(rs.getString("RS_STATUS")),
								new ReimbursementType (rs.getString("RT_TYPE"))
								)
						);
			}
			return set;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return null;
	}
	/**
	 * Returns a set of APPROVED or DECLINED requests of a specific employee.
	 * 
	 * A join should be performed with respective reimbursement status and type.
	 */
	@Override
	public Set<Reimbursement> selectFinalized(int employeeId) {
		Set<Reimbursement> set = new HashSet<>();
		try{
			String sql= "SELECT *FROM USER_T U,REIMBURSEMENT R, REIMBURSEMENT_STATUS A,REIMBURSEMENT_TYPE T "
					+"WHERE U.U_ID= R.EMPLOYEE_ID And R.RS_ID=A.RS_ID AND R.RT_ID=T.RT_ID AND (A.RS_STATUS='APPROVED' OR A.RS_STATUS='DECLINED') "
					+"AND R.EMPLOYEE_ID =?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, employeeId);

			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				set.add(	
						new Reimbursement(rs.getInt("U_ID"),
								rs.getTimestamp("R_REQUESTED").toLocalDateTime(),
								rs.getTimestamp("R_RESOLVED").toLocalDateTime(),
								rs.getDouble("R_AMOUNT"),
								rs.getString("R_DESCRIPTION"),
								new ReimbursementStatus(rs.getString("RS_STATUS")),
								new ReimbursementType (rs.getString("RT_TYPE"))
								)
						);
			}
			return set;
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Set<Reimbursement> selectAllPending() {
		
		Set<Reimbursement> set = new HashSet<>();
		try {
		String sql ="SELECT * FROM User_ROLE E,USER_T U, REIMBURSEMENT R, REIMBURSEMENT_STATUS A,REIMBURSEMENT_TYPE T "
				+"WHERE E.UR_ID=U.UR_ID AND U.U_ID =R.EMPLOYEE_ID AND R.RS_ID=A.RS_ID AND R.RT_ID=T.RT_ID AND A.RS_STATUS='PENDING' "
				+"AND E.UR_ID=1";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();
		while(rs.next()){
			set.add(	
					new Reimbursement(rs.getInt("R_ID"),
							rs.getTimestamp("R_REQUESTED").toLocalDateTime(),
							rs.getDouble("R_AMOUNT"),
							rs.getString("R_DESCRIPTION"),
							new Employee(rs.getString("U_FIRSTNAME"),rs.getString("U_LASTNAME"),rs.getString("U_USERNAME"),rs.getString("U_EMAIL")),
							new ReimbursementStatus(rs.getString("RS_STATUS")),
							new ReimbursementType (rs.getString("RT_TYPE"))
							)
					);
		}
		return set;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns a set of all APPROVED or DECLINED reimbursement requests.
	 * 
	 * A join should be performed with respective reimbursement status, type and employee.
	 */

	@Override
	public Set<Reimbursement> selectAllFinalized() {
		try {
			String sql="SELECT * FROM User_ROLE E,USER_T U, REIMBURSEMENT R, REIMBURSEMENT_STATUS A,REIMBURSEMENT_TYPE T "
					+ "WHERE E.UR_ID=U.UR_ID AND U.U_ID =R.EMPLOYEE_ID AND R.RS_ID=A.RS_ID AND R.RT_ID=T.RT_ID AND (A.RS_STATUS='APPROVED' OR A.RS_STATUS='DECLINED') "
					+ "AND E.UR_ID=1";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			Set<Reimbursement> set =new HashSet<>();
			while(rs.next()){
				set.add(	
						new Reimbursement(rs.getInt("R_ID"),
								rs.getTimestamp("R_REQUESTED").toLocalDateTime(),
								rs.getTimestamp("R_RESOLVED").toLocalDateTime(),
								rs.getDouble("R_AMOUNT"),
								rs.getString("R_DESCRIPTION"),
								new Employee(rs.getString("U_FIRSTNAME"),rs.getString("U_LASTNAME"),rs.getString("U_USERNAME"),rs.getString("U_EMAIL")),
								new ReimbursementStatus(rs.getString("RS_STATUS")),
								new ReimbursementType (rs.getString("RT_TYPE"))
								)
						);
			}
			return set;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns a set of all reimbursement types.
	 */
	@Override
	public Set<ReimbursementType> selectTypes() {
		try {
			String sql ="SELECT * FROM REIMBURSEMENT_TYPE";
			Set<ReimbursementType> set = new HashSet<>();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			while(rs.next()){
				set.add(new ReimbursementType(rs.getInt("RT_ID")
						,rs.getString("RT_TYPE") 
						));
			}
			return set;
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return null;
	}

}
