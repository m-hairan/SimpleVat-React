import { BANK } from 'constants/types'

const initState = {
  bank_account_list: []
}

const BankReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    case BANK.BANK_ACCOUNT_LIST:
      return {
        ...state,
        bank_account_list: Object.assign([], payload.data)
      }

    default:
      return state
  }
}

export default BankReducer