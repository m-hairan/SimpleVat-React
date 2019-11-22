import { BANK_ACCOUNT } from 'constants/types'
import {
  api,
  authApi
} from 'utils'

export const getAccountTypeList = () => {
  return (dispatch) => {
    let data ={
      method: 'get',
      url: 'rest/bank/getaccounttype'
    }
    return authApi(data).then(res => {
      console.log(res)
    }).catch(err => {
      throw err
    })
  }
}

export const getCurrencyList = () => {
  return (dispatch) => {
    let data = {
      method: 'get',
      url: 'rest/bank/getcurrenncy'
    }
    return authApi(data).then(res => {
      console.log(res)
    }).catch(err => {
      throw err
    })
  }
}
