import React from 'react'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'

import * as BankAccountActions from './actions'

import './style.scss'

const mapStateToProps = (state) => {
  return ({
    is_authed: state.user.is_authed,
    bank_account_list: state.bank.bank_account_list
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    bankAccountActions: bindActionCreators(BankAccountActions, dispatch)
  })
}

class BankAccount extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      
    }

  }

  componentDidMount () {
    if (this.props.is_authed === true) {
      this.initializeData()
    }
  }

  componentWillReceiveProps (newProps) {
    if (newProps.is_authed !== this.props.is_authed && newProps.is_authed === true) {
      this.initializeData()
    }
  }

  initializeData () {
    this.props.bankAccountActions.getBankAccountList()
  }


  render() {

    return (
      <div className="bank-account-screen">
        <h1>BankAccount Screen</h1>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(BankAccount)
