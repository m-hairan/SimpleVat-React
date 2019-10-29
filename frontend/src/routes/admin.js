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

  CreateInvoice,
  CreateExpense,
  CreateProduct,
  CreateContact,
  CreateProject,
  CreateUsers,
  CreatePurchase,
  CreateBankAccount,
  CreateVatCategory,
  CreateTranactionCategory,

  DetailBankAccount,
  DetailVatCategory,
  DetailTranactionCategory
} from 'screens'

const adminRoutes = [
  {
    path: '/admin/home',
    name: 'Home',
    component: Home.screen
  },
  {
    path: '/admin/invoice/create',
    name: 'Create',
    component: CreateInvoice.screen
  },
  {
    path: '/admin/invoice',
    name: 'Invoice',
    component: Invoice.screen
  },
  {
    path: '/admin/bank-account/create',
    name: 'Create',
    component: CreateBankAccount.screen
  },
  {
    path: '/admin/bank-account/detail',
    name: 'Detail',
    component: DetailBankAccount.screen
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
    path: '/admin/expense/expense/create',
    name: 'Create',
    component: CreateExpense.screen
  },
  {
    path: '/admin/expense/expense',
    name: 'Expense', 
    component: Expense.screen
  },
  {
    path: '/admin/expense/purchase/create',
    name: 'Create',
    component: CreatePurchase.screen
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
    path: '/admin/master/contact/create',
    name: 'Create',
    component: CreateContact.screen
  },
  {
    path: '/admin/master/contact',
    name: 'Contact',
    component: Contact.screen
  },
  {
    path: '/admin/master/project/create',
    name: 'Create',
    component: CreateProject.screen
  },
  {
    path: '/admin/master/project',
    name: 'Project',
    component: Project.screen
  },
  {
    path: '/admin/master/product/create',
    name: 'Create',
    component: CreateProduct.screen
  },
  {
    path: '/admin/master/product',
    name: 'Product',
    component: Product.screen
  },
  {
    path: '/admin/master/users/create',
    name: 'Create',
    component: CreateUsers.screen
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
    path: '/admin/settings/vat-category/create',
    name: 'Create',
    component: CreateVatCategory.screen
  },
  {
    path: '/admin/settings/vat-category/detail',
    name: 'Detail',
    component: DetailVatCategory.screen
  },
  {
    path: '/admin/settings/vat-category',
    name: 'Vat Category',
    component: VatCategory.screen
  },
  {
    path: '/admin/settings/transaction-category/create',
    name: 'Create',
    component: CreateTranactionCategory.screen
  },
  {
    path: '/admin/settings/transaction-category/detail',
    name: 'Detail',
    component: DetailTranactionCategory.screen
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