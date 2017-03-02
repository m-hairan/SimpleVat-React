package org.simplevat.entity.expense;

public enum ExpenseType {

    MORTGAGE("Mortgage"),
    GAS("GAS"),
    ELECTRICITY("Electricity"),
    WATER("Water"),
    EMPLOYEE_SALARY("Employee Salary"),
    INSURANCE("Insurance"),
    MACHINERY("Machinery");

    final private String type;

    private ExpenseType(final String type) {
        this.type = type;
    }

}
