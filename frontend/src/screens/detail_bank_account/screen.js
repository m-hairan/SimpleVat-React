import React from 'react'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'

import {
  Row,
  Col
} from 'reactstrap'

import {
  BankStatementList,
  UpdateBankAccount
} from './sections'

import BankACcount from '../bank_account'


import './style.scss'

const mapStateToProps = (state) => {
  return ({
    bank_statement_list: state.bank.bank_statement_list
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    bankAccountActions: bindActionCreators(BankACcount.actions, dispatch)
  })
}

class DetailBankAccount extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      loading: false,

      account_type_list: [
        { id: 'Saving', name: 'Saving' },
        { id: 'Checking', name: 'Checking' },
        { id: 'Credit Card', name: 'Credit Card' },
        { id: 'Paypal', name: 'Paypal' },
        { id: 'Others', name: 'Others' },
      ]
    }

  }

  render() {

    return (
      <div className="detail-bank-account-screen">
        <div className="animated">
          <Row>
            <Col lg={3}>
              <UpdateBankAccount {...this.props} />
            </Col>
            <Col lg={9}>
              <BankStatementList {...this.props} />
            </Col>
          </Row>
        </div>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(DetailBankAccount)
