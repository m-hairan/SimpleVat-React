import { CHART_ACCOUNTS } from 'constants/types'

const initState = {
}

const ChartAccountsReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    default:
      return state
  }
}

export default ChartAccountsReducer