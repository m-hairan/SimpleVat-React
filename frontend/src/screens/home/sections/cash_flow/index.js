import React, {Component} from 'react'
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips'
import { Bar } from 'react-chartjs-2'
import { Card, CardBody } from 'reactstrap'

import './style.scss'

const cashBarOption = {
  tooltips: {
    enabled: false,
    custom: CustomTooltips
  },
  legend: {
    display: false,
    position: 'bottom'
  },
  scales: {
    yAxes: [{
      ticks: {
        // Include a dollar sign in the ticks
        callback: function(value, index, values) {
          return '$' + value
        },
        beginAtZero: true,
      }
    }],
    
  }, 
  maintainAspectRatio: false
}

const incomeIcon = require('assets/images/dashboard/income.png')
const outcomeIcon = require('assets/images/dashboard/outcome.png')
const totalIcon = require('assets/images/dashboard/total.png')


class CashFlow extends Component {

  constructor(props) {
    super(props)
    this.state = {
      activeTab: new Array(4).fill('1')
    }

    this.toggle = this.toggle.bind(this)
  }

  toggle(tabPane, tab) {
    const newArray = this.state.activeTab.slice()
    newArray[tabPane] = tab
    this.setState({
      activeTab: newArray
    })
  }

  render() {
    const cashFlowBar = {
      labels: ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC'],
      datasets: [
        {
        label: 'Inflow',
        backgroundColor: 'rgba(75,192,192,0.7)',
        hoverBackgroundColor: 'rgba(75,192,192,1)',
        data: [3000, 5000, 7000, 2000, 5000, 5000, 2000, 1000, 5000, 2000, 6000, 3000, 1000, 2000],
        },
        {
        label: 'Outflow',
        backgroundColor: 'rgba(255,99,132,0.7)',
        hoverBackgroundColor: 'rgba(255,99,132,1)',
        data: [2500, 6000, 4000, 4000, 1000, 2500, 5300, 1100, 4530, 2000, 4000, 4000, 4000, 3000 ],
        },
      ]
    }

    return (
      <div className="animated fadeIn">
        <Card className="cash-card">
          <CardBody>
          <div className="flex-wrapper">
            <h1>Cashflow</h1>
            <div className="card-header-actions">
            <select className="form-control">
              <option value="12">Last 12 Months</option>
              <option value="6">Last 6 Months</option>
              <option value="3">Last 3 Months</option>
            </select>
            </div>
          </div>
          
          <div className="data-info">
            <div className="data-item">
            <img alt="income" src={incomeIcon}/>
            <div>
              <h3>$ 180, 40</h3>
              <p>INFLOW</p>
            </div>
            </div>
            <div className="data-item">
            <img alt="outgoing" src={outcomeIcon}/>
            <div>
              <h3>$ 180, 40</h3>
              <p>OUTFLOW</p>
            </div>
            </div>
            <div className="data-item total">
            <img alt="total" src={totalIcon}/>
            <div>
              <h3>$ 180, 40</h3>
              <p>NET</p>
            </div>
            </div>
          </div>
          <div className="chart-wrapper">
            <Bar data={cashFlowBar} options={cashBarOption} style={{height: 300}}/>
          </div>
          </CardBody>
        </Card> 
      </div>
    )
  }
}

export default CashFlow