import React, {Component} from 'react'
import { Line } from 'react-chartjs-2'
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips'
import {
  Nav,
  NavItem,
  NavLink,
  TabContent,
  TabPane,
  Card,
  CardBody
} from 'reactstrap'
import { DateRangePicker2 } from 'components'
import moment from 'moment'

import './style.scss'

const backOption = {
  tooltips: {
    enabled: false,
    custom: CustomTooltips
  },
  legend: {
    display: false,
    position: 'bottom'
  },
  elements: {
    line: {
      tension: 0 // disables bezier curves,
    }
  }
}

const ranges =  {
  'Today': [moment(), moment()],
  'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
  'Last 7 Days': [moment().subtract(6, 'days'), moment()],
  'Last 30 Days': [moment().subtract(29, 'days'), moment()],
  'This Week': [moment().startOf('week'), moment().endOf('week')],
  'This Month': [moment().startOf('month'), moment().endOf('month')],
  'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
}

const bankIcon = require('assets/images/dashboard/bank.png')


class BankAccount extends Component {

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
      activeTab: newArray,
    })
  }

  componentDidMount(){
    this.props.dashboardActions.getBankAccountTypes()
  }

  render() {
    const line = {
      labels: ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC'],
      datasets: [
        {
          label: 'Current Business Account',
          fill: true,
          lineTension: 0.1,
          backgroundColor: 'rgba(75,192,192,0.4)',
          borderColor: 'rgba(75,192,192,1)',
          borderCapStyle: 'butt',
          borderDash: [],
          borderDashOffset: 0.0,
          borderJoinStyle: 'miter',
          pointBorderColor: 'rgba(75,192,192,1)',
          pointBackgroundColor: '#fff',
          pointBorderWidth: 2,
          pointHoverRadius: 5,
          pointHoverBackgroundColor: 'rgba(75,192,192,1)',
          pointHoverBorderColor: 'rgba(220,220,220,1)',
          pointHoverBorderWidth: 2,
          pointRadius: 4,
          pointHitRadius: 20,
          data: [65, 23, 100, 2, 23,12, 40, 50, 60, 80, 90],
        }
      ]
    }

    return (
      <div className="animated fadeIn">
        <Card className="bank-card">
          <CardBody className="tab-card">
            <div className="flex-wrapper">
              <Nav tabs>
                <NavItem>
                  <NavLink
                    active={this.state.activeTab[0] === '1'}
                    onClick={() => { this.toggle(0, '1') }}
                  >
                    Banking
                  </NavLink>
                </NavItem>
              </Nav>
              <div className="card-header-actions">
                <DateRangePicker2 ranges={ranges}/>
              </div>
            </div>
            <TabContent activeTab={this.state.activeTab[0]}>
              <TabPane tabId="1">
                <div className="flex-wrapper">
                  <div className="data-info">
                  <div className="data-item">
                    <img
                      alt="bankIcon"
                      className="d-none d-lg-block"
                      src={bankIcon}
                      style={{width: 40, marginRight: 10}}
                    />
                    <div>
                      <select className="form-control bank-type-select">
                        <option value="bank">Current Business Bank Account  </option>
                        <option value='credit'>Current Credit Card</option>
                      </select>
                      <p style={{fontWeight: 500, textIndent: 5}}>Last updated on 01/20/2019</p>
                    </div>
                  </div>
                  </div>
                  
                  <div className="data-info">
                    <div className="data-item">
                      <div>
                        <h3>$12,640</h3>
                        <p>BALANCE</p>
                      </div>
                    </div>
                    <div className="data-item">
                      <div>
                        <h3>$180,40</h3>
                        <p>ALL ACCOUNTS</p>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="chart-wrapper">
                  <Line data={line} options={backOption}/>
                </div>
              </TabPane>
            </TabContent>
          </CardBody>
        </Card>
      </div>
    )
  }
}

export default BankAccount