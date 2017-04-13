package com.simplevat.test.service;

import com.simplevat.test.common.BaseManagerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.entity.Expense;
import org.junit.Ignore;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TestExpenseService extends BaseManagerTest {
	
	@Test
	@Ignore
	public void testSaveExpense(){
		
		Expense expense = new Expense();
		expense.setExpenseDescription("test description");
		
		Expense newExpense = expenseService.updateOrCreateExpense(expense);
		
	}

}
