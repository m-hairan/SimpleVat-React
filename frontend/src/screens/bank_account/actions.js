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
      dispatch({
        type: BANK.BANK_ACCOUNT_LIST,
        payload: {
          data: res.data
        }
      })
    }).catch(err => {
      throw err
    })
  }
}
