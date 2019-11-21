import { COMMON } from 'constants/types'
import {
  api,
  authApi
} from 'utils'

export const startRequest = () => {
  return (dispatch) => {
    dispatch({
      type: COMMON.START_LOADING
    })
  }
}

export const endRequest = () => {
  return (dispatch) => {
    dispatch({
      type: COMMON.END_LOADING
    })
  }
}


export const getSimpleVATVersion = () => {
  return (dispatch) => {
    let data = {
      method: 'get',
      url: '/config/getreleasenumber'
    }
    return api(data).then(res => {
      dispatch({
        type: COMMON.VAT_VERSION,
        payload: {
          data: res.data.simpleVatRelease
        }
      })
    }).catch(err => {
      throw err
    })
  }
}

