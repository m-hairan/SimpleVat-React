import { BANK_STATEMENT } from 'constants/types'

const initState = {
  bank_statement_list: []
}

const BankStatementReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    case BANK_STATEMENT.BANK_STATEMENT_LIST:
      return {
        ...state,
        bank_statement_list: Object.assign([], payload.data)
      }

    default:
      return state
  }
}

export default BankStatementReducer