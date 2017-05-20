package com.simplevat.home;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.*;

/**
 * Created by mohsin on 5/12/2017.
 */

@ManagedBean
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
        yAxis.setMax(200);
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries moneyIn = new ChartSeries();
//        moneyIn.setFill(true);
        moneyIn.setLabel("VAT Received");
        moneyIn.set("Jan", 120);
        moneyIn.set("Feb", 120);
        moneyIn.set("March", 100);
        moneyIn.set("April", 120);
        moneyIn.set("May", 100);
        moneyIn.set("June", 44);
        moneyIn.set("July", 150);
        moneyIn.set("Agust", 25);

        ChartSeries moneyOut = new ChartSeries();
//        moneyOut.setFill(true);
        moneyOut.setLabel("VAT Paid");
        moneyOut.set("Jan", 52);
        moneyOut.set("Feb", 52);
        moneyOut.set("March", 60);
        moneyOut.set("April", 52);
        moneyOut.set("May", 60);
        moneyOut.set("June", 110);
        moneyOut.set("July", 90);
        moneyOut.set("August", 120);

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
        invoices.set("March", 3);
        invoices.set("April", 6);
        invoices.set("May", 8);
        invoices.set("June",8);
        invoices.set("July", 2);
        invoices.set("August", 1);
        invoices.set("September", 3);
        invoices.set("October", 6);
        invoices.set("November", 8);
        invoices.set("December",8);

        LineChartSeries expenses = new LineChartSeries();
        expenses.setLabel("Expenses");
//        expenses.setFill(true);

        expenses.set("Jan", 6);
        expenses.set("Feb", 3);
        expenses.set("March", 2);
        expenses.set("April", 7);
        expenses.set("May", 9);
        expenses.set("June", 6);
        expenses.set("July", 3);
        expenses.set("August", 2);
        expenses.set("September", 7);
        expenses.set("October", 9);
        expenses.set("November", 7);
        expenses.set("December", 9);

        model.addSeries(invoices);
        model.addSeries(expenses);

        return model;
    }

}
