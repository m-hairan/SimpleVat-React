import { PROJECT } from 'constants/types'

const initState = {
  project_list: []
}

const ProjectReducer = (state = initState, action) => {
  const { type, payload} = action
  
  switch(type) {
    
    case PROJECT.PROJECT_LIST:
      return {
        ...state,
        project_list: Object.assign([], payload.data)
      }

    default:
      return state
  }
}

export default ProjectReducer