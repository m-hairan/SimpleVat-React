import { TAXES } from 'constants/types'

const initState = {
}

const TaxesReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    default:
      return state
  }
}

export default TaxesReducer