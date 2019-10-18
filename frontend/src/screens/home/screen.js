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

import './style.scss'


const mapStateToProps = (state) => {
  return ({
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
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
            <BankAccount/>
            <RevenueAndExpense/>
            <Invoice/>
            <ProfitAndLoss/>
          </CardColumns>
        </div>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Home)
