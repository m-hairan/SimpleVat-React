import { combineReducers } from 'redux'

import {
  UserReducer,
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
  Users,
  VatCategory
} from 'screens'


const reducer = combineReducers({
  common: CommonReducer,
  user: UserReducer,

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
  users: Users.reducer,
  vat: VatCategory.reducer
})

export default reducer