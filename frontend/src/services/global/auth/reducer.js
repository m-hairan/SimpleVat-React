import { AUTH } from 'constants/types'

const initState = {
  is_authed: true
}

const AuthReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type){

    case AUTH.SIGNED_IN:
      return {
        ...state,
        is_authed: true
      }

    case AUTH.SIGNED_OUT:
      return {
        ...state,
        is_authed: false
      }

    default:
        return state
  }
}

export default AuthReducer