package com.snapdeal.disconsumer.service.test;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.snapdeal.disconsumer.service.ExpressionService;
import com.snapdeal.disconsumer.sro.ExpressionSRO;
import com.snapdeal.disconsumer.test.BaseTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class ExpressionServiceImplTest extends BaseTest{
	
	@Autowired 
	private ExpressionService expressionService;
	
	@Test public void testQueriesForExpressionsService(){
		validateExpressionAgainstId(1,"count(user.orders.order.orderId");
	}

	private void validateExpressionAgainstId(int expressionId, String expectedExpression) {
		
		ExpressionSRO expressionSRO = expressionService.getExpressionByID(expressionId);
		assertNotNull(expressionSRO);
		assertEquals(expressionSRO.getExpression(), expectedExpression);
	}
		
}
