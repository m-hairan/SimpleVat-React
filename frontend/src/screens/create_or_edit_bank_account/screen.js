import React from 'react'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'

import './style.scss'

const mapStateToProps = (state) => {
  return ({
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
  })
}

class CreateOrEditBankAccount extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      
    }

  }

  render() {

    return (
      <div className="create-or-edit-bank-account-screen">
        <h1>CreateOrEditBankAccount Screen</h1>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(CreateOrEditBankAccount)