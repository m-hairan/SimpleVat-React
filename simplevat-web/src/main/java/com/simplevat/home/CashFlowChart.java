package com.simplevat.home;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.service.bankaccount.BankAccountService;
import com.simplevat.service.bankaccount.TransactionService;

/**
 * Created by mohsin on 5/12/2017.
 */

@Controller
@ManagedBean(name = "CashFlowChart")
@RequestScoped
public class CashFlowChart {

	@Autowired
	public TransactionService transactionService;

	@Autowired
	public BankAccountService bankAccountService;

	public void populateCashFlowChart() {
		createAreaModel();
	}

	public LineChartModel createAreaModel() {
		LineChartModel areaModel = new LineChartModel();
		LineChartSeries cashIn = new LineChartSeries();
		cashIn.setFill(true);

		if (null != transactionService) {
			Map<Object, Number> cashInDataMap = transactionService.getCashInData();
			cashIn.setData(cashInDataMap);
			cashIn.setLabel("Cash In");

			LineChartSeries cashOut = new LineChartSeries();
			cashOut.setFill(true);
			cashOut.setLabel("Cash Out");
			Map<Object, Number> cashOutDataMap = transactionService.getCashOutData();
			cashOut.setData(cashOutDataMap);

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
			int maxValue = transactionService.getMaxTransactionValue();
			yAxis.setMax(maxValue);
		}
		return areaModel;

	}

}
