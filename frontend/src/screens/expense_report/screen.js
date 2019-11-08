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

const tempdata = [{
  transactionCategoryId: 2,
  transactionCategoryCode: 2,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
}, {
  transactionCategoryId: 1,
  transactionCategoryCode: 4,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
}, {
  transactionCategoryId: 1,
  transactionCategoryCode: 4,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
}, {
  transactionCategoryId: 1,
  transactionCategoryCode: 4,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
}, {
  transactionCategoryId: 1,
  transactionCategoryCode: 4,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
},{
  transactionCategoryId: 1,
  transactionCategoryCode: 4,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
},{
  transactionCategoryId: 1,
  transactionCategoryCode: 4,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
}]

const ranges =  {
  'This Week': [moment().startOf('week'), moment().endOf('week')],
  'This Month': [moment().startOf('month'), moment().endOf('month')],
  'Last 7 Days': [moment().subtract(6, 'days'), moment()],
  'Last 30 Days': [moment().subtract(29, 'days'), moment()],
  'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
}

class ExpenseReport extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      selectedOption: '',
    }

    this.handleChange = this.handleChange.bind(this)
  }


  handleChange(selectedOption) {
    this.setState({ selectedOption })
  }

  render() {
    return (
      <div className="expense-report-screen">
        <div className="animated fadeIn">
          <Card>
            <CardHeader>
              <div className="d-flex flex-wrap align-items-start justify-content-between">
                <div>
                  <div className="h4 card-title d-flex align-items-center">
                    <i className="fas fa-tasks" />
                    <span className="ml-2">Expense Report</span>
                  </div>
                  <p className="pl-4"><i className="pl-2">Last updated at 28 October 2019</i></p>
                </div>
                <div className="filter-box p-2">
                  <Form onSubmit={this.handleSubmit} name="simpleForm">
                    <div className="flex-wrap d-flex">
                      <FormGroup>
                        <Label htmlFor="name">Period:</Label>
                        <div className="date-range">
                          <DateRangePicker2
                            ranges={ranges}
                            opens={'left'}
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
                    hover
                    exportCSV
                    pagination
                    filter = {true}
                    responsive={true}
                  >
                    <TableHeaderColumn isKey dataField="transactionCategoryCode" filter={{ type: 'TextFilter'}}>
                      Receipt Number
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="transactionCategoryName" filter={{ type: 'TextFilter'}}>
                      Expense Date
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="transactionCategoryDescription" filter={{ type: 'TextFilter'}}>
                      Description
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="parentTransactionCategory" filter={{ type: 'TextFilter'}}>
                      Amount
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

export default connect(mapStateToProps, mapDispatchToProps)(ExpenseReport)
