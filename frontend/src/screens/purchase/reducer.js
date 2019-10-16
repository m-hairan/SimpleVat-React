import { PURCHASE } from 'constants/types'

const initState = {
}

const PurchaseReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    default:
      return state
  }
}

export default PurchaseReducer