import {
  Home,
  GeneralSettings,
  VatCategory,
  Contact,
  User,
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
  UsersRoles,
  OrganizationProfile,
  Currency,
  Notification,
  DataBackup,
  Help,


  CreateInvoice,
  CreateExpense,
  CreateProduct,
  CreateContact,
  CreateProject,
  CreateUser,
  CreatePurchase,
  CreateBankAccount,
  CreateVatCategory,
  CreateTranactionCategory,

  DetailBankAccount,
  DetailInvoice,
  DetailExpense,
  DetailPurchase,
  DetailContact,
  DetailProject,
  DetailProduct,
  DetailUser,
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
    path: '/admin/invoice/detail',
    name: 'Detail',
    component: DetailInvoice.screen
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
    path: '/admin/expense/expense/detail',
    name: 'Detail',
    component: DetailExpense.screen
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
    path: '/admin/expense/purchase/detail',
    name: 'Detail',
    component: DetailPurchase.screen
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
    path: '/admin/master/contact/detail',
    name: 'Detail',
    component: DetailContact.screen
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
    path: '/admin/master/project/detail',
    name: 'Detail',
    component: DetailProject.screen
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
    path: '/admin/master/product/detail',
    name: 'Detail',
    component: DetailProduct.screen
  },
  {
    path: '/admin/master/product',
    name: 'Product',
    component: Product.screen
  },
  {
    path: '/admin/master/user/create',
    name: 'Create',
    component: CreateUser.screen
  },
  {
    path: '/admin/master/user/detail',
    name: 'Detail',
    component: DetailUser.screen
  },
  {
    path: '/admin/master/user',
    name: 'User',
    component: User.screen
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
    path: '/admin/settings/user-role',
    name: 'Users & Roles',
    component: UsersRoles.screen
  },
  {
    path: '/admin/settings/organization-profile',
    name: 'Organization Profile',
    component: OrganizationProfile.screen
  },
  {
    path: '/admin/settings/currency',
    name: 'Currencies',
    component: Currency.screen
  },
  {
    path: '/admin/settings/notification',
    name: 'Notifications',
    component: Notification.screen
  },
  {
    path: '/admin/settings/data-backup',
    name: 'Data Backup',
    component: DataBackup.screen
  },
  {
    path: '/admin/settings/help',
    name: 'Help',
    component: Help.screen
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