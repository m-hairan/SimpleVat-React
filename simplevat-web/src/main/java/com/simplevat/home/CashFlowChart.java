package com.simplevat.home;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.*;

/**
 * Created by mohsin on 5/12/2017.
 */


@ManagedBean
public class CashFlowChart implements Serializable {

    private LineChartModel areaModel;

    @PostConstruct
    public void init() {
        createAreaModel();
    }

    public LineChartModel getAreaModel() {
        return areaModel;
    }

    private void createAreaModel() {
        areaModel = new LineChartModel();

        LineChartSeries cashIn = new LineChartSeries();
        cashIn.setFill(true);
        cashIn.setLabel("Cash In");
        cashIn.set("Jan", 120);
        cashIn.set("Feb", 120);
        cashIn.set("March", 100);
        cashIn.set("April", 120);
        cashIn.set("May", 100);
        cashIn.set("June", 44);
        cashIn.set("July", 150);
        cashIn.set("Agust", 25);

        LineChartSeries cashOut = new LineChartSeries();
        cashOut.setFill(true);
        cashOut.setLabel("Cash Out");
        cashOut.set("Jan", 52);
        cashOut.set("Feb", 52);
        cashOut.set("March", 60);
        cashOut.set("April", 52);
        cashOut.set("May", 60);
        cashOut.set("June", 110);
        cashOut.set("July", 90);
        cashOut.set("August", 120);

        areaModel.addSeries(cashIn);
        areaModel.addSeries(cashOut);

        areaModel.setTitle("Cash Flow");
        areaModel.setLegendPosition("ne");
        areaModel.setStacked(true);
        areaModel.setShowPointLabels(true);
        areaModel.setAnimate(true);

        Axis xAxis = new CategoryAxis("Months");
        areaModel.getAxes().put(AxisType.X, xAxis);
        Axis yAxis = areaModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cash Amount");
        yAxis.setMin(0);
        yAxis.setMax(300);

    }

}
