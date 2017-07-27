package com.simplevat.web.home.controller;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Created by mohsin on 5/12/2017.
 */
@Controller
@SessionScope
//@ManagedBean
public class InvoiceExpenseChart implements Serializable {

    private LineChartModel animatedModel1;
    private BarChartModel animatedModel2;

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
        animatedModel1 = initLinearModel();
        animatedModel1.setTitle("Invoices and Expenses");
        animatedModel1.setAnimate(true);
        animatedModel1.setLegendPosition("se");
//        animatedModel1.setStacked(true);
//        animatedModel1.setShowPointLabels(true);
        Axis xAxis = new CategoryAxis("Months");
        animatedModel1.getAxes().put(AxisType.X, xAxis);
        Axis yAxis = animatedModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);

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

    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();

        LineChartSeries invoices = new LineChartSeries();
        invoices.setLabel("Invoices");
//        invoices.setFill(true);

        invoices.set("Jan", 2);
        invoices.set("Feb", 1);
        invoices.set("Mar", 3);
        invoices.set("Apr", 6);
        invoices.set("May", 8);
        invoices.set("Jun",8);
        invoices.set("Jul", 2);
        invoices.set("Aug", 1);
        invoices.set("Sep", 3);
        invoices.set("Oct", 6);
        invoices.set("Nov", 8);
        invoices.set("Dec",8);

        LineChartSeries expenses = new LineChartSeries();
        expenses.setLabel("Expenses");
//        expenses.setFill(true);

        expenses.set("Jan", 6);
        expenses.set("Feb", 3);
        expenses.set("Mar", 2);
        expenses.set("Apr", 7);
        expenses.set("May", 9);
        expenses.set("Jun", 6);
        expenses.set("Jul", 3);
        expenses.set("Aug", 2);
        expenses.set("Sep", 7);
        expenses.set("Oct", 9);
        expenses.set("Nov", 7);
        expenses.set("Dec", 9);

        model.addSeries(invoices);
        model.addSeries(expenses);

        return model;
    }

}
