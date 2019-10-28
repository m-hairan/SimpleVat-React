import React from 'react'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'
import {
  Card,
  CardHeader,
  CardBody,
  FormGroup,
  Label,
  Form,
  Badge
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
  status: 'paid',
  transactionCategoryId: 2,
  transactionCategoryCode: 2,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
}, {
  status: 'paid',
  transactionCategoryId: 1,
  transactionCategoryCode: 4,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
}, {
  status: 'paid',
  transactionCategoryId: 1,
  transactionCategoryCode: 4,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
}, {
  status: 'unpaid',
  transactionCategoryId: 1,
  transactionCategoryCode: 4,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
}, {
  status: 'unpaid',
  transactionCategoryId: 1,
  transactionCategoryCode: 4,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
},{
  status: 'paid',
  transactionCategoryId: 1,
  transactionCategoryCode: 4,
  transactionCategoryName: 'temp',
  transactionCategoryDescription: 'temp',
  parentTransactionCategory: 'Loream Ipsume',
  transactionType: 'TEMP'
},{
  status: 'unpaid',
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

class InvoiceReport extends React.Component {
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

  getInvoiceStatus(cell, row) {
    return(<Badge style={{width: 50, padding: 5}} color={cell === 'paid'?'success':'danger'}>{cell}</Badge>)
  }

  render() {
    return (
      <div className="invoice-report-screen">
        <div className="animated fadeIn">
          <Card>
            <CardHeader>
              <div className="d-flex flex-wrap" style={{justifyContent: 'space-between'}}>
                <div>
                  <h2 className="card-title">Invoice Report</h2>
                  <p><i>Last updated at 28 October 2019</i></p>
                </div>
                <div className="filter-box">
                  <Form onSubmit={this.handleSubmit} name="simpleForm">
                    <div className="flex-wrap d-flex">
                      <FormGroup>
                        <Label htmlFor="name">Period:</Label>
                        <div className="date-range">
                          <DateRangePicker2  ranges={ranges}/>
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
                    responsive={true}
                  >
                    <TableHeaderColumn width="75" dataField="status"  dataFormat={this.getInvoiceStatus}>
                      
                    </TableHeaderColumn>
                    <TableHeaderColumn isKey dataField="transactionCategoryCode" filter={{ type: 'TextFilter'}}>
                      Ref.Number
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="transactionCategoryName" filter={{ type: 'TextFilter'}}>
                      Date
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="transactionCategoryDescription" filter={{ type: 'TextFilter'}}>
                      Due Date
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="parentTransactionCategory" filter={{ type: 'TextFilter'}}>
                      Contact Name
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="transactionType" filter={{ type: 'TextFilter'}}>
                      No.ofItems
                    </TableHeaderColumn>
                    <TableHeaderColumn dataField="transactionType" filter={{ type: 'TextFilter'}}>
                      Total Cost
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

export default connect(mapStateToProps, mapDispatchToProps)(InvoiceReport)
