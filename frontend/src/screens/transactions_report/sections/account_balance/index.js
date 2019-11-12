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
  Input,
  ButtonGroup
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

class AccountBalances extends React.Component {
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
            <CardBody>
           		<Row>
                <Col lg={12}>
		            	<div className="flex-wrap d-flex" style={{justifyContent:'space-between'}}>
		                  <div className="info-block">
		                    <h4>Company Name - <small><i>Transactions</i></small></h4>
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
		                        <Input type="text" placeholder="Transaction Date" />
		                      </FormGroup>
		                      <FormGroup className="pr-3 my-1">
		                        <Select
		                        	className="select-min-width"
		                          options={colourOptions}
		                          value={this.state.selectedType}
		                          placeholder="Account"
		                          onChange={this.changeType}
		                        />
		                      </FormGroup>  
		                      <FormGroup className="pr-3 my-1">
		                        <Select
		                        	className="select-min-width"
		                          options={colourOptions}
		                          value={this.state.selectedType}
		                          placeholder="Transaction Type"
		                          onChange={this.changeType}
		                        />
		                      </FormGroup>  
		                      <FormGroup className="pr-3 my-1">
		                        <Select
		                        	className="select-min-width"
		                          options={colourOptions}
		                          value={this.state.selectedType}
		                          placeholder="Transaction Category"
		                          onChange={this.changeType}
		                        />
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
		                  >
		                    <TableHeaderColumn isKey dataField="transactionDate">
		                      Transaction Date
		                    </TableHeaderColumn>
		                    <TableHeaderColumn dataField="transactionDate">
		                      Account
		                    </TableHeaderColumn>
		                    <TableHeaderColumn dataField="transactionType">
		                      Transaction Type
		                    </TableHeaderColumn>
		                    <TableHeaderColumn dataField="parentTransactionCategory">
		                      Transaction Category
		                    </TableHeaderColumn>
		                    <TableHeaderColumn dataField="transactionCategoryDescription">
		                      Transaction Description
		                    </TableHeaderColumn>
		                    <TableHeaderColumn dataField="transactionType">
		                      Transaction Amount
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

export default connect(mapStateToProps, mapDispatchToProps)(AccountBalances)
