import { VAT } from 'constants/types'
import {
  api,
  authApi
} from 'utils'


// Get Vat List
export const getVatList = () => {
  return (dispatch) => {
    let data = {
      method: 'GET',
      url: '/rest/vat/getvat'
    }

    return authApi(data).then(res => {
      dispatch({
        type: VAT.VAT_LIST,
        payload: res.data
      })
      return res
    }).catch(err => {
      throw err
    })
  }
}

// Delete Vat Row
export const deleteVat = (id) => {
  return (dispatch) => {
    let data = {
      method: 'DELETE',
      url: `rest/vat/deletevat?id=${id}`
    }

    return authApi(data).then(res => {
      return res
    }).catch(err => {
      throw err
    })
  }
}
