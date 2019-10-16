import { USER } from 'constants/types'

const initState = {
  is_authed: false
}

const UserReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type){

    case USER.SIGNED_IN:
      return {
        ...state,
        is_authed: true
      }

    case USER.SIGNED_OUT:
      return {
        ...state,
        is_authed: false
      }

    default:
        return state
  }
}

export default UserReducer