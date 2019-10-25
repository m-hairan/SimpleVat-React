import { TRANSACTION } from 'constants/types'
import {
  api,
  authApi
} from 'utils'

// Get Transaction List
export const getTransactionList = () => {
  return (dispatch) => {
    let data = {
      method: 'GET',
      url: '/rest/transaction/gettransactioncategory'
    }

    return authApi(data).then(res => {
      dispatch({
        type: TRANSACTION.TRANSACTION_LIST,
        payload: res.data
      })
      return res
    }).catch(err => {
      throw err
    })
  }
}

// Delete Transaction Row
export const deleteTransaction = (id) => {
  return (dispatch) => {
    let data = {
      method: 'DELETE',
      url: `/rest/transaction/deletetransactioncategory?id=${id}`
    }

    return authApi(data).then(res => {
      return res
    }).catch(err => {
      throw err
    })
  }
}
