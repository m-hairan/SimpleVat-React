/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.rest.transactioncontroller;

import com.simplevat.entity.VatCategory;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.VatCategoryService;
import com.simplevat.service.bankaccount.TransactionTypeService;
import com.simplevat.constant.DefualtTypeConstant;
import com.simplevat.exceptions.UnauthorizedException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sonu
 */
@RestController
@RequestMapping(value = "/rest/transaction")
public class TransactionController  implements Serializable{

    @Autowired
    private TransactionCategoryServiceNew transactionCategoryService;

    @Autowired
    private VatCategoryService vatCategoryService;

    @Autowired
    TransactionTypeService transactionTypeService;

    private TranscationCategoryHelper transcationCategoryHelper = new TranscationCategoryHelper();

    private TransactionCategory selectedTransactionCategory = new TransactionCategory();

    @GetMapping(value = "/gettransactioncategory")
    private ResponseEntity<List<TransactionCategory>> getAllTransactionCategory() {
        List<TransactionCategory> transactionCategories = transactionCategoryService.findAllTransactionCategory();
        if (transactionCategories != null) {
            return new ResponseEntity(transactionCategories, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = "/edittransactioncategory")
    private ResponseEntity<TransactionCategory> editTransactionCategory(@RequestParam("id") Integer id) {
        TransactionCategory transactionCategories = transactionCategoryService.findByPK(id);
        if (transactionCategories != null) {
            return new ResponseEntity(transactionCategories, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping(value = "/deletetransactioncategory")
    private ResponseEntity<TransactionCategory> deleteTransactionCategory(@RequestParam("id") Integer id) {
        TransactionCategory transactionCategories = transactionCategoryService.findByPK(id);
        if (transactionCategories == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        transactionCategories.setDeleteFlag(Boolean.TRUE);
        transactionCategoryService.update(transactionCategories, id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/getvatcategories")
    private ResponseEntity<List<VatCategory>> getVatCategories() {
        List<VatCategory> vatCategorys = vatCategoryService.getVatCategoryList();
        if (vatCategorys != null) {
            return new ResponseEntity(vatCategorys, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        }
    }

    @GetMapping(value = "/gettransactiontype")
    private ResponseEntity<List<TransactionType>> getAllTransactionType(String str) {
        List<TransactionType> transactionTypes = null;
        if (str == null) {
            return new ResponseEntity(transactionTypeService.findAll(), HttpStatus.OK);
        }
        List<TransactionType> filterList = new ArrayList<>();
        transactionTypes = transactionTypeService.findByText(str);
        for (TransactionType type : transactionTypes) {
            filterList.add(type);
        }
        return new ResponseEntity(filterList, HttpStatus.OK);
    }

    @PostMapping(value = "/savetransaction")
    private ResponseEntity save(@RequestBody TransactionCategoryModel transactionCategoryModel, @RequestParam("id") Integer id) throws UnauthorizedException {
        selectedTransactionCategory = transcationCategoryHelper.getTrascationModel(transactionCategoryModel);
        if (selectedTransactionCategory.getDefaltFlag() == DefualtTypeConstant.YES) {
            System.out.println("insideif=========1=========" + selectedTransactionCategory.getDefaltFlag());
            TransactionCategory transactionCategory = transactionCategoryService.getDefaultTransactionCategoryByTransactionCategoryId(selectedTransactionCategory.getTransactionCategoryId());
            if (transactionCategory != null) {
                System.out.println("insideif=========3=========" + transactionCategory.getDefaltFlag());
                transactionCategory.setDefaltFlag(DefualtTypeConstant.NO);
                transactionCategoryService.update(transactionCategory);
            }
        }
        if (selectedTransactionCategory.getTransactionCategoryId() == null) {
            selectedTransactionCategory.setOrderSequence(1);
            selectedTransactionCategory.setCreatedBy(id);
            selectedTransactionCategory.setCreatedDate(LocalDateTime.now());
            transactionCategoryService.persist(selectedTransactionCategory);
        } else {
            selectedTransactionCategory.setLastUpdateBy(id);
            selectedTransactionCategory.setLastUpdateDate(LocalDateTime.now());
            transactionCategoryService.update(selectedTransactionCategory);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
