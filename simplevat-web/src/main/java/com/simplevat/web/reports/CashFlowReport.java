package com.simplevat.web.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.simplevat.web.reports.model.ExpenseMonthWise;
import com.simplevat.web.reports.model.ExpenseReportModel;
import com.simplevat.web.reports.model.InvoiceReportModel;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
 
@ManagedBean(name = "cashFlowReport")
@SessionScoped
public class CashFlowReport extends AbstractReportBean {
 
    private final String COMPILE_FILE_NAME = "CashFlow";
 
    @Override
    protected String getCompileFileName() {
        return COMPILE_FILE_NAME;
    }
 
    @Override
    protected Map<String, Object> getReportParameters() {
 
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("REPORT_NAME", "Cash Flow");
        parameters.put("FROM_DATE", new Date());
        parameters.put("TO_DATE", new Date());
        
        List<ExpenseReportModel> expenseReportModelList = getExpenseReportModel();
        parameters.put("CASH_OUT", new JRBeanCollectionDataSource(expenseReportModelList));        

        List<InvoiceReportModel> invoiceReportModelList = getInvoiceReportModels();
        parameters.put("CASH_IN", new JRBeanCollectionDataSource(invoiceReportModelList));
        
        parameters.put("CASH_FLOW_CHART_DATA", new JRBeanCollectionDataSource(getExpenseMonthWise()));
 
        return parameters;
    }
 
    public String execute() {
        try {
            super.prepareReport();
        } catch (Exception e) {
            // make your own exception handling
            e.printStackTrace();
        }
 
        return null;
    }
    
    private static List<ExpenseMonthWise> getExpenseMonthWise() {
    	List<ExpenseMonthWise> list = new ArrayList<ExpenseMonthWise>();
    	list.add(new ExpenseMonthWise("Jan-17", 100,30));
    	list.add(new ExpenseMonthWise("Feb-17", 200,100));
    	list.add(new ExpenseMonthWise("Mar-17", 100, 70));
    	list.add(new ExpenseMonthWise("Apr-17", 500, 300));
    	list.add(new ExpenseMonthWise("May-17", 100,20));
    	list.add(new ExpenseMonthWise("Jun-17", 100, 30));
    	list.add(new ExpenseMonthWise("Jul-17", 300, 150));
    	list.add(new ExpenseMonthWise("Aug-17", 100,80));
    	list.add(new ExpenseMonthWise("Sep-17", 100, 60));
    	list.add(new ExpenseMonthWise("Oct-17", 350, 300));
    	list.add(new ExpenseMonthWise("Nov-17", 150,100));
    	list.add(new ExpenseMonthWise("Dec-17", 100,20));
    	
    	return list;
    }
    
    private static List<InvoiceReportModel> getInvoiceReportModels() {
        List<InvoiceReportModel> invoiceReportModelList = new ArrayList<>();
        for(int i=0;i<15;i++) {
        	InvoiceReportModel invoiceReportModel = new InvoiceReportModel();
        	invoiceReportModel.setCurrency("$");
        	invoiceReportModel.setDiscountType("Discount Type");
        	invoiceReportModel.setInvoiceAmount((i+1)*100);
        	invoiceReportModel.setInvoiceDate(new Date());
        	invoiceReportModel.setInvoiceDiscount(10);
        	invoiceReportModel.setInvoiceDueOn((i+1) * 10);
        	invoiceReportModel.setInvoiceId(i);
        	invoiceReportModel.setInvoiceReferenceNumber("Ref Num" + i);
        	invoiceReportModel.setInvoiceText("InvText" + i);
        	invoiceReportModel.setProject("Project");
        	invoiceReportModelList.add(invoiceReportModel);
        	
        }
        return invoiceReportModelList;
    }
    
    private static List<ExpenseReportModel> getExpenseReportModel() {
        List<ExpenseReportModel> lists = new ArrayList<>();
        for(int i=0;i<15;i++) {
        	ExpenseReportModel model = new ExpenseReportModel();
        	model.setExpenseDate(new Date());
        	model.setExpenseId(i);
        	model.setExpenseAmount(i);
        	model.setCurrency("$");
        	model.setExpenseDescription("Some Expense Description");
        	model.setProject("Project " + i);
        	model.setReceiptNumber("Reciept " + i);
        	model.setTransactionCategory("Sell");
        	model.setTransactionType("Type ");
        	lists.add(model);
        }
        return lists;
    }
}
