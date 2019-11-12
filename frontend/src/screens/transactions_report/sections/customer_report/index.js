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
  Badge,
  Row,
  Col,
  Input,
  Button,
  ButtonGroup
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

class CustomerReport extends React.Component {
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
            <CardBody>
              <Row>
                <Col lg={12}>
                  <div className="flex-wrap d-flex" style={{justifyContent:'space-between'}}>
                    <div className="info-block">
                      <h4>Company Name - <small><i>Invoices</i></small></h4>
                    </div>
                    <Form onSubmit={this.handleSubmit} name="simpleForm">
                        <div className="flex-wrap d-flex">
                          <FormGroup>
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
                  <div className="mb-2">
                    <ButtonGroup size="sm">
                      <Button
                        color="success"
                        className="btn-square"
                      >
                        <i className="fa glyphicon glyphicon-export fa-download mr-1" />
                        Export to CSV
                      </Button>
                    </ButtonGroup>
                  </div>
                  <div className="filter-panel my-3 py-3">
                    <Form inline>
                      <FormGroup className="pr-3 my-1">
                        <Input type="text" placeholder="Ref.Number" />
                      </FormGroup>
                      <FormGroup className="pr-3 my-1">
                        <Input type="text" placeholder="Date" />
                      </FormGroup>
                      <FormGroup className="pr-3 my-1">
                        <Input type="text" placeholder="Due Date" />
                      </FormGroup>
                      <FormGroup className="pr-3 my-1">
                        <Input type="text" placeholder="Contact Name" />
                      </FormGroup>
                      <Button
                        type="submit"
                        color="primary"
                        className="btn-square my-1"
                      >
                        <i className="fas fa-search mr-1"></i>Filter
                      </Button>
                    </Form>
                  </div>
		              <div className="table-wrapper">
		                <BootstrapTable 
		                    data={tempdata} 
		                    hover
		                    pagination
		                    filter = {true}
		                    responsive={true}
		                  >
		                    <TableHeaderColumn width="75" dataField="status"  dataFormat={this.getInvoiceStatus}>
		                      
		                    </TableHeaderColumn>
		                    <TableHeaderColumn isKey dataField="transactionCategoryCode">
		                      Ref.Number
		                    </TableHeaderColumn>
		                    <TableHeaderColumn dataField="transactionCategoryName">
		                      Date
		                    </TableHeaderColumn>
		                    <TableHeaderColumn dataField="transactionCategoryDescription">
		                      Due Date
		                    </TableHeaderColumn>
		                    <TableHeaderColumn dataField="parentTransactionCategory">
		                      Contact Name
		                    </TableHeaderColumn>
		                    <TableHeaderColumn dataField="transactionType">
		                      No.ofItems
		                    </TableHeaderColumn>
		                    <TableHeaderColumn dataField="transactionType">
		                      Total Cost
		                    </TableHeaderColumn>
		                  </BootstrapTable>
		              </div>
              	</Col>
              </Row>
            </CardBody>
          </Card>
        </div>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(CustomerReport)
