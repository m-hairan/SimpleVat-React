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
  Imports,
  Employee,
  InvoiceReport,
  Invoice,
  BankAccount,
  Taxes,
  CreateOrEditInvoice,
  CreateOrEditExpense,
  CreateOrEditProduct,
  CreateOrEditContact,
  CreateOrEditProject,
  CreateOrEditUsers,
  CreateOrEditPurchase,
  CreateOrEditBankAccount,
  CreateOrEditVatCategory,
  CreateOrEditTranactionCategory,
  TransactionCategory
} from 'screens'

const adminRoutes = [
  {
    path: '/admin/home',
    name: 'Home',
    component: Home.screen
  },
  {
    path: '/admin/invoice',
    name: 'Invoice',
    component: Invoice.screen
  },
  {
    path: '/admin/bank-account',
    name: 'BankAccount',
    component: BankAccount.screen
  },
  {
    path: '/admin/taxes',
    name: 'Taxes',
    component: Taxes.screen
  },
  {
    path: '/admin/create-invoice',
    name: 'Invoice',
    component: CreateOrEditInvoice.screen
  },
  {
    path: '/admin/create-expense',
    name: 'Expense',
    component: CreateOrEditExpense.screen
  },
  {
    path: '/admin/create-product',
    name: 'Product',
    component: CreateOrEditProduct.screen
  },
  {
    path: '/admin/create-users',
    name: 'Users',
    component: CreateOrEditUsers.screen
  },
  {
    path: '/admin/create-project',
    name: 'Project',
    component: CreateOrEditProject.screen
  },
  {
    path: '/admin/create-contact',
    name: 'Contact',
    component: CreateOrEditContact.screen
  },
  {
    path: '/admin/create-purchase',
    name: 'Purchase',
    component: CreateOrEditPurchase.screen
  },
  {
    path: '/admin/create-bank-account',
    name: 'BankAccount',
    component: CreateOrEditBankAccount.screen
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
    name: 'ExpenseReport', 
    component: ExpenseReport.screen
  },
  {
    path: '/admin/report/invoice-report',
    name: 'InvoiceReport', 
    component: InvoiceReport.screen
  },
  {
    path: '/admin/report/transaction-report',
    name: 'TransactionReport', 
    component: TransactionReport.screen
  },
  {
    path: '/admin/expense/expense',
    name: 'Expense', 
    component: Expense.screen
  },
  {
    path: '/admin/expense/purchase',
    name: 'Purchase',
    component: Purchase.screen
  },
  {
    path: '/admin/settings/general',
    name: 'General Settings',
    component: GeneralSettings.screen
  },
  {
    path: '/admin/settings/vat-category',
    name: 'Vat Category',
    component: VatCategory.screen
  },
  {
    path: '/admin/master/contact',
    name: 'Contact',
    component: Contact.screen
  },
  {
    path: '/admin/master/project',
    name: 'Project',
    component: Project.screen
  },
  {
    path: '/admin/master/product',
    name: 'Product',
    component: Product.screen
  },
  {
    path: '/admin/master/users',
    name: 'Users',
    component: Users.screen
  },
  {
    path: '/admin/create-vat-category',
    name: 'Create Vat Category',
    component: CreateOrEditVatCategory.screen
  },
  {
    path: '/admin/create-transaction-category',
    name: 'Create Transaction Category',
    component: CreateOrEditTranactionCategory.screen
  },
  {
    path: '/admin/settings/transaction-category',
    name: 'Transaction Category',
    component: TransactionCategory.screen
  },
  {
    redirect: true,
    path: '/admin',
    pathTo: '/admin/home',
    name: 'Admin'
  }
]

export default adminRoutes