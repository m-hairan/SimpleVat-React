import { INVOICE } from 'constants/types'

const initState = {
  invoice_list: []
}

const InvoiceReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    case INVOICE.INVOICE_LIST:
      return {
        ...state,
        invoice_list: Object.assign([], payload.data)
      }

    default:
      return state
  }
}

export default InvoiceReducer