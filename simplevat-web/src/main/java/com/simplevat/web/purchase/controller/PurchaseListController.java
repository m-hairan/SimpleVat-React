package com.simplevat.web.purchase.controller;

import com.simplevat.web.expense.controller.*;
import com.github.javaplugs.jsf.SpringScopeView;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.entity.Expense;
import com.simplevat.entity.Purchase;
import com.simplevat.web.expense.model.ExpenseModel;
import com.simplevat.service.ExpenseService;
import com.simplevat.service.PurchaseService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.purchase.model.PurchaseModel;
import java.io.Serializable;
import javax.annotation.PostConstruct;

@Controller
@SpringScopeView
public class PurchaseListController extends BaseController implements Serializable {

    private static final long serialVersionUID = 9066359395680732884L;

    @Autowired
    private PurchaseService purchaseService;
    @Getter
    @Setter
    private PurchaseModel selectedPurchaseModel;
    @Getter
    @Setter
    private List<PurchaseModel> purchaseModels = new ArrayList<>();

    @Getter
    @Setter
    PurchaseControllerHelper purchaseControllerHelper;

    public PurchaseListController() {
        super(ModuleName.PURCHASE_MODULE);
    }

    @PostConstruct
    public void init() {
        purchaseControllerHelper = new PurchaseControllerHelper();
        getAllPurchase();
    }

    public void getAllPurchase() {
        if (purchaseService.getAllPurchase() != null) {
            for (Purchase purchase : purchaseService.getAllPurchase()) {
                System.out.println("Dataaaaaaaaaa============" + purchase.getPurchaseDescription());
                PurchaseModel model = purchaseControllerHelper.getPurchaseModel(purchase);
                purchaseModels.add(model);
            }
        }
    }

    public String reDirectToCreate() {
        return "/pages/secure/purchase/purchase.xhtml?faces-redirect=true";
    }

    public String redirectToEdit() {
        return "purchase?faces-redirect=true&selectedPurchaseModelId=" + selectedPurchaseModel.getPurchaseId();
    }

}
