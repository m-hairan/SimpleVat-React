/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.rest.vatcontroller;

import com.simplevat.bank.model.DeleteModel;
import com.simplevat.entity.VatCategory;
import com.simplevat.service.VatCategoryService;
import java.io.Serializable;
import java.util.Date;
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
@RequestMapping(value = "/rest/vat")
public class VatController implements Serializable {

    @Autowired
    private VatCategoryService vatCategoryService;

    @GetMapping(value = "getvat")
    private ResponseEntity<List<VatCategory>> getVatList() {
        List<VatCategory> vatCategorys = vatCategoryService.getVatCategoryList();
        if (vatCategorys != null) {
            return new ResponseEntity(vatCategorys, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(value = "/deletevat")
    private ResponseEntity deleteVat(@RequestParam(value = "id") Integer id) {
        VatCategory vatCategory = vatCategoryService.findByPK(id);
        if (vatCategory != null) {
            vatCategory.setDeleteFlag(true);
            vatCategoryService.update(vatCategory, vatCategory.getId());
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);

    }

    @DeleteMapping(value = "/deletevats")
    public ResponseEntity deleteVats(@RequestBody DeleteModel ids) {
        try {
            vatCategoryService.deleteByIds(ids.getIds());
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/getbyid")
    private ResponseEntity<VatCategory> editInvoice(@RequestParam(value = "id") Integer id) {
        VatCategory vatCategory = vatCategoryService.findByPK(id);
        if (vatCategory != null) {
            return new ResponseEntity(vatCategory, HttpStatus.OK);

        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/savevat")
    private ResponseEntity saveInvoice(@RequestBody VatCategory vatCategory, @RequestParam(value = "id") Integer id) {

        if (vatCategory.getId() == null) {
            vatCategory.setCreatedBy(id);
            vatCategory.setCreatedDate(new Date());
            vatCategory.setCreatedDate(new Date());
            vatCategory.setDefaultFlag('N');
            vatCategory.setOrderSequence(1);
            vatCategory.setVersionNumber(1);
            vatCategoryService.persist(vatCategory);
        } else {
            vatCategory.setId(vatCategory.getId());
            vatCategoryService.update(vatCategory);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/deletevats")
    private ResponseEntity deleteVats() {
        List<VatCategory> vatCategoryList = vatCategoryService.getVatCategoryList();
        if (vatCategoryList != null) {
            for (VatCategory vatCategory : vatCategoryList) {
            	vatCategory.setDeleteFlag(true);
            	vatCategoryService.update(vatCategory, vatCategory.getId());
			}
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);

    }
     
    
}
