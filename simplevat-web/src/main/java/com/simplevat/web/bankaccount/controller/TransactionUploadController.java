package com.simplevat.web.bankaccount.controller;

import com.github.javaplugs.jsf.SpringScopeView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.criteria.bankaccount.ImportedDraftTransactionCriteria;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.entity.bankaccount.ImportedDraftTransaction;
import com.simplevat.entity.bankaccount.Transaction;
import com.simplevat.entity.bankaccount.TransactionStatus;
import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.service.bankaccount.ImportedDraftTransactonService;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.service.bankaccount.TransactionStatusService;

@Controller
@SpringScopeView
public class TransactionUploadController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TransactionUploadController.class);
	
	@Autowired
	private ImportedDraftTransactonService importedDraftTransactonService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private TransactionController transactionController;
	
	@Autowired
	private TransactionStatusService transactionStatusService;
	
	@Getter
	@Setter
	private ImportedDraftTransaction selectedImportedDraftTransaction;
	
	@Autowired
	private BankAccountService bankAccountService;
	
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
						
						ImportedDraftTransaction importedDraftTransacton = new ImportedDraftTransaction();
						importedDraftTransacton.setImportedTransactionDate(transactionDate);
						importedDraftTransacton.setImportedTransactionDescription(transactionDescription);
						
						if(debitAmount != null && !debitAmount.trim().isEmpty()){
							importedDraftTransacton.setImportedDebitCreditFlag('D');
							importedDraftTransacton.setImportedTransactionAmount(new BigDecimal(debitAmount.trim()));
						} else if (creditAmount != null && !creditAmount.trim().isEmpty()) {
							importedDraftTransacton.setImportedDebitCreditFlag('C');
							importedDraftTransacton.setImportedTransactionAmount(new BigDecimal(creditAmount.trim()));
						} else {
							importedDraftTransacton.setImportedDebitCreditFlag('D');
						}
						importedDraftTransacton.setBankAccount(transactionController.getSelectedBankAccount());
						importedDraftTransacton.setCreatedBy(12345);
						try{
							importedDraftTransactonService.updateOrCreateImportedDraftTransaction(importedDraftTransacton);
						} catch (Exception e){
							LOGGER.error(e.getMessage());
						}
						
					}
					
					
					
				}
			} else {
				LOGGER.error("please upload file in expected formate");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("please upload file in expected formate"));
				return "/pages/secure/bankaccount/review-imported-bank-transactions.xhtml";
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transactions uploaded successfully"));
			return "/pages/secure/bankaccount/review-imported-bank-transactions.xhtml";
			
		}
		
		
	}
	
	public void deleteImportedDraftTransaction(){
		selectedImportedDraftTransaction.setDeleteFlag(true);
		importedDraftTransactonService.updateOrCreateImportedDraftTransaction(selectedImportedDraftTransaction);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transaction deleted successfully"));
	}
	
	public String saveTransactions() throws Exception{
		
		ImportedDraftTransactionCriteria importedDraftTransactonCriteria = new ImportedDraftTransactionCriteria();
		importedDraftTransactonCriteria.setActive(Boolean.TRUE);
		List<ImportedDraftTransaction> importedDraftTransactions = importedDraftTransactonService.getImportedDraftTransactionsByCriteria(importedDraftTransactonCriteria);
		
        Map<String,String> map = new HashMap<>();
		map.put("explainationStatusName", "UNEXPLAINED");
		TransactionStatus transactionStatus  = transactionStatusService.findByAttributes(map).get(0);
		
		for(ImportedDraftTransaction importedDraftTransaction : importedDraftTransactions){
			Transaction transaction = new Transaction();
			transaction.setTransactionStatus(transactionStatus);
			transaction.setTransactionDate(importedDraftTransaction.getImportedTransactionDate());
			transaction.setTransactionDescription(importedDraftTransaction.getImportedTransactionDescription());
			transaction.setDebitCreditFlag(importedDraftTransaction.getImportedDebitCreditFlag());
			transaction.setTransactionAmount(importedDraftTransaction.getImportedTransactionAmount());
			transaction.setLastUpdateBy(12345);
			transaction.setCreatedBy(12345);
			
			BankAccount bankAccount = bankAccountService.findByPK(importedDraftTransaction.getBankAccount().getBankAccountId());
			if(importedDraftTransaction.getImportedDebitCreditFlag() == 'C' && importedDraftTransaction.getImportedTransactionAmount() != null){
				bankAccount.setCurrentBalance(bankAccount.getCurrentBalance().add(importedDraftTransaction.getImportedTransactionAmount()));
			} else if (importedDraftTransaction.getImportedDebitCreditFlag() == 'D' && importedDraftTransaction.getImportedTransactionAmount() != null){
				bankAccount.setCurrentBalance(bankAccount.getCurrentBalance().subtract(importedDraftTransaction.getImportedTransactionAmount()));
			}
			bankAccountService.persist(bankAccount, 0);
			transaction.setCurrentBalance(bankAccount.getCurrentBalance());
			transaction.setBankAccount(bankAccount);
			transactionService.updateOrCreateTransaction(transaction);
		}
		
		importedDraftTransactonService.deleteImportedDraftTransaction(transactionController.getSelectedBankAccount().getBankAccountId());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Transactions imported successfully"));
		return "/pages/secure/bankaccount/bank-transactions.xhtml";
		
	}
}
