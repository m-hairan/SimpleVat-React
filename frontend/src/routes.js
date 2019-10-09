import React from "react";

const Login = React.lazy(() => import("./views/Login"));
const Dashboard = React.lazy(() => import("./views/Dashboard"));
const GeneralSettings = React.lazy(() =>
  import("./views/Settings/General-settings")
);
const VatCategory = React.lazy(() => import("./views/Settings/Vat-Category"));
const Expense = React.lazy(() => import("./views/Expense/Expense"));
const Purchase = React.lazy(() => import("./views/Expense/Purchase"));
const ExpenseReport = React.lazy(() => import("./views/Report/ExpenseReport"));
const TransactionReport = React.lazy(() => import("./views/Report/TransactionReport"));
const Imports = React.lazy(() => import("./views/Imports/Imports"));
const Employee = React.lazy(() => import("./views/Employee/Employee"));
const InvoiceReport = React.lazy(() => import("./views/Report/InvoiceReport"));
const Invoice = React.lazy(() => import("./views/Invoice"));
const BankAccount = React.lazy(() => import("./views/BankAccount/BankAccount"));
const Taxes = React.lazy(() => import("./views/Taxes/Taxes"));
const CreateOrEditInvoice = React.lazy(() => import("./views/Invoice/CreateOrEditInvoice/CreateOrEditInvoice"));
const CreateOrEditExpense = React.lazy(() => import("./views/Expense/Expense/CreateOrEditExpense/CreateOrEditExpense"));
const CreateOrEditPurchase = React.lazy(() => import("./views/Expense/Purchase/CreateOrEditPurchase/CreateOrEditPurchase"));
const CreateOrEditBankAccount = React.lazy(() => import("./views/BankAccount/CreateOrEditBankAccount/CreateOrEditBankAccount"));
const CreateOrEditVatCategory = React.lazy(() =>
  import(
    "./views/Settings/Vat-Category/CreateOrEditVatCategory/CreateOrEditVatCategory"
  )
);
const CreateOrEditTranactionCategory = React.lazy(() =>
  import(
    "./views/Settings/Transaction-Category/CreateOrEditTransactionCategory/CreateOrEditTransactionCategory"
  )
);
const TransactionCategory = React.lazy(() =>
  import("./views/Settings/Transaction-Category")
);

// https://github.com/ReactTraining/react-router/tree/master/packages/react-router-config
const routes = [
  { path: "/login", exact: true, name: "Login", component: Login },
  // { path: "/", exact: true, name: "Home" },
  { path: "/home", name: "Home", component: Dashboard },
  { path: "/invoice", name: "Invoice", component: Invoice },
  { path: "/bankAccount", name: "BankAccount", component: BankAccount },
  { path: "/Taxes", name: "Taxes", component: Taxes },
  { path: "/create-Invoice", name: "CreateOrEditInvoice", component: CreateOrEditInvoice },
  { path: "/create-Expense", name: "CreateOrEditExpense", component: CreateOrEditExpense },
  { path: "/create-Purchase", name: "CreateOrEditPurchase", component: CreateOrEditPurchase },
  { path: "/create-bank-account", name: "CreateOrEditBankAccount", component: CreateOrEditBankAccount },
  { path: "/Imports", name: "Imports", component: Imports },
  { path: "/Employee", name: "Employee", component: Employee },
  {
    path: "/Report/ExpenseReport",
    name: "ExpenseReport", 
    component: ExpenseReport
  },
  {
    path: "/Report/InvoiceReport",
    name: "InvoiceReport", 
    component: InvoiceReport
  },
  {
    path: "/Report/TransactionReport",
    name: "TransactionReport", 
    component: TransactionReport
  },
  {
    path: "/Expense/Expense",
    name: "Expense", 
    component: Expense
  },
  {
    path: "/Expense/Purchase",
    name: "Purchase",
    component: Purchase
  },
  {
    path: "/settings/general",
    name: "General Settings",
    component: GeneralSettings
  },
  {
    path: "/settings/vat-category",
    name: "Vat Category",
    component: VatCategory
  },
  {
    path: "/create-vat-category",
    name: "Create Vat Category",
    component: CreateOrEditVatCategory
  },
  {
    path: "/create-transaction-category",
    name: "Create Transaction Category",
    component: CreateOrEditTranactionCategory
  },
  {
    path: "/settings/transaction-category",
    name: "Transaction Category",
    component: TransactionCategory
  }
];

export default routes;
