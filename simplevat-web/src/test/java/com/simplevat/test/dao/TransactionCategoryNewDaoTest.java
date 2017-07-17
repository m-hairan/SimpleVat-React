package com.simplevat.test.dao;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.simplevat.dao.Dao;
import com.simplevat.dao.bankaccount.TransactionCategoryFilter;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;

@ContextConfiguration({ "/spring/applicationContext.xml" })
public class TransactionCategoryNewDaoTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private Dao<Integer, TransactionCategory> dao;

	public static int PK;
	
	@Before
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void setUp() {
			TransactionCategory transactionCategory = getTransactionCategory();
			transactionCategory = dao.persist(transactionCategory);
			assertTrue("transactionCategory  is null ", transactionCategory != null);
			PK = transactionCategory.getTransactionCategoryCode();
	}

	
	@After
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void tearDown() {
		TransactionCategory transactionCategory = dao.findByPK(PK);
		if (transactionCategory != null) {
			dao.delete(transactionCategory);
		}
	}

	@Test
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void testUpdate() {
		TransactionCategory transactionCategory = dao.findByPK(PK);
		assertTrue("Transaction Category is  null", transactionCategory != null);
		transactionCategory = getTransactionCategory();
		transactionCategory.setTransactionCategoryDescription("UPDATED_DESCRIPTION");
		transactionCategory.setTransactionCategoryCode(PK);
		dao.update(transactionCategory);
		transactionCategory = dao.findByPK(PK);
		assertTrue("Transaction Category is  null", transactionCategory != null);
		assertTrue("Updationn is not working ",
				transactionCategory.getTransactionCategoryDescription().equals("UPDATED_DESCRIPTION"));
	}

	
	@Test
	public void testFindByAttribute() {
		Map<String,String> map = new HashMap<>();
		map.put("transactionCategoryName", "Category");
		List<TransactionCategory> listTransactionCategory = dao.findByAttributes(map);
		assertTrue("findByAttribute Not working ", listTransactionCategory.size()>0);
	}
	
	@Test
	public void testFilter() {
		TransactionCategory transactionCategory = new TransactionCategory();
		transactionCategory.setDeleteFlag(false);
		TransactionCategoryFilter filter = new TransactionCategoryFilter(transactionCategory,0,2);
		List<TransactionCategory> listTransactionCategory = dao.filter(filter);
		assertTrue("testFilter Not working ", listTransactionCategory.size()==2);
		int transactionCategoryCode = listTransactionCategory.get(0).getTransactionCategoryCode();
		for(int i=1;i<=listTransactionCategory.size()-1;i++) {
			int anotherCategory = listTransactionCategory.get(i).getTransactionCategoryCode();
			assertTrue("Not in proper order ", transactionCategoryCode < anotherCategory);
			transactionCategoryCode = anotherCategory;
			
		}
	}
	@Test
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void testNamedQuery() {
		List<TransactionCategory> listTransactionCategory = dao.executeNamedQuery("findAllTransactionCategory");
		assertTrue("Size should be more than 0", listTransactionCategory.size() > 0);
	}

	private TransactionCategory getTransactionCategory() {
		TransactionCategory transactionCategory = new TransactionCategory();
		transactionCategory.setCreatedBy(3);
		transactionCategory.setCreatedDate(LocalDateTime.now());
		transactionCategory.setDefaltFlag('N');
		transactionCategory.setDeleteFlag(false);
		transactionCategory.setLastUpdateDate(null);
		transactionCategory.setLastUpdatedBy(3);
		transactionCategory.setOrderSequence(1);
		//transactionCategory.setParentTransactionCategoryCode(null);
		transactionCategory.setParentTransactionCategory(null);
		//transactionCategory.setTransactionCategoryCode(1);
		transactionCategory.setTransactionCategoryDescription("Desctiption 1");
		transactionCategory.setTransactionCategoryName("Category Name");
		TransactionType transactionType = new TransactionType();
		transactionType.setTransactionTypeCode(1);
		//transactionCategory.setTransactionTypeCode(1);
		transactionCategory.setTransactionType(transactionType);
		transactionCategory.setVersionNumber(0);
		return transactionCategory;
	}

}
