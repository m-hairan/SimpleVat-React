package org.simplevat.controller.expense;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.simplevat.entity.expense.Expense;
import org.simplevat.entity.expense.ExpenseType;
import org.simplevat.model.ExpenseModel;
import org.simplevat.service.expense.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@ViewScoped
@ManagedBean(name = "expenseListController")
@Controller
public class ExpenseListController implements Serializable {

    private static final long serialVersionUID = 3873334178066646197L;
    
    @Autowired
    public ExpenseService expenseService;
    
    @Nonnull
    @Getter
    @Setter
    private List<ExpenseModel> expenseList;
    
    @PostConstruct
    public void init() {
        List<Expense> expenses = expenseService.getExpenseList();
        
        double gasTotalExpense = 0;
        double waterTotalExpense = 0;
        double salaryTotalExpense = 0;
        double insuranceTotalExpense = 0;
        double electricityTotalExpense = 0;
        double machineryTotalExpense = 0;
        double mortagageTotalExpense = 0;
            
        for(Expense e : expenses){
            
            if(ExpenseType.GAS.equals(e.getType())){
                gasTotalExpense = gasTotalExpense + e.getAmount(); 
            }
            if(ExpenseType.WATER.equals(e.getType())){
                waterTotalExpense = waterTotalExpense + e.getAmount(); 
            }
            if(ExpenseType.EMPLOYEE_SALARY.equals(e.getType())){
                salaryTotalExpense = salaryTotalExpense + e.getAmount(); 
            }
            if(ExpenseType.ELECTRICITY.equals(e.getType())){
                electricityTotalExpense = electricityTotalExpense + e.getAmount(); 
            }
            if(ExpenseType.INSURANCE.equals(e.getType())){
                insuranceTotalExpense = insuranceTotalExpense + e.getAmount(); 
            }
            if(ExpenseType.MACHINERY.equals(e.getType())){
                machineryTotalExpense = machineryTotalExpense + e.getAmount(); 
            }
            if(ExpenseType.MORTGAGE.equals(e.getType())){
                mortagageTotalExpense = mortagageTotalExpense + e.getAmount(); 
            }
            
        }
        
        List<ExpenseModel> expenseModelList = new ArrayList<ExpenseModel>();
        
        expenseModelList.add(new ExpenseModel(ExpenseType.GAS, gasTotalExpense));
        expenseModelList.add(new ExpenseModel(ExpenseType.WATER, waterTotalExpense));
        expenseModelList.add(new ExpenseModel(ExpenseType.ELECTRICITY, electricityTotalExpense));
        expenseModelList.add(new ExpenseModel(ExpenseType.EMPLOYEE_SALARY, salaryTotalExpense));
        expenseModelList.add(new ExpenseModel(ExpenseType.INSURANCE, insuranceTotalExpense));
        expenseModelList.add(new ExpenseModel(ExpenseType.MORTGAGE, mortagageTotalExpense));
        expenseModelList.add(new ExpenseModel(ExpenseType.MACHINERY, machineryTotalExpense));
        
        this.setExpenseList(expenseModelList);
       
    }
    
    public List<Expense> getTypeExpenseList(ExpenseType type){
        return expenseService.getTypeExpenseList(type);
    }
  
}
