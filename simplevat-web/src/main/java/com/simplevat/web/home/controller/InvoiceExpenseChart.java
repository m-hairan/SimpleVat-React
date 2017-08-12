package com.simplevat.web.home.controller;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.service.ExpenseService;
import com.simplevat.service.invoice.InvoiceService;

/**
 * Created by mohsin on 5/12/2017.
 */
@Controller
@SpringScopeView
//@ManagedBean
public class InvoiceExpenseChart implements Serializable {

    private LineChartModel animatedModel1;
    private BarChartModel animatedModel2;
    
    @Autowired
    private InvoiceService invoiceService;
    
    @Autowired
    private ExpenseService expenseService;

    @PostConstruct
    public void init() {
        createAnimatedModels();
    }

    public LineChartModel getAnimatedModel1() {
        return animatedModel1;
    }

    public BarChartModel getAnimatedModel2() {
        return animatedModel2;
    }

    private void createAnimatedModels() {
    	Map<Object,Number> invoiseData = invoiceService.getInvoicePerMonth();
    	Map<Object,Number> expesneData = expenseService.getExpensePerMonth();
        animatedModel1 = initLinearModel(invoiseData,expesneData);
        animatedModel1.setTitle("Invoices and Expenses");
        animatedModel1.setAnimate(true);
        animatedModel1.setLegendPosition("se");
//        animatedModel1.setStacked(true);
//        animatedModel1.setShowPointLabels(true);
        Axis xAxis = new CategoryAxis("Months");
        animatedModel1.getAxes().put(AxisType.X, xAxis);
        Axis yAxis = animatedModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        if(invoiceService.getMaxValue(invoiseData) >= expenseService.getMaxValue(expesneData)) {
        	yAxis.setMax(invoiceService.getMaxValue(invoiseData));
        } else {
        	yAxis.setMax(invoiceService.getMaxValue(expesneData));
        }
        

        animatedModel2 = initBarModel();
        animatedModel2.setTitle("VAT");
        animatedModel2.setAnimate(true);
        animatedModel2.setLegendPosition("ne");
        animatedModel2.setStacked(true);

        yAxis = animatedModel2.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(300);
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries moneyIn = new ChartSeries();
//        moneyIn.setFill(true);
        moneyIn.setLabel("VAT Received");
        moneyIn.set("Jan", 120);
        moneyIn.set("Feb", 120);
        moneyIn.set("Mar", 100);
        moneyIn.set("Apr", 120);
        moneyIn.set("May", 100);
        moneyIn.set("Jun", 44);
        moneyIn.set("Jul", 150);
        moneyIn.set("Aug", 25);
        moneyIn.set("Sep", 75);
        moneyIn.set("Oct", 100);
        moneyIn.set("Nov", 90);
        moneyIn.set("Dec",80);

        ChartSeries moneyOut = new ChartSeries();
//        moneyOut.setFill(true);
        moneyOut.setLabel("VAT Paid");
        moneyOut.set("Jan", 52);
        moneyOut.set("Feb", 52);
        moneyOut.set("Mar", 60);
        moneyOut.set("Apr", 52);
        moneyOut.set("May", 60);
        moneyOut.set("Jun", 110);
        moneyOut.set("Jul", 90);
        moneyOut.set("Aug", 120);
        moneyOut.set("Sep", 50);
        moneyOut.set("Oct", 60);
        moneyOut.set("Nov", 80);
        moneyOut.set("Dec",80);

        model.addSeries(moneyIn);
        model.addSeries(moneyOut);

        return model;
    }

    private LineChartModel initLinearModel(Map<Object,Number> invoiceData, Map<Object,Number> expenseData) {
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

}
