import { INVOICE } from 'constants/types'

const initState = {
}

const InvoiceReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    default:
      return state
  }
}

export default InvoiceReducer