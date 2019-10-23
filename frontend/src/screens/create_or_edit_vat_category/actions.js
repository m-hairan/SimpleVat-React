import { VAT } from 'constants/types'
import {
  api,
  authApi
} from 'utils'


// Get Vat By ID
export const getVatByID = (id) => {
  return (dispatch) => {
    let data = {
      method: 'GET',
      url: `rest/vat/getbyid?id=${id}`
    }

    return authApi(data).then(res => {
      dispatch({
        type: VAT.VAT_ROW,
        payload: res.data
      })
      return res
    }).catch(err => {
      throw err
    })
  }
}


// Create New Bat
export const createBat = (bat) => {
  return (dispatch) => {
    let data = {
      method: 'POST',
      url: `rest/vat/savevat?id=1`,
      data: bat
    }

    return authApi(data).then(res => {
      dispatch({
        type: VAT.VAT_ROW,
        payload: res.data
      })
      return res
    }).catch(err => {
      throw err
    })
  }
}