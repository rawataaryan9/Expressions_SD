package com.snapdeal.disconsumer.dao;

import java.util.List;

import com.snapdeal.disconsumer.entity.ExpressionDO;
import com.snapdeal.disconsumer.enums.ExecutionMode;

//Dao Interface for Expression Entity

public interface ExpressionDao {
	
	public List<ExpressionDO> getAllExpressions();
	public int getExpressionsCount();
	public ExpressionDO getExpressionById(int id);
	public boolean deleteExpressionById(int id);
	public ExpressionDO getExpressionByName(String refName);
	
}
