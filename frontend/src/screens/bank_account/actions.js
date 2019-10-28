import { BANK } from 'constants/types'
import {
  api,
  authApi
} from 'utils'

export const getBankAccountList = () => {
  return (dispatch) => {
    dispatch({
      type: BANK.BANK_ACCOUNT_LIST,
      payload: {
        data: [{
          bank_name: 'NBC',
          country: 'US',
          account_name: 'TTC',
          account_number: 'DGLE23042322340',
          IBAN_number: 'TYE234254343',
          currency: 234204924,
          account_type: 'Credit Card',
          swift_code: 'UYR239239',
          opening_balance: 234293
        }, {
          bank_name: 'NBC',
          country: 'US',
          account_name: 'TTC',
          account_number: 'DGLE2304230293',
          IBAN_number: 'TYE234254343',
          currency: 234204924,
          account_type: 'Credit Card',
          swift_code: 'UYR239239',
          opening_balance: 234293
        }, {
          bank_name: 'NBC',
          country: 'US',
          account_name: 'TTC',
          account_number: 'DGLE23042904',
          IBAN_number: 'TYE234254343',
          currency: 234204924,
          account_type: 'Credit Card',
          swift_code: 'UYR239239',
          opening_balance: 234293
        }, {
          bank_name: 'NBC',
          country: 'US',
          account_name: 'TTC',
          account_number: 'DGLE230423218',
          IBAN_number: 'TYE234254343',
          currency: 234204924,
          account_type: 'Credit Card',
          swift_code: 'UYR239239',
          opening_balance: 234293
        }]
      }
    })
  }
}


export const deleteBankAccount = (_id) => {
  return (dispatch) => {
    let data = {
      method: 'delete',
      url: `rest/bank/deletebank?id=${_id}`
    }
    return authApi(data).then(res => {
      return res
    }).catch(err => {
      throw err
    })
  }
}


export const getBankStatementList = () => {
  return (dispatch) => {
    dispatch({
      type: BANK.BANK_STATEMENT_LIST,
      payload: {
        data: [{
          transaction_type: 'Debit',
          amount: 3453246,
          reference_number: 'KDF3920342',
          description: 'This is description',
          transaction_date: 'Oct 28th, 2019'
        }, {
          transaction_type: 'Debit',
          amount: 3453246,
          reference_number: 'KDF3929865',
          description: 'This is description',
          transaction_date: 'Oct 28th, 2019'
        }, {
          transaction_type: 'Debit',
          amount: 3453246,
          reference_number: 'KDF39206574',
          description: 'This is description',
          transaction_date: 'Oct 28th, 2019'
        }, {
          transaction_type: 'Debit',
          amount: 3453246,
          reference_number: 'KDF392394',
          description: 'This is description',
          transaction_date: 'Oct 28th, 2019'
        }, {
          transaction_type: 'Debit',
          amount: 3453246,
          reference_number: 'KDF3920923',
          description: 'This is description',
          transaction_date: 'Oct 28th, 2019'
        }]
      }
    })
  }
}
