package com.snapdeal.disconsumer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.disconsumer.enums.ExecutionMode;
import com.snapdeal.disconsumer.service.ExpressionService;
import com.snapdeal.disconsumer.sro.ExpressionSRO;

/*
 * Controller for Handling Servlet Request*/
@Controller
public class ExpressionController {
	
	private ExpressionService expressionService;

	public ExpressionService getExpressionService() {
		return expressionService;
	}

	/* Setting expressionService Dependency */
	@Autowired
	public void setExpressionService(ExpressionService expressionService) {
		this.expressionService = expressionService;
	}

	/* Method for getting all expressions */
	@RequestMapping(value = "/allexpressions",method = RequestMethod.GET)
	@ResponseBody
	public List<ExpressionSRO> getAllExpressions(){
		return expressionService.getAllExpressions();
	}
	
	//inserting an expression in database
	@RequestMapping(value="/insertexpression",method=RequestMethod.POST)
	@ResponseBody
	public boolean insertEvent(@RequestParam(value="namespace") String namespace,
			@RequestParam(value="referenceName") String referenceName,
			@RequestParam(value="expression") String expression,
			@RequestParam(value="executionMode") String executionMode,
			@RequestParam(value="createdBy") String createdby){
		try{
			ExecutionMode.valueOf(executionMode);
		}catch(Exception e){
			System.out.println("illlegal argument exception");
			return false;
		}
		return expressionService.insertExpression(referenceName,namespace, expression, createdby, ExecutionMode.valueOf(executionMode));	
	}
	
	/* Method for getting expressionById */
	@RequestMapping(value = "/getExpressionById",method = RequestMethod.GET)
	@ResponseBody
	public ExpressionSRO getExpressionById(@RequestParam(value= "expressionId") int id){
		return expressionService.getExpressionByID(id);
	}
	
	/*Method for getting expression By Name */
	@RequestMapping(value = "/getExpressionByName",method=RequestMethod.GET)
	@ResponseBody
	public ExpressionSRO getExpressionByName(@RequestParam(value = "expressionName") String name){
		return expressionService.getExpressionByName(name);
	}
	
	@RequestMapping(value = "/handleActions",method=RequestMethod.POST)
	@ResponseBody
	public boolean handleAction(@RequestParam(value="expressionId") int id,
			@RequestParam(value="name") String name,
			@RequestParam(value="expression") String expression,
			@RequestParam(value="action") String action){
		
		String namespace = name.substring(name.lastIndexOf('.') + 1);
		String referenceName = name.substring(0, name.lastIndexOf("."));
		
		ExpressionSRO expSRO = new ExpressionSRO(id,namespace,referenceName,expression);
		
		expressionService.executeActions(expSRO,action);
		
		return true;
	}
	
	@RequestMapping(value = "/getAllActions",method=RequestMethod.GET)
	@ResponseBody
	public List<String> getActions(){
		return expressionService.getAllActions();
	}
	
	@RequestMapping(value = "/deleteExpressionById",method=RequestMethod.GET)
	@ResponseBody
	public boolean deleteExpressionById(@RequestParam("id") int id){
		return expressionService.deleteExpressionById(id);
	}
	
}
