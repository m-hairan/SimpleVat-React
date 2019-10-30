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
  Table 
} from "reactstrap"

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

class TransactionReport extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      selectedType: '',
      selectedCategory: ''
    }

    this.changeType = this.changeType.bind(this)
    this.changeCategory = this.changeCategory.bind(this)
  }


  changeType(selectedType) {
    this.setState({ selectedType })
  }

  changeCategory(selectedCategory) {
    this.setState({ selectedCategory })
  }

  render() {
    return (
      <div className="transaction-report-screen">
        <div className="animated fadeIn">
          <Card>
            <CardHeader>
              <div className="d-flex flex-wrap align-items-start justify-content-between">
                <div>
                  <div className="h4 card-title d-flex align-items-center">
                    <i className="fas fa-exchange-alt" />
                    <span className="ml-2">Transaction Report</span>
                  </div>
                  <p className="pl-4"><i className="pl-2">Last updated at 28 October 2019</i></p>
                </div>
                <div className="filter-box p-2">
                  <Form onSubmit={this.handleSubmit} name="simpleForm">
                    <div className="flex-wrap d-flex">
                      <FormGroup>
                        <Label htmlFor="name">Period:</Label>
                        <div className="date-range">
                          <DateRangePicker2  ranges={ranges}/>
                        </div>
                      </FormGroup>  
                      <FormGroup>
                        <Label htmlFor="name">Type:</Label>
                        <div className="account-type">
                          <Select
                            options={colourOptions}
                            value={this.state.selectedType}
                            onChange={this.changeType}
                          />
                        </div>
                      </FormGroup>  
                      <FormGroup>
                        <Label htmlFor="name">Category:</Label>
                        <div className="account-type">
                          <Select
                            options={colourOptions}
                            value={this.state.selectedCategory}
                            onChange={this.changeCategory}
                          />
                        </div>
                      </FormGroup>  
                    </div>
                  </Form>
                </div>
              </div>
            </CardHeader>
            <CardBody>
              <div className="table-wrapper">
                <BootstrapTable 
                    data={tempdata} 
                    exportCSV
                    hover
                    pagination
                    filter = {true}
                  >
                    <TableHeaderColumn isKey dataField="transactionDate" filter={{ type: 'TextFilter'}}>
                      Transaction Date
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="transactionCategoryDescription" filter={{ type: 'TextFilter'}}>
                      Transaction Description
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="transactionType" filter={{ type: 'TextFilter'}}>
                      Transaction Type
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="parentTransactionCategory" filter={{ type: 'TextFilter'}}>
                      Transaction Category
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="transactionType" filter={{ type: 'TextFilter'}}>
                      Transaction Amount
                    </TableHeaderColumn>
                  </BootstrapTable>
              </div>
              
            </CardBody>
          </Card>
        </div>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(TransactionReport)
