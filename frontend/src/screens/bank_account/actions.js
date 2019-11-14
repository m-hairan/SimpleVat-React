import { BANK_ACCOUNT } from 'constants/types'
import {
  api,
  authApi
} from 'utils'

export const getBankAccountList = () => {
  return (dispatch) => {
    dispatch({
      type: BANK_ACCOUNT.BANK_ACCOUNT_LIST,
      payload: {
        data: [{
          bank_name: 'NBC',
          country: 'US',
          account_name: 'TTC',
          account_number: 'DGLE23042322340',
          IBAN_number: 'TYE234254343',
          currency: 234204924,
          account_type: 'Bank',
          swift_code: 'UYR239239',
          opening_balance: 234293
        }, {
          bank_name: 'NBC',
          country: 'US',
          account_name: 'TTC',
          account_number: 'DGLE2304230291',
          IBAN_number: 'TYE234254343',
          currency: 234204924,
          account_type: 'Bank',
          swift_code: 'UYR239239',
          opening_balance: 234293
        }, {
          bank_name: 'NBC',
          country: 'US',
          account_name: 'TTC',
          account_number: 'DGLE230429042',
          IBAN_number: 'TYE234254343',
          currency: 234204924,
          account_type: 'Bank',
          swift_code: 'UYR239239',
          opening_balance: 234293
        }, {
          bank_name: 'NBC',
          country: 'US',
          account_name: 'TTC',
          account_number: 'DGLE2304232183',
          IBAN_number: 'TYE234254343',
          currency: 234204924,
          account_type: 'Bank',
          swift_code: 'UYR239239',
          opening_balance: 234293
        },  {
          bank_name: 'NBC',
          country: 'US',
          account_name: 'TTC',
          account_number: 'DGLE23042302934',
          IBAN_number: 'TYE234254343',
          currency: 234204924,
          account_type: 'Bank',
          swift_code: 'UYR239239',
          opening_balance: 234293
        }, {
          bank_name: 'NBC',
          country: 'US',
          account_name: 'TTC',
          account_number: 'DGLE230429045',
          IBAN_number: 'TYE234254343',
          currency: 234204924,
          account_type: 'Bank',
          swift_code: 'UYR239239',
          opening_balance: 234293
        }, {
          bank_name: 'NBC',
          country: 'US',
          account_name: 'TTC',
          account_number: 'DGLE2304232186',
          IBAN_number: 'TYE234254343',
          currency: 234204924,
          account_type: 'Bank',
          swift_code: 'UYR239239',
          opening_balance: 234293
        },  {
          bank_name: 'NBC',
          country: 'US',
          account_name: 'TTC',
          account_number: 'DGLE23042302937',
          IBAN_number: 'TYE234254343',
          currency: 234204924,
          account_type: 'Bank',
          swift_code: 'UYR239239',
          opening_balance: 234293
        }, {
          bank_name: 'NBC',
          country: 'US',
          account_name: 'TTC',
          account_number: 'DGLE230429048',
          IBAN_number: 'TYE234254343',
          currency: 234204924,
          account_type: 'Bank',
          swift_code: 'UYR239239',
          opening_balance: 234293
        }, {
          bank_name: 'NBC',
          country: 'US',
          account_name: 'TTC',
          account_number: 'DGLE2304232189',
          IBAN_number: 'TYE234254343',
          currency: 234204924,
          account_type: 'Bank',
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



