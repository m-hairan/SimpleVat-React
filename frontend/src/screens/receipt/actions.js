import { RECEIPT } from 'constants/types'
import {
  api,
  authApi
} from 'utils'

export const getReceiptList = () => {
  return (dispatch) => {
    dispatch({
      type: RECEIPT.RECEIPT_LIST,
      payload: {
        data: [{
          transactionCategoryId: 2,
          transactionCategoryCode: 2,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        }, {
          transactionCategoryId: 1,
          transactionCategoryCode: 4,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        }, {
          transactionCategoryId: 1,
          transactionCategoryCode: 4,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        }, {
          transactionCategoryId: 1,
          transactionCategoryCode: 4,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        }, {
          transactionCategoryId: 1,
          transactionCategoryCode: 4,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        },{
          transactionCategoryId: 1,
          transactionCategoryCode: 4,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        },{
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
