import { BANK } from 'constants/types'

const initState = {
}

const BankReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    default:
      return state
  }
}

export default BankReducer