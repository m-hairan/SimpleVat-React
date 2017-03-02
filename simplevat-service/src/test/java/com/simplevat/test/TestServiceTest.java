package com.simplevat.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.simplevat.entity.TestEntity;
import org.junit.Ignore;

@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class TestServiceTest extends BaseManagerTest {
	
	@Test
	public void testSaveExpense(){
		
		TestEntity test = new TestEntity();
		test.setTestName("test Name");
		testService.saveTest(test);
	}

}
