import React from "react";

const Login = React.lazy(() => import("./views/Login"));
const Dashboard = React.lazy(() => import("./views/Dashboard"));
const GeneralSettings = React.lazy(() =>
  import("./views/Settings/General-settings")
);
const VatCategory = React.lazy(() => import("./views/Settings/Vat-Category"));
const Invoice = React.lazy(() => import("./views/Invoice"));
const BankAccount = React.lazy(() => import("./views/BankAccount/BankAccount"));
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
  { path: "/create-bank-account", name: "CreateOrEditBankAccount", component: CreateOrEditBankAccount },
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
