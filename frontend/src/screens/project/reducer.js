import { PROJECT } from 'constants/types'

const initState = {
  project_list: [],
  project_currency_list: [],
  project_country_list: [],
  project_title_list: []
}

const ProjectReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {
    
    case PROJECT.PROJECT_LIST:
      return {
        ...state,
        project_list: Object.assign([], payload.data)
      }

    case PROJECT.PROJECT_CURRENCY_LIST:
      return {
        ...state,
        project_currency_list: Object.assign([], payload.data)
      }
    
    case PROJECT.PROJECT_COUNTRY_LIST:
      return {
        ...state,
        project_country_list: Object.assign([], payload.data)
      }

    case PROJECT.PROJECT_TITLE_LIST:
      return {
        ...state,
        project_title_list: Object.assign([], payload.data)
      }

    default:
      return state
  }
}

export default ProjectReducer