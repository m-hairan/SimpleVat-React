import { PROJECT } from 'constants/types'
import {
  api,
  authApi
} from 'utils'

export const getProjectList = (obj) => {
  return (dispatch) => {
    dispatch({
      type: PROJECT.PROJECT_LIST,
      payload: {
        data: [{
          transactionCategoryId: 2,
          transactionCategoryCode: 2,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        }, {
          transactionCategoryId: 1,
          transactionCategoryCode: 4,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        }, {
          transactionCategoryId: 1,
          transactionCategoryCode: 4,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        }, {
          transactionCategoryId: 1,
          transactionCategoryCode: 4,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        }, {
          transactionCategoryId: 1,
          transactionCategoryCode: 4,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        },{
          transactionCategoryId: 1,
          transactionCategoryCode: 4,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        },{
          transactionCategoryId: 1,
          transactionCategoryCode: 4,
          transactionCategoryName: 'temp',
          transactionCategoryDescription: 'temp',
          parentTransactionCategory: 'Loream Ipsume',
          transactionType: 'TEMP'
        }]
      }
    })
  }
}


// Create & Save Project
export const createAndSaveProject = (project) => {
  return (dispatch) => {
    let data = {
      method: 'POST',
      url: `/rest/project/saveproject?id=1`,
      data: project
    }

    return authApi(data).then(res => {
      return res
    }).catch(err => {
      throw err
    })
  }
}


// Get Project Currency
export const getProjectCurrencyList = () => {
  return (dispatch) => {
    let data = {
      method: 'GET',
      url: '/rest/project/getcurrenncy?currencyStr=1'
    }

    return authApi(data).then(res => {
      console.log("=================== project currency ==============", res)
      dispatch({
        type: PROJECT.PROJECT_CURRENCY_LIST,
        payload: res.data
      })
      return res
    }).catch(err => {
      throw err
    })
  }
}


// Get Project Country
export const getProjectCountryList = () => {
  return (dispatch) => {
    let data = {
      method: 'GET',
      url: '/rest/project/getcountry?countryStr=1'
    }

    return authApi(data).then(res => {
      console.log("=================== project Coutnry ==============", res)
      dispatch({
        type: PROJECT.PROJECT_COUNTRY_LIST,
        payload: res.data
      })
      return res
    }).catch(err => {
      throw err
    })
  }
}
