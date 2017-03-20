package com.simplevat.controller.expense;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;

import com.simplevat.entity.Expense;
import com.simplevat.expense.model.ExpenseModel;

public class ExpenseControllerHelper {
	
	public Expense getExpense(ExpenseModel model){
		Expense expense = new Expense();
		expense.setExpenseId(model.getExpenseId());
		expense.setClaimantId(model.getClaimantId());
		expense.setCreatedBy(model.getCreatedBy());
		expense.setCreatedDate(model.getCreatedDate());
		expense.setCurrencyCode(model.getCurrencyCode());
		expense.setDeleteFlag(model.isDeleteFlag());
		expense.setExpenseAmount(model.getExpenseAmount());
		expense.setExpenseDate(model.getExpenseDate());
		expense.setExpenseDescription(model.getExpenseDescription());
		expense.setLastUpdateDate(model.getLastUpdateDate());
		expense.setLastUpdatedBy(model.getLastUpdatedBy());
		expense.setProjectId(model.getProjectId());
		expense.setReceiptAttachmentDescription(model.getReceiptAttachmentDescription());
		expense.setReceiptAttachmentPath(model.getReceiptAttachmentPath());
		expense.setReceiptNumber(model.getReceiptNumber());
		expense.setTransactionCategoryCode(model.getTransactionCategoryCode());
		expense.setTransactionTypeCode(model.getTransactionTypeCode());
		return expense;
	}
	
	public ExpenseModel getExpenseModel(Expense entity){
		ExpenseModel expenseModel = new ExpenseModel();
		expenseModel.setExpenseId(entity.getExpenseId());
		expenseModel.setClaimantId(entity.getClaimantId());
		expenseModel.setCreatedBy(entity.getCreatedBy());
		expenseModel.setCreatedDate(entity.getCreatedDate());
		expenseModel.setCurrencyCode(entity.getCurrencyCode());
		expenseModel.setDeleteFlag(entity.isDeleteFlag());
		expenseModel.setExpenseAmount(entity.getExpenseAmount());
		expenseModel.setExpenseDate(entity.getExpenseDate());
		expenseModel.setExpenseDescription(entity.getExpenseDescription());
		expenseModel.setLastUpdateDate(entity.getLastUpdateDate());
		expenseModel.setLastUpdatedBy(entity.getLastUpdatedBy());
		expenseModel.setProjectId(entity.getProjectId());
		expenseModel.setReceiptAttachmentDescription(entity.getReceiptAttachmentDescription());
		expenseModel.setReceiptAttachmentPath(entity.getReceiptAttachmentPath());
		expenseModel.setReceiptNumber(entity.getReceiptNumber());
		expenseModel.setTransactionCategoryCode(entity.getTransactionCategoryCode());
		expenseModel.setTransactionTypeCode(entity.getTransactionTypeCode());
		return expenseModel;
	}
	
	public void storeUploadedFile(ExpenseModel model) {
		String tomcatHome = System.getProperty("catalina.base");
		
		String fileLocation = null;
		InputStream input = null;

		try {
			Properties prop = new Properties();
			
			input = getClass().getClassLoader()
                    .getResourceAsStream("/simplevat.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			fileLocation = prop.getProperty("file.upload.location");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		String fileUploadAbsolutePath = tomcatHome.concat(fileLocation);
		File filePath = new File(fileUploadAbsolutePath);
		if (!filePath.exists()) {
            filePath.mkdir();
        }
		Path dataFolder = Paths.get(fileUploadAbsolutePath);
		
		UploadedFile uploadedFile = model.getAttachmentFile();
		
		String filename = FilenameUtils.getBaseName(uploadedFile.getFileName()); 
		String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
		
		try {
			Path file = Files.createTempFile(dataFolder, filename + "-", "." + extension);
			InputStream in = uploadedFile.getInputstream();
			Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
