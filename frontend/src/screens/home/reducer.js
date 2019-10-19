import { HOME } from 'constants/types'

const initState = {
  bank_account_type: [],
  bank_account_graph: {},
  cash_flow_graph: {},
  invoice_graph: {},
  proft_loss: {},
  revenue_graph: {},
  expense_graph: {}
}

const HomeReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    // Bank Account
    case HOME.BANK_ACCOUNT_TYPE:
      return {
        ...state,
        bank_account_type: Object.assign([], payload)
      }

    case HOME.BANK_ACCOUNT_GRAPH:
      return {
        ...state,
        bank_account_graph: Object.assign({}, payload)
      }
      

    // Cash Flow
    case HOME.CASH_FLOW_GRAPH:
      return {
        ...state,
        cash_flow_graph: Object.assign({}, payload)
      }


    // Invoice 
    case HOME.INVOICE_GRAPH:
      return {
        ...state,
        invoice_graph: Object.assign({}, payload)
      }


    // Profit and Loss 
    case HOME.PROFIT_LOSS:
      return {
        ...state,
        proft_loss: Object.assign({}, payload)
      }

    
    // Revenues and Expenses 
    case HOME.REVENUE_GRAPH:
      return {
        ...state,
        revenue_graph: Object.assign({}, payload)
      }

    case HOME.EXPENSE_GRAPH:
      return {
        ...state,
        expense_graph: Object.assign({}, payload)
      }
    
    default:
      return state
  }
}

export default HomeReducer