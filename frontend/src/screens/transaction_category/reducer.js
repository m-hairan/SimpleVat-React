import { TRANSACTION } from 'constants/types'

const initState = {
}

const TransactionReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    default:
      return state
  }
}

export default TransactionReducer