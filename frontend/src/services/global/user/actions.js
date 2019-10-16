import { USER } from 'constants/types'
import {
  api,
  authApi
} from 'utils'

export const signIn = (obj) => {
  return (dispatch) => {
    let data = {
      method: 'post',
      url: '/auth/login',
      data: obj
    }
    return api(data).then(res => {
      dispatch({
        type: USER.SIGNED_IN
      })
      return res
    }).catch(err => {
      throw err
    })
  }
}

export const signOut = () => {
  return (dispatch) => {
    dispatch({ type: USER.SIGNED_OUT })
  }
}
