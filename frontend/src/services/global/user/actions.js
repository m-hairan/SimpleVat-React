import { USER } from 'constants/types'
import {
  api,
  authApi
} from 'utils'

export const checkAuthStatus = () => {
  return (dispatch) => {
    if (window.localStorage.getItem('accessToken')) {
      dispatch({
        type: USER.SIGNED_IN
      })
    } else {
      dispatch({
        type: USER.SIGNED_OUT
      })
    }
  }
}

export const logIn = (obj) => {
  return (dispatch) => {
    let data = {
      method: 'post',
      url: '/auth/token',
      data: obj
    }
    return api(data).then(res => {
      dispatch({
        type: USER.SIGNED_IN
      })
      window.localStorage.setItem('accessToken', res.data.token)
      return res
    }).catch(err => {
      throw err
    })
  }
}

export const logOut = () => {
  return (dispatch) => {
    window.localStorage.removeItem('accessToken')
    dispatch({
      type: USER.SIGNED_OUT
    })
  }
}
