import { USER } from 'constants/types'

const initState = {
}

const UserReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    default:
      return state
  }
}

export default UserReducer