package com.simplevat.controller.bankaccount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.criteria.bankaccount.ImportedDraftTransactionCriteria;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.ImportedDraftTransaction;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.service.bankaccount.ImportedDraftTransactonService;
import com.simplevat.service.bankaccount.TransactionService;

@Controller
@ManagedBean(name = "transactionUploadController")
@RequestScoped
public class TransactionUploadController {
	
	@Autowired
	private ImportedDraftTransactonService importedDraftTransactonService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private TransactionController transactionController;
	
	@Getter
	@Setter
	private ImportedDraftTransaction selectedImportedDraftTransaction;
	
	@Getter
	@Setter
	private UploadedFile statementFile;
	
	public static final String TOMCAT_TEMP_DIR = System.getProperty("java.io.tmpdir");
	
	public static final String TRANSACTION_DATE_FORMATE = "DD/MM/yyyy";
	
	private static final String CSV_HEADER = "TRANSACTION_DATE,TRANSACTION_DESCRIPTION,DEBIT_TRANSACTION_AMOUNT,CREDIT_TRANSACTION_AMOUNT";
	
	public String uploadImportedDraftTransactions() throws IOException{
		
		if(statementFile == null){
			return null;
		} {
			String filename = FilenameUtils.getBaseName(statementFile.getFileName()); 
			String extension = FilenameUtils.getExtension(statementFile.getFileName());
			
			File file = File.createTempFile(filename, extension, new File(TOMCAT_TEMP_DIR));
			
			InputStream in = statementFile.getInputstream();
			Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String csvHeader = bufferedReader.readLine();
			
			if(CSV_HEADER.equalsIgnoreCase(csvHeader)) {
				String line = "";
				while((line = bufferedReader.readLine()) != null) {
					
					LocalDateTime transactionDate = null;
					String transactionDescription = null;
					String debitAmount = null;
					String creditAmount = null;
					boolean istransactionLineGood = true;
					try{
						String[] transactionDataAry = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
						
						DateFormat format = new SimpleDateFormat(TRANSACTION_DATE_FORMATE, Locale.ENGLISH);
						Date date = format.parse(transactionDataAry[0]);
						transactionDate = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
						transactionDescription = transactionDataAry[1];
						debitAmount = transactionDataAry[2].replace("\"", "").replaceAll(",", "");
						creditAmount = transactionDataAry[3].replace("\"", "").replaceAll(",", "");
				
					} catch(ArrayIndexOutOfBoundsException e){
						
					} catch(Exception e){
						istransactionLineGood = false;
					}
					
					if(istransactionLineGood){
						
						
						System.out.println("transactionDate-->"+transactionDate);
						System.out.println("transactionDescription-->"+transactionDescription);
						System.out.println("debitAmount-->"+debitAmount);
						System.out.println("creditAmount-->"+creditAmount);
						
						ImportedDraftTransaction importedDraftTransacton = new ImportedDraftTransaction();
						importedDraftTransacton.setImportedTransactionDate(transactionDate);
						importedDraftTransacton.setImportedTransactionDescription(transactionDescription);
						
						if(debitAmount != null && !debitAmount.trim().isEmpty()){
							importedDraftTransacton.setImportedDebitCreditFlag('D');
							importedDraftTransacton.setImportedTransactionAmount(new BigDecimal(debitAmount));
						} else if (creditAmount != null && !creditAmount.trim().isEmpty()) {
							importedDraftTransacton.setImportedDebitCreditFlag('C');
							importedDraftTransacton.setImportedTransactionAmount(new BigDecimal(creditAmount));
						} else {
							importedDraftTransacton.setImportedDebitCreditFlag('D');
						}
						importedDraftTransacton.setBankAccount(transactionController.getSelectedBankAccount());
						importedDraftTransacton.setCreatedBy(12345);
						try{
							importedDraftTransactonService.updateOrCreateImportedDraftTransaction(importedDraftTransacton);
						} catch (Exception e){
							e.printStackTrace();
						}
						
					}
					
					
					
				}
			} else {
				System.out.println("please upload expected file");
			}
			System.out.println("filename-->"+file.getName());
			System.out.println("filename-->"+file.length());
			
			return "/pages/secure/bankaccount/review-imported-bank-transactions.xhtml";
			
		}
		
		
	}
	
	public void deleteImportedDraftTransaction(){
		selectedImportedDraftTransaction.setDeleteFlag(true);
		importedDraftTransactonService.updateOrCreateImportedDraftTransaction(selectedImportedDraftTransaction);
	}
	
	public String saveTransactions() throws Exception{
		
		ImportedDraftTransactionCriteria importedDraftTransactonCriteria = new ImportedDraftTransactionCriteria();
		importedDraftTransactonCriteria.setActive(Boolean.TRUE);
		List<ImportedDraftTransaction> importedDraftTransactions = importedDraftTransactonService.getImportedDraftTransactionsByCriteria(importedDraftTransactonCriteria);
		
		for(ImportedDraftTransaction importedDraftTransaction : importedDraftTransactions){
			Transaction transaction = new Transaction();
			transaction.setTransactionDate(importedDraftTransaction.getImportedTransactionDate());
			transaction.setTransactionDescription(importedDraftTransaction.getImportedTransactionDescription());
			transaction.setDebitCreditFlag(importedDraftTransaction.getImportedDebitCreditFlag());
			transaction.setTransactionAmount(importedDraftTransaction.getImportedTransactionAmount());
			transaction.setLastUpdatedBy(12345);
			transaction.setCreatedBy(12345);
			transaction.setBankAccount(importedDraftTransaction.getBankAccount());
			transactionService.updateOrCreateTransaction(transaction);
		}
		
		return "/pages/secure/bankaccount/bank-transactions.xhtml";
		
	}
}
