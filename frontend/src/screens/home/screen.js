import React, {Component} from 'react'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'
import {CardColumns} from 'reactstrap'

import {
  Invoice,
  BankAccount,
  CashFlow,
  RevenueAndExpense,
  ProfitAndLoss
} from './sections'

import * as HomeActions from './actions'

import './style.scss'


const mapStateToProps = (state) => {
  return ({
    // Bank Account
    bank_account_type: state.home.bank_account_type,
    bank_account_graph: state.home.bank_account_graph,

    // Cash Flow
    cash_flow_graph: state.home.cash_flow_graph,

    // Invoice 
    invoice_graph: state.home.invoice_graph,

    // Profit and Loss
    profit_loss: state.home.proft_loss,

    // Revenues and Expenses
    revenue_graph: state.home.revenue_graph,
    expense_graph: state.home.expense_graph
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    HomeActions: bindActionCreators(HomeActions, dispatch)
  })
}

class Home extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
    }
  }

  render() {
    return (
      <div className="home-screen">
        <div className="animated fadeIn">
          <CashFlow {...this.props}/>
          <CardColumns className="cols-2">
            <BankAccount {...this.props}/>
            <RevenueAndExpense {...this.props}/>
            <Invoice {...this.props}/>
            <ProfitAndLoss {...this.props}/>
          </CardColumns>
        </div>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Home)
