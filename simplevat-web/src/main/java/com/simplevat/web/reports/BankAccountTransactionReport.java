package com.simplevat.web.reports;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.simplevat.web.reports.model.BankAccountTransactionReportModel;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean(name = "bankAccountTransactionReport")
@SessionScoped
public class BankAccountTransactionReport extends AbstractReportBean {
	
    private final String COMPILE_FILE_NAME = "BankAccountTransaction";
    
    @Override
    protected String getCompileFileName() {
        return COMPILE_FILE_NAME;
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
    
    @Override
    protected Map<String, Object> getReportParameters() {
 
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("REPORT_NAME", "Bank Account Transaction Report");
        parameters.put("FROM_DATE", new Date());
        parameters.put("TO_DATE", new Date());
        parameters.put("COMPANY_NAME", "Demo Company Name");
        parameters.put("openingBalance", new BigDecimal(Math.max(100, 300)));
        parameters.put("closingBalance", new BigDecimal(Math.max(100, 300)));
        parameters.put("currency","$");
        List<BankAccountTransactionReportModel> bankAccountTransactionList = getReportModel();
        parameters.put("MAIN_DATA", new JRBeanCollectionDataSource(bankAccountTransactionList));

 
        return parameters;
    }
    
    protected List<BankAccountTransactionReportModel>  getReportModel() {
    	List<BankAccountTransactionReportModel> list = new ArrayList<>();
    	for(int i=0;i<50;i++) {
    		BankAccountTransactionReportModel model = new BankAccountTransactionReportModel();
    		model.setDate(new Date());
    		model.setReference("Refence " + i+1);
    		model.setTransaction("Transaction " + i+1);
    		model.setCredit(new BigDecimal(Math.max(100, 200)));
    		model.setDebit(new BigDecimal(Math.max(100, 200)));
    		model.setType("Type " + i+1);
    		list.add(model);
    	}
    	
    	return list;
    }
 

}
