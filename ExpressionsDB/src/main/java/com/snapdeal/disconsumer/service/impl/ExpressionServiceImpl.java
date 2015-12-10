package com.snapdeal.disconsumer.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.snapdeal.disconsumer.dao.ExpressionDao;
import com.snapdeal.disconsumer.entity.ExpressionDO;
import com.snapdeal.disconsumer.enums.Actions;
import com.snapdeal.disconsumer.service.ExpressionService;
import com.snapdeal.disconsumer.sro.ExpressionSRO;

//Service Implementation 
@Service
public class ExpressionServiceImpl implements ExpressionService{
	
	//Injecting dependency expressionDao
	@Autowired
	@Qualifier(value="expressionDao")
	private ExpressionDao expressionDao;
	
	public ExpressionDao getExpressionDao() {
		return expressionDao;
	}
	
	public void setExpressionDao(ExpressionDao expressionDao) {
		this.expressionDao = expressionDao;
	}
	
	//Getting list of all expression.
	public List<ExpressionSRO> getAllExpressions(){
		
		List<ExpressionDO> listExpDO = expressionDao.getAllExpressions();
		
		List<ExpressionSRO> listExpSRO = new ArrayList<ExpressionSRO>();
		if (listExpDO.size()>0) {
			for (ExpressionDO expDO : listExpDO) {
				listExpSRO.add(expDO.getExpressionSRO());
			}
		}
		return listExpSRO;
	}
	
	//Getting expression by id
	public ExpressionSRO getExpressionByID(int id){
		
		ExpressionDO expDO = expressionDao.getExpressionById(id);
		if(expDO==null){
			return null;
		}
		else {
			return expDO.getExpressionSRO();
		}
	}
	
	// SOft deletion of an expression by updating its field isDeleted to true.
	//@Transactional
	public boolean deleteExpressionById(int id){
		ExpressionDO exprDO = expressionDao.getExpressionById(id);
		if(exprDO==null){
			return false;
		}
		return expressionDao.deleteExpressionById(id);
	}

	public ExpressionSRO getExpressionByName(String refName) {
		ExpressionDO exprDO  = expressionDao.getExpressionByName(refName);
		if(exprDO!=null){
			return exprDO.getExpressionSRO();
		}
		return null;
	}

	@Override
	public void executeActions(ExpressionSRO expSRO, String action) {

	}


	public List<String> getAllActions() {
		List<Actions> listactions = Arrays.asList(Actions.values());
		List<String> allActions = new ArrayList<String>();
		for(Actions ac:listactions){
			System.out.println(ac.getDescription()+" :st");
			allActions.add(ac.getDescription());
		}
		return allActions;
	}
}
