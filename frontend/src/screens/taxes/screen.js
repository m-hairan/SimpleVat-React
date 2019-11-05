
import React from 'react'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'
import {
  Card,
  CardHeader,
  CardBody,
  Button,
  Row,
  Col,
  FormGroup,
  Label,
  Form,
  Table,
  TabContent,
  TabPane,
  Nav,
  NavItem,
  NavLink,
} from 'reactstrap'

import _ from "lodash"
import Select from 'react-select'
import { DateRangePicker2 } from 'components'
import moment from 'moment'
import { BootstrapTable, TableHeaderColumn, SearchField } from 'react-bootstrap-table'

import "react-bootstrap-table/dist/react-bootstrap-table-all.min.css"
import "react-toastify/dist/ReactToastify.css"
import 'react-select/dist/react-select.css'
import './style.scss'

const mapStateToProps = (state) => {
  return ({
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
  })
}

const colourOptions = [
  { value: 'ocean', label: 'Ocean', color: '#00B8D9', isFixed: true },
  { value: 'blue', label: 'Blue', color: '#0052CC', isDisabled: true },
  { value: 'purple', label: 'Purple', color: '#5243AA' },
  { value: 'red', label: 'Red', color: '#FF5630', isFixed: true },
  { value: 'orange', label: 'Orange', color: '#FF8B00' },
  { value: 'yellow', label: 'Yellow', color: '#FFC400' },
  { value: 'green', label: 'Green', color: '#36B37E' },
  { value: 'forest', label: 'Forestasd fsad fas fsad fsad fsa', color: '#00875A' },
  { value: 'slate', label: 'Slate', color: '#253858' },
  { value: 'silver', label: 'Silver', color: '#666666' },
]

const tempdata = [{
  transactionDate: '10/15/2019',
  transactionCategoryId: 2,
  transactionCategoryCode: 2,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
}, {
  transactionDate: '10/15/2019',
  transactionCategoryId: 1,
  transactionCategoryCode: 4,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
}, {
  transactionDate: '10/15/2019',
  transactionCategoryId: 1,
  transactionCategoryCode: 4,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
}, {
  transactionDate: '10/15/2019',
  transactionCategoryId: 1,
  transactionCategoryCode: 4,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
}, {
  transactionDate: '10/15/2019',
  transactionCategoryId: 1,
  transactionCategoryCode: 4,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
},{
  transactionDate: '10/15/2019',
  transactionCategoryId: 1,
  transactionCategoryCode: 4,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
},{
  transactionDate: '10/15/2019',
  transactionCategoryId: 1,
  transactionCategoryCode: 4,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
}]

const ranges =  {
  'Last 7 Days': [moment().subtract(6, 'days'), moment()],
  'Last 30 Days': [moment().subtract(29, 'days'), moment()],
  'This Week': [moment().startOf('week'), moment().endOf('week')],
  'This Month': [moment().startOf('month'), moment().endOf('month')],
  'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
}

class Taxes extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      activeTab: new Array(2).fill('1')
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
    return (
      <div className="tax-screen">
        <div className="animated fadeIn">
          <Card>
            <CardHeader>
              <Row>
                <Col lg={12}>
                  <div className="h4 mb-0 d-flex align-items-center">
                    <i className="nav-icon fas fa-chart-line" />
                    <span className="ml-2">Taxes</span>
                  </div>
                </Col>
              </Row>  
            </CardHeader>
            <CardBody>
              <Nav tabs>
                <NavItem>
                  <NavLink
                    active={this.state.activeTab[0] === '1'}
                    onClick={() => { this.toggle(0, '1') }}
                  >
                    Tax Transaction
                  </NavLink>
                </NavItem>
                <NavItem>
                  <NavLink
                    active={this.state.activeTab[0] === '2'}
                    onClick={() => { this.toggle(0, '2') }}
                  >
                    Tax History
                  </NavLink>
                </NavItem>
              </Nav>
              <TabContent activeTab={this.state.activeTab[0]}>
                <TabPane tabId="1"> 
                  <div className="table-wrapper">
                    <BootstrapTable 
                        data={tempdata} 
                        
                        hover
                        pagination
                        filter = {true}
                      >
                        <TableHeaderColumn isKey dataField="transactionDate" filter={{ type: 'TextFilter'}}>
                          Start Date
                        </TableHeaderColumn>
                        <TableHeaderColumn dataField="transactionCategoryDescription" filter={{ type: 'TextFilter'}}>
                          End Date
                        </TableHeaderColumn>
                        <TableHeaderColumn dataField="transactionType" filter={{ type: 'TextFilter'}}>
                          VAT IN
                        </TableHeaderColumn>
                        <TableHeaderColumn dataField="parentTransactionCategory" filter={{ type: 'TextFilter'}}>
                          VAT OUT
                        </TableHeaderColumn>
                        <TableHeaderColumn dataField="transactionType" filter={{ type: 'TextFilter'}}>
                          Total
                        </TableHeaderColumn>
                        <TableHeaderColumn dataField="transactionType" filter={{ type: 'TextFilter'}}>
                          Payment Date
                        </TableHeaderColumn>
                        <TableHeaderColumn dataField="transactionType" filter={{ type: 'TextFilter'}}>
                          Due Amount
                        </TableHeaderColumn>
                        <TableHeaderColumn dataField="transactionType" filter={{ type: 'TextFilter'}}>
                          Paid Amount
                        </TableHeaderColumn>
                        <TableHeaderColumn dataField="transactionType" filter={{ type: 'TextFilter'}}>
                          Status
                        </TableHeaderColumn>
                        <TableHeaderColumn  >
                          Actions
                        </TableHeaderColumn>
                      </BootstrapTable>
                  </div>
                </TabPane>
                <TabPane tabId="2">
                  <div className="table-wrapper">
                    <BootstrapTable 
                        data={tempdata} 
                        
                        hover
                        pagination
                        filter = {true}
                      >
                        <TableHeaderColumn isKey dataField="transactionDate" filter={{ type: 'TextFilter'}}>
                          Start Date
                        </TableHeaderColumn>
                        <TableHeaderColumn dataField="transactionCategoryDescription" filter={{ type: 'TextFilter'}}>
                          End Date
                        </TableHeaderColumn>
                        <TableHeaderColumn dataField="transactionType" filter={{ type: 'TextFilter'}}>
                          VAT IN
                        </TableHeaderColumn>
                        <TableHeaderColumn dataField="parentTransactionCategory" filter={{ type: 'TextFilter'}}>
                          VAT OUT
                        </TableHeaderColumn>
                        <TableHeaderColumn dataField="transactionType" filter={{ type: 'TextFilter'}}>
                          Total
                        </TableHeaderColumn>
                        <TableHeaderColumn dataField="transactionType" filter={{ type: 'TextFilter'}}>
                          Payment Date
                        </TableHeaderColumn>
                        <TableHeaderColumn dataField="transactionType" filter={{ type: 'TextFilter'}}>
                          Due Amount
                        </TableHeaderColumn>
                        <TableHeaderColumn dataField="transactionType" filter={{ type: 'TextFilter'}}>
                          Paid Amount
                        </TableHeaderColumn>
                        <TableHeaderColumn dataField="transactionType" filter={{ type: 'TextFilter'}}>
                          Status
                        </TableHeaderColumn>
                      </BootstrapTable>
                  </div>
                </TabPane>
              </TabContent>
            </CardBody>
          </Card>
        </div>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Taxes)
