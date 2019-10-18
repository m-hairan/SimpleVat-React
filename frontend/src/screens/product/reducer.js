import { PRODUCT } from 'constants/types'

const initState = {
}

const ProductReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    default:
      return state
  }
}

export default ProductReducer