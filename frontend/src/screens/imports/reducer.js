import { IMPORTS } from 'constants/types'

const initState = {
  import_list: []
}

const ImportsReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {

    case IMPORTS.IMPORT_LIST:
      return {
        ...state,
        import_list: Object.assign([], payload.data)
      }

    default:
      return state
  }
}

export default ImportsReducer