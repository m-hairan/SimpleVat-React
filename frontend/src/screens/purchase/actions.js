import { PURCHASE } from 'constants/types'
import {
  api,
  authApi
} from 'utils'

export const getPurchaseList = () => {
  return (dispatch) => {
    dispatch({
      type: PURCHASE.PURCHASE_LIST,
      payload: {
        data: [{
          status: 'paid',
          transactionCategoryId: 2,
          transactionCategoryCode: 2,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        }, {
          status: 'paid',
          transactionCategoryId: 1,
          transactionCategoryCode: 4,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        }, {
          status: 'paid',
          transactionCategoryId: 1,
          transactionCategoryCode: 4,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        }, {
          status: 'unpaid',
          transactionCategoryId: 1,
          transactionCategoryCode: 4,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        }, {
          status: 'unpaid',
          transactionCategoryId: 1,
          transactionCategoryCode: 4,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        },{
          status: 'paid',
          transactionCategoryId: 1,
          transactionCategoryCode: 4,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        },{
          status: 'unpaid',
          transactionCategoryId: 1,
          transactionCategoryCode: 4,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        }]        
      }
    })
  }
}
