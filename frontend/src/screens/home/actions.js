import { HOME } from 'constants/types'
import {
  api,
  authApi
} from 'utils'

export const initialData = (obj) => {
  return (dispatch) => {
    
  }
}

export const getBankAccountTypes = () => {
  return (dispatch) => {
    let data = {
      method: 'GET',
      url: '/rest/bank/getaccounttype'
    }
    return authApi(data).then(res => {
      console.log(res)
      dispatch({
        type: HOME.BANK_ACCOUNT_TYPE,
        payload: res
      })
    }).catch(err => {
      throw err
    })
  }
}
