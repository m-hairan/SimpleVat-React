import { COMMON } from 'constants/types'

const initState = {
  is_loading: false,
  version: ''
}

const CommonReducer = (state = initState, action) => {
  const { type, payload } = action
  
  switch(type){

    case COMMON.START_LOADING:
      return {
        ...state,
        is_loading: true,
      }
      
    case COMMON.END_LOADING:
      return {
        ...state,
        is_loading: false,
      }

    case COMMON.VAT_VERSION:
      return {
        ...state,
        version: payload.data
      }
    
    default:
        return state
  }
}

export default CommonReducer