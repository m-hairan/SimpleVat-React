package com.simplevat.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.entity.ExpenseEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ExpenseServiceTest extends BaseManagerTest {
	
	@Test
	public void testSaveExpense(){
		
		ExpenseEntity expenseEntity = new ExpenseEntity();
		expenseEntity.setExpenseDescription("test description");
		
		expenseService.saveExpense(expenseEntity);
	}

}
