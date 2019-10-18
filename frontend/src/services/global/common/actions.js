import { COMMON } from 'constants/types'

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

