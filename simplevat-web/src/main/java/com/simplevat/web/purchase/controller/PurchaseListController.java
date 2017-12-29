package com.simplevat.web.purchase.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.entity.Purchase;
import com.simplevat.service.PurchaseService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.InvoicePurchaseStatusConstant;
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
    private int totalPurchases;

    @Getter
    @Setter
    private int totalPaid;

    @Getter
    @Setter
    private int totalUnPaid;

    @Getter
    @Setter
    private int totalPartiallyPaid;

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
        totalPurchases = 0;
        totalPaid = 0;
        totalPartiallyPaid = 0;
        totalUnPaid = 0;
        purchaseControllerHelper = new PurchaseControllerHelper();
        populatePurchases();
    }

    public void populatePurchases() {
        if (purchaseService.getAllPurchase() != null) {
            for (Purchase purchase : purchaseService.getAllPurchase()) {
                if (purchase.getStatus() != null) {
                    if (purchase.getStatus() == InvoicePurchaseStatusConstant.PAID) {
                        totalPaid++;
                    } else if (purchase.getStatus() == InvoicePurchaseStatusConstant.PARTIALPAID) {
                        totalPartiallyPaid++;
                    } else if (purchase.getStatus() == InvoicePurchaseStatusConstant.UNPAID) {
                        totalUnPaid++;
                    }
                }
                totalPurchases++;
                PurchaseModel model = purchaseControllerHelper.getPurchaseModel(purchase);
                purchaseModels.add(model);
            }
        }
    }

    public void allPaidPurchase() {
        purchaseModels.clear();
        for (Purchase purchase : purchaseService.getAllPurchase()) {
            if (purchase.getStatus() != null && purchase.getStatus() == InvoicePurchaseStatusConstant.PAID) {
                purchaseModels.add(purchaseControllerHelper.getPurchaseModel(purchase));
            }
        }
    }

    public void allUnPaidPurchase() {
        purchaseModels.clear();
        for (Purchase purchase : purchaseService.getAllPurchase()) {
            if (purchase.getStatus() != null && purchase.getStatus() == InvoicePurchaseStatusConstant.UNPAID) {
                purchaseModels.add(purchaseControllerHelper.getPurchaseModel(purchase));
            }
        }
    }

    public void allPartialPaidPurchase() {
        purchaseModels.clear();
        for (Purchase purchase : purchaseService.getAllPurchase()) {
            if (purchase.getStatus() != null && purchase.getStatus() == InvoicePurchaseStatusConstant.PARTIALPAID) {
                purchaseModels.add(purchaseControllerHelper.getPurchaseModel(purchase));
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
