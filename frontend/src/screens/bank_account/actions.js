import { BANK } from 'constants/types'
import {
  api,
  authApi
} from 'utils'

export const getBankAccountList = () => {
  return (dispatch) => {
    let data = {
      method: 'get',
      url: '/rest/bank/getbanklist'
    }
    return authApi(data).then(res => {
      // dispatch({
      //   type: BANK.BANK_ACCOUNT_LIST,
      //   payload: {
      //     data: res.data
      //   }
      // })
      dispatch({
        type: BANK.BANK_ACCOUNT_LIST,
        payload: {
          data: [{
            bankAccountId: 4,
            bankAccountName: 'temp',
            accountNumber: 'temp',
            swiftCode: 'temp',
            bankAccountStatus: {
              bankAccountStatusName: 'temp'
            },
            openingBalance: 'temp'
          }, {
            bankAccountId: 4,
            bankAccountName: 'temp',
            accountNumber: 'temp',
            swiftCode: 'temp',
            bankAccountStatus: {
              bankAccountStatusName: 'temp'
            },
            openingBalance: 'temp'
          }]
        }
      })
    }).catch(err => {
      throw err
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
