import { BANK_ACCOUNT } from 'constants/types'

const initState = {
  bank_account_list: []
}

const BankAccountReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    case BANK_ACCOUNT.BANK_ACCOUNT_LIST:
      return {
        ...state,
        bank_account_list: Object.assign([], payload.data)
      }
    
    default:
      return state
  }
}

export default BankAccountReducer