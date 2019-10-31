import React from 'react'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'

import {
  BankAccountList,
  BankStatementList
} from './sections'

import * as BankAccountActions from './actions'

import './style.scss'

const mapStateToProps = (state) => {
  return ({
    is_authed: state.auth.is_authed,
    bank_account_list: state.bank.bank_account_list,
    bank_statement_list: state.bank.bank_statement_list
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


  render() {

    return (
      <div className="bank-account-screen">
        <div className="animated fadeIn">
          <BankAccountList {...this.props} />
          {/* <BankStatementList {...this.props} /> */}
        </div>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(BankAccount)
