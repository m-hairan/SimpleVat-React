import {
  Home,
  GeneralSettings,
  VatCategory,
  Contact,
  Users,
  Project,
  Product,
  Expense,
  Purchase,
  ExpenseReport,
  TransactionReport,
  TaxReport,
  Imports,
  Employee,
  InvoiceReport,
  Invoice,
  BankAccount,
  Taxes,
  ProfitAndLoss,
  BalanceSheet,
  CashFlowPosition,
  TransactionCategory,
  CreateOrEditInvoice,
  CreateOrEditExpense,
  CreateOrEditProduct,
  CreateOrEditContact,
  CreateOrEditProject,
  CreateOrEditUsers,
  CreateOrEditPurchase,
  CreateOrEditBankAccount,
  CreateOrEditVatCategory,
  CreateOrEditTranactionCategory
} from 'screens'

const adminRoutes = [
  {
    path: '/admin/home',
    name: 'Home',
    component: Home.screen
  },
  {
    path: '/admin/invoice/update',
    name: 'Update',
    component: CreateOrEditInvoice.screen
  },
  {
    path: '/admin/invoice',
    name: 'Invoice',
    component: Invoice.screen
  },
  {
    path: '/admin/bank-account/update',
    name: 'Update',
    component: CreateOrEditBankAccount.screen
  },
  {
    path: '/admin/bank-account',
    name: 'Bank Account',
    component: BankAccount.screen
  },
  {
    path: '/admin/taxes',
    name: 'Taxes',
    component: Taxes.screen
  },
  {
    path: '/admin/imports',
    name: 'Imports',
    component: Imports.screen
  },
  {
    path: '/admin/employee',
    name: 'Employee',
    component: Employee.screen
  },
  {
    path: '/admin/report/expense-report',
    name: 'Expense Report', 
    component: ExpenseReport.screen
  },
  {
    path: '/admin/report/invoice-report',
    name: 'Invoice Report', 
    component: InvoiceReport.screen
  },
  {
    path: '/admin/report/transaction-report',
    name: 'Transaction Report', 
    component: TransactionReport.screen
  },
  {
    path: '/admin/report/tax-report',
    name: 'Tax Report', 
    component: TaxReport.screen
  },
  {
    path: '/admin/report/profit-and-loss',
    name: 'Profit and Loss', 
    component: ProfitAndLoss.screen
  },
  {
    path: '/admin/report/balance-sheet',
    name: 'Balance Sheet', 
    component: BalanceSheet.screen
  },
  {
    path: '/admin/report/cash-flow-position',
    name: 'Cash Flow / Position', 
    component: CashFlowPosition.screen
  },
  {
    redirect: true,
    path: '/admin/report',
    pathTo: '/admin/report/transaction-report',
    name: 'Report'
  },
  {
    path: '/admin/expense/expense/update',
    name: 'Update',
    component: CreateOrEditExpense.screen
  },
  {
    path: '/admin/expense/expense',
    name: 'Expense', 
    component: Expense.screen
  },
  {
    path: '/admin/expense/purchase/update',
    name: 'Update',
    component: CreateOrEditPurchase.screen
  },
  {
    path: '/admin/expense/purchase',
    name: 'Purchase',
    component: Purchase.screen
  },
  {
    redirect: true,
    path: '/admin/expense',
    pathTo: '/admin/expense/expense',
    name: 'Expense'
  },
  {
    path: '/admin/master/contact/update',
    name: 'Update',
    component: CreateOrEditContact.screen
  },
  {
    path: '/admin/master/contact',
    name: 'Contact',
    component: Contact.screen
  },
  {
    path: '/admin/master/project/update',
    name: 'Update',
    component: CreateOrEditProject.screen
  },
  {
    path: '/admin/master/project',
    name: 'Project',
    component: Project.screen
  },
  {
    path: '/admin/master/product/update',
    name: 'Update',
    component: CreateOrEditProduct.screen
  },
  {
    path: '/admin/master/product',
    name: 'Product',
    component: Product.screen
  },
  {
    path: '/admin/master/users/update',
    name: 'Update',
    component: CreateOrEditUsers.screen
  },
  {
    path: '/admin/master/users',
    name: 'Users',
    component: Users.screen
  },
  {
    redirect: true,
    path: '/admin/master',
    pathTo: '/admin/master/product',
    name: 'Master'
  },
  {
    path: '/admin/settings/general',
    name: 'General Settings',
    component: GeneralSettings.screen
  },
  {
    path: '/admin/settings/vat-category/update',
    name: 'Update',
    component: CreateOrEditVatCategory.screen
  },
  {
    path: '/admin/settings/vat-category',
    name: 'Vat Category',
    component: VatCategory.screen
  },
  {
    path: '/admin/settings/transaction-category/update',
    name: 'Update',
    component: CreateOrEditTranactionCategory.screen
  },
  {
    path: '/admin/settings/transaction-category',
    name: 'Transaction Category',
    component: TransactionCategory.screen
  },
  {
    redirect: true,
    path: '/admin/settings',
    pathTo: '/admin/settings/general',
    name: 'Settings'
  },
  {
    redirect: true,
    path: '/admin',
    pathTo: '/admin/home',
    name: 'Admin'
  }
]

export default adminRoutes