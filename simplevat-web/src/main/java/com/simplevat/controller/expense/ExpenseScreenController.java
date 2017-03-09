package com.simplevat.controller.expense;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean(name = "expenseScreenController")
public class ExpenseScreenController implements Serializable {
	
	private static final long serialVersionUID = -938219424984775517L;

	private boolean showNewExpensePanel;
	
	private boolean showAllExpensePanel;
	
	@PostConstruct
	public void init(){
		this.setShowAllExpensePanel(true);
		this.setShowNewExpensePanel(false);
	}

	public boolean getShowNewExpensePanel() {
		return showNewExpensePanel;
	}

	public void setShowNewExpensePanel(boolean showNewExpensePanel) {
		this.showNewExpensePanel = showNewExpensePanel;
	}

	public boolean getShowAllExpensePanel() {
		return showAllExpensePanel;
	}

	public void setShowAllExpensePanel(boolean showAllExpensePanel) {
		this.showAllExpensePanel = showAllExpensePanel;
	}

}
