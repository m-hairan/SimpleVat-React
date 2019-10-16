import { EXPENSE } from 'constants/types'

const initState = {
}

const ExpenseReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    default:
      return state
  }
}

export default ExpenseReducer