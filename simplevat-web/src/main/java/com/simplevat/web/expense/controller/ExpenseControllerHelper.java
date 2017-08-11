package com.simplevat.web.expense.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import com.simplevat.entity.Expense;
import com.simplevat.web.constant.ExpenseConstants;
import com.simplevat.web.expense.model.ExpenseModel;

public class ExpenseControllerHelper {

    public Expense getExpense(ExpenseModel model) {
        Expense expense = new Expense();
        expense.setExpenseId(model.getExpenseId());
        System.out.println("accwedwqrfewewq : " + model.getExpenseId());
        expense.setUser(model.getUser());
        expense.setCreatedBy(model.getCreatedBy());
        expense.setCreatedDate(model.getCreatedDate());
        expense.setCurrency(model.getCurrency());
        expense.setDeleteFlag(model.isDeleteFlag());
        expense.setExpenseAmount(model.getExpenseAmount());
        if (model.getExpenseDate() != null) {
            LocalDateTime expenseDate = Instant.ofEpochMilli(model.getExpenseDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            expense.setExpenseDate(expenseDate);
        }
        expense.setExpenseDescription(model.getExpenseDescription());
        expense.setLastUpdateDate(model.getLastUpdateDate());
        expense.setLastUpdatedBy(model.getLastUpdatedBy());
        expense.setProject(model.getProject());
        expense.setReceiptAttachmentDescription(model.getReceiptAttachmentDescription());
        expense.setReceiptAttachmentPath(model.getReceiptAttachmentPath());
        expense.setReceiptNumber(model.getReceiptNumber());
        expense.setTransactionCategory(model.getTransactionCategory());
        expense.setTransactionType(model.getTransactionType());
        expense.setVersionNumber(model.getVersionNumber());
        return expense;
    }

    public ExpenseModel getExpenseModel(Expense entity) {
        ExpenseModel expenseModel = new ExpenseModel();
        expenseModel.setExpenseId(entity.getExpenseId());
        expenseModel.setUser(entity.getUser());
        expenseModel.setCreatedBy(entity.getCreatedBy());
        expenseModel.setCreatedDate(entity.getCreatedDate());
        expenseModel.setCurrency(entity.getCurrency());
        expenseModel.setDeleteFlag(entity.getDeleteFlag());
        expenseModel.setExpenseAmount(entity.getExpenseAmount());

        if (entity.getExpenseDate() != null) {
            Date expenseDate = Date.from(entity.getExpenseDate().atZone(ZoneId.systemDefault()).toInstant());
            expenseModel.setExpenseDate(expenseDate);
        }

        expenseModel.setExpenseDescription(entity.getExpenseDescription());
        expenseModel.setLastUpdateDate(entity.getLastUpdateDate());
        expenseModel.setLastUpdatedBy(entity.getLastUpdatedBy());
        expenseModel.setProject(entity.getProject());
        expenseModel.setReceiptAttachmentDescription(entity.getReceiptAttachmentDescription());
        expenseModel.setReceiptAttachmentPath(entity.getReceiptAttachmentPath());
        expenseModel.setReceiptNumber(entity.getReceiptNumber());
        expenseModel.setTransactionCategory(entity.getTransactionCategory());
        expenseModel.setTransactionType(entity.getTransactionType());
        expenseModel.setVersionNumber(entity.getVersionNumber());

        String attachmentPath = entity.getReceiptAttachmentPath();
        if (attachmentPath != null && !attachmentPath.isEmpty()) {
            String tomcatHome = System.getProperty("catalina.base");
            File expenseFile = new File(tomcatHome.concat(attachmentPath));
            try {
                InputStream inputStream = new FileInputStream(expenseFile);
                expenseModel.setAttachmentFileContent(new DefaultStreamedContent(inputStream, new MimetypesFileTypeMap().getContentType(expenseFile), expenseFile.getName()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return expenseModel;
    }

    public void storeUploadedFile(ExpenseModel model, Expense expense, String fileLocation) {
        String tomcatHome = System.getProperty("catalina.base");

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

            Path file = Files.createTempFile(dataFolder, ExpenseConstants.EXPENSE + "_" + filename, "_" + System.currentTimeMillis() + "." + extension);
            InputStream in = uploadedFile.getInputstream();
            Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);

            expense.setReceiptAttachmentPath(fileLocation + "/" + file.getFileName());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
