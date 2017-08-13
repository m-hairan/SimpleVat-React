package com.simplevat.web.home.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

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
        Axis xAxis = new CategoryAxis("Months");
        animatedModel1.getAxes().put(AxisType.X, xAxis);
        Axis yAxis = animatedModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        if(invoiceService.getMaxValue(invoiseData) >= expenseService.getMaxValue(expesneData)) {
        	yAxis.setMax(invoiceService.getMaxValue(invoiseData));
        } else {
        	yAxis.setMax(invoiceService.getMaxValue(expesneData));
        }
        
       	Map<Object,Number> vatInData = invoiceService.getVatInPerMonth();
    	Map<Object,Number> vatOutData = expenseService.getVatOutPerMonth();
        animatedModel2 = initBarModel(vatInData,vatOutData);
        animatedModel2.setTitle("VAT");
        animatedModel2.setAnimate(true);
        animatedModel2.setLegendPosition("ne");
        animatedModel2.setStacked(true);

        yAxis = animatedModel2.getAxis(AxisType.Y);
        yAxis.setMin(0);
        int maxValue = getMaxForVat(vatInData, vatOutData);
        yAxis.setMax(maxValue);
    }
    
    private int getMaxForVat(Map<Object,Number> vatInData, Map<Object,Number> vatOutData) {
    	Map<Object, Number> tempMap = new LinkedHashMap<>(0);
    	Set<Object> keys = vatInData.keySet();
    	for(Object key : keys) {
    		if(vatOutData.containsKey(key)) {
    			tempMap.put(key, (vatInData.get(key).doubleValue() + vatOutData.get(key).doubleValue()));
    		}
    	}
    	return expenseService.getMaxValue(tempMap);
    }

    private BarChartModel initBarModel(Map<Object,Number> vatInData, Map<Object,Number> vatOutData) {
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
