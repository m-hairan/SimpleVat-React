package com.simplevat.test.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.simplevat.dao.ProjectDao;
import com.simplevat.dao.bankaccount.TransactionCategoryDaoNew;
import com.simplevat.dao.bankaccount.TransactionStatusDao;
import com.simplevat.dao.bankaccount.TransactionTypeDao;
import com.simplevat.entity.Project;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionStatus;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.test.common.BaseManagerTest;

/**
 * @author Ankit
 *
 */
public class TestTransactionService extends BaseManagerTest {
	
	@Autowired
	public TransactionService transactionService;
	
	@Autowired
	TransactionStatusDao transacitonStatusDao;
	
	@Autowired
	TransactionCategoryDaoNew transactionCategoryDao;
	
	@Autowired
	ProjectDao projectDao;
	
	@Autowired
	TransactionTypeDao transactionTypeDao;

	@Test
	public void getcheckInData() {
		Map<Object, Number> map = transactionService.getCashInData();
		
		for (Object obj : map.keySet()) {
			Number n = map.get(obj);
			System.out.println((String)obj +"-------------------"+n);
		}
		System.out.println("Check in size ::: "+map.size());
	}

	
	@Test
	public void getcheckOutData() {
		Map<Object, Number> map = transactionService.getCashOutData();
		System.out.println("Check Out size :: "+map.size());
		for (Object obj : map.keySet()) {
			Number n = map.get(obj);
			System.out.println((String)obj +"-------------------"+n);
			
		}
	}
/*	@Test
	public void getMaxValue(){
		int d = transactionService.getMaxTransactionValue();
		System.out.println("Max Value is ::::::::::::::::::::::::  "+d);
	}*/
	
	/**
	 * this method is used to generate the sample data for Transaction.
	 */
	//@Test
	public void generateTestData() {
		
		for(int i=1;i<100;i++) {
			Transaction transaction = getTransaction(i);
			transactionService.persist(transaction);
		}
		
	}
	
	private Transaction getTransaction(int recordNumber) {
		int amount = ThreadLocalRandom.current().nextInt(1000, 5000);
		Transaction transaction = new Transaction();
		transaction.setCreatedBy(1);
		transaction.setDebitCreditFlag(getRandomCreditOrDebit());
		transaction.setDeleteFlag(false);
		transaction.setExplainedTransactionAttachementDescription("");
//		transaction.setExplainedTransactionAttachementPath("");
		TransactionCategory category = transactionCategoryDao.findByPK(1);
		//category.setTransactionCategoryCode(1);
		transaction.setExplainedTransactionCategory(category);
		transaction.setExplainedTransactionDescription("");
		transaction.setLastUpdateBy(1);
		Project project =projectDao.findByPK(1);
		//project.setProjectId(1);
		
		transaction.setProject(project);
		transaction.setReceiptNumber(receiptNumber(recordNumber));
		transaction.setTransactionAmount(getRandomTransactionAmount());
		transaction.setTransactionDate(getRandomDate());
		transaction.setTransactionDescription("");
		TransactionStatus status = transacitonStatusDao.findByPK(1);
		
		transaction.setTransactionStatus(status);
		TransactionType type = transactionTypeDao.findByPK(1);
		//type.setTransactionTypeCode(1);
		transaction.setTransactionType(type);
		
		
		return transaction;
		
	}
	
	private char getRandomCreditOrDebit() {
		String alphabet = "cd";
		Random r = new Random();
		return alphabet.charAt(r.nextInt(alphabet.length()));
	}
	
	private LocalDateTime getRandomDate() {
		  LocalDate today = LocalDate.now();
		  LocalDate lastYear = today.minusDays(365);
		  long todayDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime();
		  long lastYearDate = Date.from(lastYear.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime();
		  Date randomDate = new Date(ThreadLocalRandom.current().nextLong(lastYearDate,todayDate));
		  Instant instant = randomDate.toInstant();
		  LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
		  return localDateTime;
	}
	
	private BigDecimal getRandomTransactionAmount() {
		int amount = ThreadLocalRandom.current().nextInt(1000, 5000);
		return new BigDecimal(amount);
	}
	
	private String receiptNumber(int i) {
		return "recieptNumber" + i;
	}
	
	

}
