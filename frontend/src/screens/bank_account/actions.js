import { BANK_ACCOUNT } from 'constants/types'
import {
  api,
  authApi
} from 'utils'

export const getBankAccountList = () => {
  return (dispatch) => {
    let data = {
      method: 'get',
      url: 'rest/bank/getbanklist'
    }
    return authApi(data).then(res => {
      if (res.status == 200) {
        dispatch({
          type: BANK_ACCOUNT.BANK_ACCOUNT_LIST,
          payload: {
            data: Object.assign([], res.data)
          } 
        })
      }
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



