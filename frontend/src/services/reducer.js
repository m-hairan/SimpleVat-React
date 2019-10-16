import { combineReducers } from 'redux'

import {
  UserReducer,
  CommonReducer
} from './global'

const reducer = combineReducers({
  common: CommonReducer,
  user: UserReducer
})

export default reducer