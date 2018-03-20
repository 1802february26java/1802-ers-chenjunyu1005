package com.revature.service;

import java.util.Set;

import com.revature.exception.NegativeAmountException;
import com.revature.model.Employee;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementType;
import com.revature.repository.ReimbursementDao;

public class ReimbursementSeriveAlpha implements ReimbursementService {

	
	private static ReimbursementSeriveAlpha reimbursementSerive= new ReimbursementSeriveAlpha();
	private ReimbursementSeriveAlpha (){}
	public static ReimbursementService getInstance(){
		return reimbursementSerive;
	}
	@Override
	public boolean submitRequest(Reimbursement reimbursement) {
		if(reimbursement.getAmount()<0){
			try {
				throw new NegativeAmountException("Can not be negative");
			} catch (NegativeAmountException e) {
				e.printStackTrace();
			}
		}
		return ReimbursementDao.getInstance().insert(reimbursement) ;
	}

	@Override
	public boolean finalizeRequest(Reimbursement reimbursement) {
		return ReimbursementDao.getInstance().update(reimbursement);
	}

	@Override
	public Reimbursement getSingleRequest(Reimbursement reimbursement) {

		return ReimbursementDao.getInstance().select(reimbursement.getId());
	}

	@Override
	public Set<Reimbursement> getUserPendingRequests(Employee employee) {

		return ReimbursementDao.getInstance().selectPending(employee.getId());
	}

	@Override
	public Set<Reimbursement> getUserFinalizedRequests(Employee employee) {

		return ReimbursementDao.getInstance().selectFinalized(employee.getId());
	}

	@Override
	public Set<Reimbursement> getAllPendingRequests() {

		return ReimbursementDao.getInstance().selectAllPending();
	}

	@Override
	public Set<Reimbursement> getAllResolvedRequests() {

		return ReimbursementDao.getInstance().selectAllFinalized();
	}

	@Override
	public Set<ReimbursementType> getReimbursementTypes() {

		return ReimbursementDao.getInstance().selectTypes();
	}

}
