/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.bankaccount.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.bankaccount.TransactionStatus;
import com.simplevat.entity.bankaccount.TransactionView;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.web.bankaccount.model.TransactionModel;
import com.simplevat.web.bankaccount.model.TransactionViewModel;
import com.simplevat.web.constant.TransactionCreditDebitConstant;
import com.simplevat.web.constant.TransactionStatusConstant;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author admin
 */
@Component
@SpringScopeView
public class TransactionViewLazyModelDemo extends LazyDataModel<TransactionViewModel> {

    @Autowired
    private TransactionControllerHelperDemo transactionControllerHelperDemo;
    @Autowired
    private TransactionService transactionService;
    private List<TransactionView> transactionViewList = new ArrayList<>();
    private List<TransactionView> childTransactionViewList = new ArrayList<>();
    @Setter
    private List<Invoice> invoiceList = new ArrayList<>();
    @Setter
    private List<TransactionModel> transactionModelList = new ArrayList<>();
    @Setter
    private List<TransactionStatus> transactionStatuseList = new ArrayList<>();
    @Getter
    @Setter
    private TransactionModel expandedTransactionModel;
    @Getter
    @Setter
    private List<TransactionViewModel> transactionViewModelList = new ArrayList<>();

    @Setter
    private Integer bankAccountId;

    @Setter
    @Getter
    private Integer calculateRows;
    @Setter
    @Getter
    private Integer pageCount;
    @Setter
    private Integer previousPageNo = 0;
    private Integer defaultRowsPerPage = 5;
    private int numberOfRow = 0;

    public TransactionViewLazyModelDemo() {
    }

    @Override
    public Object getRowKey(TransactionViewModel transactionViewModel) {
        return transactionViewModel.getTransactionId();
    }

    @Override
    public List<TransactionViewModel> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        transactionViewModelList = new ArrayList<>();
        childTransactionViewList.clear();

        first = defaultRowsPerPage * previousPageNo;
        List<TransactionView> list = transactionService.getTransactionViewList(first, bankAccountId, pageSize);
        if (list != null) {
            transactionViewList = list;
        }
        for (TransactionView transactionView : transactionViewList) {
            List<TransactionView> childList = transactionService.getChildTransactionViewListByParentId(transactionView.getTransactionId());
            if (childList != null) {
                childTransactionViewList.addAll(childList);
            }
        }
        populateChildTransaction();
        populateTransactionList();
        //filter
//        for (TransactionViewModel transactionViewModel : data) {
//            boolean match = true;
//            if (filters != null) {
//                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
//                    try {
//                        String filterProperty = it.next();
//                        Object filterValue = filters.get(filterProperty);
//                        String fieldValue = String.valueOf(transactionViewModel.getClass().getField(filterProperty).get(transactionViewModel));
//
//                        if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
//                            match = true;
//                        } else {
//                            match = false;
//                            break;
//                        }
//                    } catch (Exception e) { 
//                        match = false;
//                    }
//                }
//            }
//
//            if (match) {
//
//                data.add(transactionViewModel);
//            }
//        }
        //sort
//        if(sortField != null) {
//            Collections.sort(data, new LazySorter(sortField, sortOrder));
//        }
        //rowCount

        int dataBaseRowCount = transactionService.getTotalTransactionCountByBankAccountId(bankAccountId);// + transactionService.getTotalPartiallyExplainedTransactionCountByBankAccountId(bankAccountId);
        this.setRowCount(dataBaseRowCount);
        this.setPageSize(childTransactionViewList.size());
        //paginate
//        if (dataSize > pageSize) {
//            try {
//                return data.subList(first, first + pageSize);
//            } catch (IndexOutOfBoundsException e) {
//                return data.subList(first, first + (dataSize % pageSize));
//            }
//        } else { 

        return transactionViewModelList;
//        }
    }

//    public int getCalculateRows() {
//        return data.size();
//    }
    private void populateTransactionList() {
        for (TransactionView transactionView : transactionViewList) {
            TransactionViewModel transactionViewModel = transactionControllerHelperDemo.getTransactionViewModel(transactionView);
            if (transactionViewModel.getExplanationStatusCode() == TransactionStatusConstant.UNEXPLAINED
                    || transactionViewModel.getExplanationStatusCode() == TransactionStatusConstant.PARTIALLYEXPLIANED) {
                getSuggestionStringForUnexplainedOrPartial(transactionViewModel);
            } else {
                getSuggestionStringForExplianed(transactionViewModel);
            }
            transactionViewModelList.add(transactionViewModel);
        }
        List<TransactionViewModel> childTransactionViewModelList = new ArrayList();
        for (TransactionViewModel transactionViewModel : transactionViewModelList) {
            if (transactionViewModel.isParent()) {
                childTransactionViewModelList.addAll(transactionViewModel.getChildTransactionList());
            }
        }
        System.out.println("previousPageChildCount :" + previousPageNo);
        for (TransactionViewModel transactionViewModel : childTransactionViewModelList) {
            int parentIndex = 0;
            for (TransactionViewModel transactionViewModel1 : transactionViewModelList) {
                if (transactionViewModel1.isParent()) {
                    if (transactionViewModel1.getTransactionId() == transactionViewModel.getParentTransaction()) {
                        parentIndex = transactionViewModelList.indexOf(transactionViewModel1);
                    }
                }
            }
            if (transactionViewModel.getExplanationStatusCode() == TransactionStatusConstant.UNEXPLAINED
                    || transactionViewModel.getExplanationStatusCode() == TransactionStatusConstant.PARTIALLYEXPLIANED) {
                getSuggestionStringForUnexplainedOrPartial(transactionViewModel);
            } else {
                getSuggestionStringForExplianed(transactionViewModel);
            }
            transactionViewModelList.add(++parentIndex, transactionViewModel);
        }
    }

    private void populateChildTransaction() {
        transactionControllerHelperDemo.getChildTransactions().clear();
        if (childTransactionViewList != null) {
            for (TransactionView transactionView : childTransactionViewList) {
                if (transactionView.getParentTransaction() != null) {
                    TransactionViewModel transactionViewModel = transactionControllerHelperDemo.getTransactionViewModel(transactionView);
                    transactionControllerHelperDemo.getChildTransactions().add(transactionViewModel);
                }
            }
        }
    }

    private void getSuggestionStringForUnexplainedOrPartial(TransactionViewModel transactionViewModel) {
        String suggestedTransactionString = "";
        BigDecimal suggestionAmount = transactionViewModel.getTransactionAmount();
        if (!transactionViewModel.getChildTransactionList().isEmpty()) {
            int i = 0;
            for (TransactionViewModel childModel : transactionViewModel.getChildTransactionList()) {
                if (i == transactionViewModel.getChildTransactionList().size() - 1) {
                    break;
                }
                suggestionAmount = suggestionAmount.subtract(childModel.getTransactionAmount());
                i++;
            }
        }
        if (transactionViewModel.getDebitCreditFlag() == TransactionCreditDebitConstant.CREDIT) {
            if (invoiceList != null && !invoiceList.isEmpty()) {
                for (Invoice invoice : invoiceList) {
                    if (suggestionAmount.doubleValue() == invoice.getDueAmount().doubleValue()) {
                        transactionViewModel.setReferenceId(invoice.getInvoiceId());
                        suggestedTransactionString = transactionViewModel.getTransactionTypeName() + " : " + transactionViewModel.getTransactionCategoryName()
                                + " : Invoice Paid : " + invoice.getInvoiceReferenceNumber() + " : " + invoice.getInvoiceContact().getFirstName()
                                + " : " + invoice.getCurrency().getCurrencySymbol() + " " + invoice.getDueAmount()
                                + " : Due On : " + new SimpleDateFormat("MM/dd/yyyy").format(Date.from(invoice.getInvoiceDueDate().atZone(ZoneId.systemDefault()).toInstant()));
                        break;
                    }
                }
            }
        }
        transactionViewModel.setSuggestedTransactionString(suggestedTransactionString);
    }

    private void getSuggestionStringForExplianed(TransactionViewModel transactionViewModel) {
        String suggestedTransactionString = "";
        if (!transactionViewModel.getChildTransactionList().isEmpty()) {
            TransactionViewModel childTransactionViewModel = transactionViewModel.getChildTransactionList().get(0);
            suggestedTransactionString = generateSuggestedTransactionString(childTransactionViewModel);
        } else {
            suggestedTransactionString = generateSuggestedTransactionString(transactionViewModel);
        }
        transactionViewModel.setSuggestedTransactionString(suggestedTransactionString);
    }

    private String generateSuggestedTransactionString(TransactionViewModel transactionViewModel) {
        String transactionDecription = "";
        if (transactionViewModel.getTransactionDescription() != null) {
            String[] transactionDecrpStringArr = transactionViewModel.getTransactionDescription().split(" ");
            transactionDecription = transactionDecrpStringArr.length > 1 ? transactionDecrpStringArr[0] + " " + transactionDecrpStringArr[1] : transactionDecrpStringArr[0];
        }
        if (transactionViewModel.getReferenceId() != null) {
            return transactionViewModel.getTransactionTypeName() + " : " + transactionViewModel.getTransactionCategoryName()
                    + " : " + (transactionViewModel.getTransactionDescription() != null ? transactionDecription + " : " : "") + transactionViewModel.getReferenceName() + " : " + transactionViewModel.getContactName()
                    + " : " + transactionViewModel.getCurrencySymbol() + " " + transactionViewModel.getDueAmount()
                    + " : Due On : " + new SimpleDateFormat("MM/dd/yyyy").format(transactionViewModel.getDueOn())
                    + "......";

        } else {
            return transactionViewModel.getTransactionTypeName() + " : " + transactionViewModel.getTransactionCategoryName()
                    + " : " + (transactionViewModel.getTransactionDescription() != null ? transactionDecription : "") + "......";
        }
    }

    public boolean expandTransaction(String btnComponentId, TransactionViewModel transactionViewModel) throws Exception {
        if (transactionViewModel.getChildTransactionList().isEmpty()) {
            expandToggler(btnComponentId, transactionViewModel);
        }
        return true;
    }

    public boolean collapseTransaction(TransactionViewModel parentTransactionViewModelId) throws Exception {
        if (!parentTransactionViewModelId.isParent()) {
            for (TransactionViewModel viewModel : transactionViewModelList) {
                if (parentTransactionViewModelId.getTransactionId() != viewModel.getTransactionId()
                        && viewModel.getParentTransaction() == parentTransactionViewModelId.getParentTransaction()) {
                    viewModel.setExpandIcon(false);
                }
            }
        } else {
            parentTransactionViewModelId.setExpandIcon(false);
            for (TransactionViewModel viewModel : transactionViewModelList) {
                if (parentTransactionViewModelId.getTransactionId() != viewModel.getTransactionId()) {
                    viewModel.setExpandIcon(false);
                }
            }
        }
        return false;
    }

    public boolean collapseTransaction(TransactionViewModel parentTransactionViewModelId, TransactionViewModel currentTransactionViewModelId) throws Exception {
        for (TransactionViewModel viewModel : transactionViewModelList) {
            if (currentTransactionViewModelId.getTransactionId() != viewModel.getTransactionId()) {
                viewModel.setExpandIcon(false);
            }
        }
        return false;
    }

    public void expandToggler(String btnComponentId, TransactionViewModel transactionViewModel) {
        expandedTransactionModel = new TransactionModel();
        if (transactionViewModel.getTransactionId() > 0) {
            for (TransactionModel transactionModel : transactionModelList) {
                if (transactionViewModel.getTransactionId() == transactionModel.getTransactionId()) {
                    System.out.println("time before" + new Date());
                    expandedTransactionModel = transactionModel;
                    System.out.println("time after" + new Date());
                }
            }
        } else {
            expandedTransactionModel.setTransactionId(transactionViewModel.getTransactionId());
            expandedTransactionModel.setDebitCreditFlag(transactionViewModel.getDebitCreditFlag());
            for (TransactionStatus transactionStatus : transactionStatuseList) {
                if (transactionStatus.getExplainationStatusCode() == TransactionStatusConstant.UNEXPLAINED) {
                    expandedTransactionModel.setTransactionStatus(transactionStatus);
                }
            }
            expandedTransactionModel.setTransactionAmount(transactionViewModel.getTransactionAmount());
            for (TransactionModel model : transactionModelList) {
                if (model.getParentTransaction() != null) {
                    if (transactionViewModel.getParentTransaction() == model.getParentTransaction().getTransactionId()) {
                        expandedTransactionModel.setParentTransaction(model.getParentTransaction());
                    }
                }
            }
        }
        int itemIndex = transactionViewModelList.indexOf(transactionViewModel);
        String tempBtnComponentId = btnComponentId.substring(0, btnComponentId.indexOf("transactionsTable:") + 18) + itemIndex + ":collapseExpandBtn";
        btnComponentId = tempBtnComponentId.replace(":", "\\\\:");

        RequestContext.getCurrentInstance()
                .execute(" $('#" + btnComponentId + "').parent().find('.ui-row-toggler').trigger('click');");
    }
}
