/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.web.home.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Activity;
import com.simplevat.entity.Event;
import com.simplevat.entity.bankaccount.BankAccount;
import com.simplevat.service.ActivityService;
import com.simplevat.service.EventService;
import com.simplevat.service.ExpenseService;
import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.service.bankaccount.TransactionService;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.web.utils.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author uday
 */
@Controller
@SpringScopeView
public class HomeController implements Serializable {

    @Autowired
    public TransactionService transactionService;
    @Autowired
    public BankAccountService bankAccountService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private EventService eventService;
    @Autowired
    private ActivityService activityService;

    @Getter
    @Setter
    private LineChartModel cashFlowLineChartModel;
    @Getter
    private boolean renderCashFlowLineChartModel;
    @Getter
    @Setter
    private List<BankAccount> bankAccountList;
    @Getter
    private int vatIn;
    @Getter
    private int vatOut;
    @Getter
    private int vatBalance;
    @Getter
    private int numberOfTransaction;
    @Getter
    @Setter
    private LineChartModel animatedModel1;
    @Getter
    @Setter
    private BarChartModel animatedModel2;
    @Getter
    @Setter
    private ScheduleModel eventModel;
    @Getter
    private List<Activity> activities;
    private DashboardModel model;
    @Getter
    @Setter
    private int selectedBankAccountId;

    @PostConstruct
    public void init() {
        cashFlowLineChartModel = new LineChartModel();
        bankAccountList = new ArrayList<>();
        animatedModel1 = new LineChartModel();
        animatedModel2 = new BarChartModel();
        eventModel = new DefaultScheduleModel();
        activities = new ArrayList<>();

        model = new DefaultDashboardModel();
        DefaultDashboardColumn column1 = new DefaultDashboardColumn();
        DefaultDashboardColumn column2 = new DefaultDashboardColumn();
        DefaultDashboardColumn column3 = new DefaultDashboardColumn();
        column1.setStyleClass("ui-g-12 ui-md-4");
        column2.setStyleClass("ui-g-12 ui-md-4");
        column3.setStyleClass("ui-g-12 ui-md-4");

        column1.addWidget("cashflow");
        column2.addWidget("invoiceExpanse");
        column3.addWidget("vatActivity");

        column1.addWidget("bankAccount");
        column2.addWidget("scheduler");
        column3.addWidget("activity");

        model.addColumn(column1);
        model.addColumn(column2);
        model.addColumn(column3);
    }

    public void lazyInitializationFlowChart() {
        populateCashFlowChart();
    }

    public void lazyInitializationBankAccount() {
        populateAccountDetails();
    }

    public void lazyInitializationVatInOutCalculation() {
        populateVatDetails();
    }

    public void lazyInitializationInvoiceAndExpanse() {
        populateInvoiceAndExpanseChart();
    }

    public void lazyInitializationVatActivity() {
        populateVatInOutChart();
    }

    public void lazyInitializationEvent() {
        populateEventModel();

    }

    public void lazyInitializationActivity() {
        populateLatestActivity();
    }
    
    public String loadBankAccountTransaction(){
        FacesUtil.setSelectedBankAccountId(selectedBankAccountId);
        return "/pages/secure/bankaccount/bank-transactions?faces-redirect=true";
    }

    private void populateCashFlowChart() {
        renderCashFlowLineChartModel = false;
        cashFlowLineChartModel = new LineChartModel();
        cashFlowLineChartModel.setLegendPosition("n");
        cashFlowLineChartModel.setLegendPlacement(LegendPlacement.OUTSIDE);
        cashFlowLineChartModel.setLegendCols(2);
        cashFlowLineChartModel.setLegendRows(1);
        cashFlowLineChartModel.setExtender("areaChart");

        LineChartSeries cashIn = new LineChartSeries();
        cashIn.setFill(true);

        Map<Object, Number> cashInDataMap = transactionService.getCashInData();
        cashIn.setData(cashInDataMap);
        cashIn.setLabel("Cash In");

        LineChartSeries cashOut = new LineChartSeries();
        cashOut.setFill(true);
        cashOut.setLabel("Cash Out");
        Map<Object, Number> cashOutDataMap = transactionService.getCashOutData();
        cashOut.setData(cashOutDataMap);

        cashFlowLineChartModel.addSeries(cashIn);
        cashFlowLineChartModel.addSeries(cashOut);

        cashFlowLineChartModel.setTitle("Cash Flow");
        cashFlowLineChartModel.setStacked(true);
        cashFlowLineChartModel.setShowPointLabels(true);
        cashFlowLineChartModel.setAnimate(true);

        Axis xAxis = new CategoryAxis("Months");        
        xAxis.setTickAngle(45);
        cashFlowLineChartModel.getAxes().put(AxisType.X, xAxis);
        Axis yAxis = cashFlowLineChartModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cash Amount");
        yAxis.setMin(0);
        int maxValue = transactionService.getMaxTransactionValue(cashInDataMap, cashOutDataMap);
        yAxis.setMax(maxValue);
        if (cashInDataMap != null && !cashInDataMap.isEmpty() && cashOutDataMap != null && !cashOutDataMap.isEmpty()) {
            renderCashFlowLineChartModel = true;
        }
    }

    private void populateAccountDetails() {
        bankAccountList = new ArrayList<>();
        try {
            bankAccountList = bankAccountService.getBankAccounts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateVatDetails() {
        vatIn = invoiceService.getVatInQuartly();
        vatOut = expenseService.getVatOutQuartly();
        vatBalance = vatIn - vatOut;
    }

    private void populateEventModel() {
        eventModel = new DefaultScheduleModel();
        List<Event> events = eventService.getEvents();
        for (Event event : events) {
            eventModel.addEvent(new DefaultScheduleEvent(event.getTitle(), event.getStartDate(), event.getEndDate()));
        }
    }

    private void populateLatestActivity() {
        activities = activityService.getLatestActivites(6);
    }

    private void populateInvoiceAndExpanseChart() {
        Map<Object, Number> invoiseData = invoiceService.getInvoicePerMonth();
        Map<Object, Number> expesneData = expenseService.getExpensePerMonth();
        animatedModel1 = initLinearModel(invoiseData, expesneData);
        animatedModel1.setTitle("Invoices and Expenses");
        animatedModel1.setAnimate(true);
        animatedModel1.setLegendPosition("n");
        animatedModel1.setLegendPlacement(LegendPlacement.OUTSIDE);
        animatedModel1.setLegendCols(2);
        animatedModel1.setLegendRows(1);
        animatedModel1.setExtender("lineChart");
        Axis xAxis = new CategoryAxis("Months");
        xAxis.setTickAngle(45);
        animatedModel1.getAxes().put(AxisType.X, xAxis);
        Axis yAxis = animatedModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        if (invoiceService.getMaxValue(invoiseData) >= expenseService.getMaxValue(expesneData)) {
            yAxis.setMax(invoiceService.getMaxValue(invoiseData));
        } else {
            yAxis.setMax(invoiceService.getMaxValue(expesneData));
        }
    }

    private void populateVatInOutChart() {
        Map<Object, Number> vatInData = invoiceService.getVatInPerMonth();
        Map<Object, Number> vatOutData = expenseService.getVatOutPerMonth();
        animatedModel2 = initBarModel(vatInData, vatOutData);
        animatedModel2.setTitle("VAT");
        animatedModel2.setAnimate(true);
        animatedModel2.setLegendPosition("n");
        animatedModel2.setLegendPlacement(LegendPlacement.OUTSIDE);
        animatedModel2.setLegendCols(2);
        animatedModel2.setLegendRows(1);
        animatedModel2.setStacked(true);
        animatedModel2.setExtender("barChart");
        Axis xAxis = new CategoryAxis("Months");
        xAxis.setTickAngle(45);

        Axis yAxis = animatedModel2.getAxis(AxisType.Y);
        yAxis.setMin(0);
        animatedModel2.getAxes().put(AxisType.X, xAxis);
        int maxValue = getMaxForVat(vatInData, vatOutData);
        yAxis.setMax(maxValue);
    }

    private int getMaxForVat(Map<Object, Number> vatInData, Map<Object, Number> vatOutData) {
        Map<Object, Number> tempMap = new LinkedHashMap<>(0);
        Set<Object> keys = vatInData.keySet();
        for (Object key : keys) {
            if (vatOutData.containsKey(key)) {
                tempMap.put(key, (vatInData.get(key).doubleValue() + vatOutData.get(key).doubleValue()));
            }
        }
        return expenseService.getMaxValue(tempMap);
    }

    private BarChartModel initBarModel(Map<Object, Number> vatInData, Map<Object, Number> vatOutData) {
        BarChartModel model = new BarChartModel();

        ChartSeries vatIn = new ChartSeries();
        vatIn.setLabel("VAT Received");

        vatIn.setData(vatInData);

        ChartSeries vatOut = new ChartSeries();
        vatOut.setLabel("VAT Paid");

        vatOut.setData(vatOutData);

        model.addSeries(vatIn);
        model.addSeries(vatOut);

        return model;
    }

    private LineChartModel initLinearModel(Map<Object, Number> invoiceData, Map<Object, Number> expenseData) {
        LineChartModel model = new LineChartModel();

        LineChartSeries invoices = new LineChartSeries();
        invoices.setData(invoiceData);
        invoices.setLabel("Invoices");

        LineChartSeries expenses = new LineChartSeries();
        expenses.setLabel("Expenses");
        expenses.setData(expenseData);

        model.addSeries(invoices);
        model.addSeries(expenses);

        return model;
    }

    /**
     * @return the model
     */
    public DashboardModel getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(DashboardModel model) {
        this.model = model;
    }
}
