import { PURCHASE } from 'constants/types'

const initState = {
  purchase_list: []
}

const PurchaseReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    case PURCHASE.PURCHASE_LIST:
      return {
        ...state,
        purchase_list: Object.assign([], payload.data)
      }

    default:
      return state
  }
}

export default PurchaseReducer