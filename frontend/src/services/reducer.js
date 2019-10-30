import { combineReducers } from 'redux'

import {
  AuthReducer,
  CommonReducer
} from './global'

import {
  Home,
  BankAccount,
  Employee,
  Contact,
  Expense,
  GeneralSettings,
  Imports,
  Invoice,
  Product,
  Project,
  Purchase,
  Taxes,
  TransactionCategory,
  User,
  VatCategory
} from 'screens'


const reducer = combineReducers({
  common: CommonReducer,
  auth: AuthReducer,

  home: Home.reducer,
  bank: BankAccount.reducer,
  employee: Employee.reducer,
  contact: Contact.reducer,
  expense: Expense.reducer,
  settings: GeneralSettings.reducer,
  imports: Imports.reducer,
  invoice: Invoice.reducer,
  product: Product.reducer,
  project: Project.reducer,
  purchase: Purchase.reducer,
  taxes: Taxes.reducer,
  transaction: TransactionCategory.reducer,
  user: User.reducer,
  vat: VatCategory.reducer
})

export default reducer