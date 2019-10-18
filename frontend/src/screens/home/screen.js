import React from 'react'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'
import { CardColumns } from 'reactstrap'

import {
  Invoice,
  BankAccount,
  CashFlow,
  RevenueAndExpense,
  ProfitAndLoss
} from './sections'

import * as DashboardActions from './actions'

import './style.scss'


const mapStateToProps = (state) => {
  return ({
    data: state.home.data
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    dashboardActions: bindActionCreators(DashboardActions, dispatch)
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
          <CashFlow/>
          <CardColumns className="cols-2">
            <BankAccount {...this.props} />
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
