import { USERS } from 'constants/types'

const initState = {
}

const UsersReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    default:
      return state
  }
}

export default UsersReducer