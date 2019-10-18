import { HOME } from 'constants/types'

const initState = {
}

const HomeReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {
    case HOME.BANK_ACCOUNT_TYPE:
      return {
        ...state,
        bank_account_type: payload
      }
    
    default:
      return state
  }
}

export default HomeReducer