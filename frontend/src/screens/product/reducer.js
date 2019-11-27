import { PRODUCT } from 'constants/types'

const initState = {
  product_list: []
}

const ProductReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    case PRODUCT.PRODUCT_LIST:
      return {
        ...state,
        product_list: Object.assign([], payload.data)
      }

    default:
      return state
  }
}

export default ProductReducer