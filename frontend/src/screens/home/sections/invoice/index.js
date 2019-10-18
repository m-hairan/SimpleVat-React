import React, {Component} from 'react';
import { HorizontalBar } from 'react-chartjs-2';
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import {Nav, NavItem, NavLink, TabContent, TabPane, Card, CardBody} from 'reactstrap';
import { DateRangePicker2 } from 'components'
import moment from "moment"

import './style.scss'

const invoiceOption = {
  tooltips: {
    enabled: false,
    custom: CustomTooltips
  },
  legend: {
    display: true,
    position: 'right',
    labels: {
      boxWidth: 15,
    }
  },
  scales: {
    xAxes: [{
    stacked: true
    }],
    yAxes: [{
      stacked: true,
    }]
  }, 
  maintainAspectRatio: false
}

const ranges =  {
  // 'Today': [moment(), moment()],
  // 'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
  'This Week': [moment().startOf('week'), moment().endOf('week')],
  'This Month': [moment().startOf('month'), moment().endOf('month')],
  'Last 7 Days': [moment().subtract(6, 'days'), moment()],
  'Last 30 Days': [moment().subtract(29, 'days'), moment()],
  'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
}

class Invoice extends Component {

  constructor(props) {
    super(props);

    this.toggle = this.toggle.bind(this);
    this.state = {
      activeTab: new Array(4).fill('1'),
    };
  }

  toggle(tabPane, tab) {
    const newArray = this.state.activeTab.slice()
    newArray[tabPane] = tab
    this.setState({
      activeTab: newArray,
    });
  }

  render() {
    const invoiceBar = {
      labels: ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL'],
      datasets: [
        {
        label: 'Paid(25)',
        backgroundColor: '#36A2EB89',
        data: [2500, 6000, 4000, 2000, 1000, 2400 ],
        },
        {
        label: 'Due(30)',
        backgroundColor: '#FF638489',
        data: [3000, 5000, 7000, 1000],
        },
        {
        label: 'Overdue(100)',
        backgroundColor: '#FFCE5689',
        data: [2500, 6000, 4000, 500 ],
        },
      ],
      };

    return (
      <div className="animated fadeIn">
        <Card className='invoice-card'>
          <CardBody className="tab-card">
            <div className="flex-wrapper">
              <Nav tabs>
                <NavItem>
                  <NavLink
                  active={this.state.activeTab[0] === '1'}
                  onClick={() => { this.toggle(0, '1'); }}
                  >
                  Invoices Timeline
                  </NavLink>
                </NavItem>
                <NavItem>
                  <NavLink
                  active={this.state.activeTab[0] === '2'}
                  onClick={() => { this.toggle(0, '2'); }}
                  >
                  Invoices
                  </NavLink>
                </NavItem>
                <NavItem>
                  <NavLink
                  active={this.state.activeTab[0] === '3'}
                  onClick={() => { this.toggle(0, '3'); }}
                  >
                  Projects
                  </NavLink>
                </NavItem>
              </Nav>
              <div className="card-header-actions">
                <DateRangePicker2 ranges={ranges}/>
              </div>
            </div>
            <TabContent activeTab={this.state.activeTab[0]}>
              <TabPane tabId="1">
                <div className="flex-wrapper" style={{paddingLeft: 20}}>
                  <div className="data-info">
                  <button className="btn-instagram btn-brand mr-1 mb-1 btn btn-secondary btn-sm">
                    <i className="nav-icon icon-speech"></i><span>New Invoice</span>
                  </button>
                  </div>
                  <div className="data-info">
                  <div className="data-item">
                    <div>
                      <h3>$12,640</h3>
                      <p>OUTSTANDING</p>
                    </div>
                  </div>
                  </div>
                </div>
                <div className="chart-wrapper invoices">
                  <HorizontalBar data={invoiceBar} options={invoiceOption}/>
                </div>
              </TabPane>
              <TabPane tabId="2">
                <div className="flex-wrapper" style={{paddingLeft: 20}}>
                  <div className="data-info">
                  <button className="btn-instagram btn-brand mr-1 mb-1 btn btn-secondary btn-sm">
                    <i className="nav-icon icon-speech"></i><span>New Invoice</span>
                  </button>
                  </div>
                  <div className="data-info">
                  <div className="data-item">
                    <div>
                      <h3>$12,640</h3>
                      <p>OUTSTANDING</p>
                    </div>
                  </div>
                  </div>
                </div>
                <div className="chart-wrapper invoices">
                  <HorizontalBar data={invoiceBar} options={invoiceOption} type='horizontalBar'/>
                </div>
              </TabPane>
              <TabPane tabId="3">
                <div className="flex-wrapper" style={{paddingLeft: 20}}>
                  <div className="data-info">
                  <button className="btn-instagram btn-brand mr-1 mb-1 btn btn-secondary btn-sm">
                    <i className="nav-icon icon-speech"></i><span>New Invoice</span>
                  </button>
                  </div>
                  <div className="data-info">
                  <div className="data-item">
                    <div>
                      <h3>$12,640</h3>
                      <p>OUTSTANDING</p>
                    </div>
                  </div>
                  </div>
                </div>
                <div className="chart-wrapper invoices">
                  <HorizontalBar data={invoiceBar} options={invoiceOption} type='horizontalBar'/>
                </div>
              </TabPane>
            </TabContent>
          </CardBody>
        </Card>
      </div>
    );
  }
}

export default Invoice;