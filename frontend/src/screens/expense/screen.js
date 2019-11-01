import React from 'react'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'
import {
  Card,
  CardHeader,
  CardBody,
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Row,
  Col,
  ButtonGroup,
  Form,
  FormGroup,
  Input
} from 'reactstrap'
import { ToastContainer, toast } from 'react-toastify'
import { BootstrapTable, TableHeaderColumn, SearchField } from 'react-bootstrap-table'

import Loader from 'components/loader'

import 'react-toastify/dist/ReactToastify.css'
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css'

import * as ExpenseActions from './actions'

import './style.scss'

const mapStateToProps = (state) => {
  return ({
    expense_list: state.expense.expense_list
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    expenseActions: bindActionCreators(ExpenseActions, dispatch)
  })
}

class Expense extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      loading: false,
    }

    this.options = {
    }

    this.selectRowProp = {
      mode: 'checkbox',
      bgColor: 'rgba(0,0,0, 0.05)',
      clickToSelect: false,
      onSelect: this.onRowSelect,
      onSelectAll: this.onSelectAll
    }

    this.renderRecieptNumber = this.renderRecieptNumber.bind(this)
    this.onRowSelect = this.onRowSelect.bind(this)
    this.onSelectAll = this.onSelectAll.bind(this)

  }

  componentDidMount () {
    this.initializeData()
  }

  initializeData () {
    this.props.expenseActions.getExpenseList()
  }

  renderRecieptNumber (cell, row) {
    return (
      <label
        className="text-primary my-link mb-0"
        onClick={() => this.props.history.push('/admin/expense/expense/detail')}
      >
        { row.transactionCategoryName }
      </label>
    )
  }

  onRowSelect (row, isSelected, e) {
    console.log('one row checked ++++++++', row)
  }
  onSelectAll (isSelected, rows) {
    console.log('current page all row checked ++++++++', rows)
  }

  render() {

    const { loading } = this.state
    const { expense_list } = this.props
    const containerStyle = {
      zIndex: 1999
    }

    return (
      <div className="expense-screen">
        <div className="animated">
          <ToastContainer position="top-right" autoClose={5000} style={containerStyle} />
          <Card>
            <CardHeader>
              <Row>
                <Col lg={12}>
                  <div className="h4 mb-0 d-flex align-items-center">
                    <i className="fab fa-stack-exchange" />
                    <span className="ml-2">Expenses</span>
                  </div>
                </Col>
              </Row>
            </CardHeader>
            <CardBody>
              {
                loading ?
                  <Row>
                    <Col lg={12}>
                      <Loader />
                    </Col>
                  </Row>
                :
                  <Row>
                    <Col lg={12}>
                      <div className="mb-2">
                        <ButtonGroup size="sm">
                          <Button
                            color="success"
                            className="btn-square"
                          >
                            <i className="fa glyphicon glyphicon-export fa-download mr-1" />
                            Export to CSV
                          </Button>
                          <Button
                            color="primary"
                            className="btn-square"
                            onClick={() => this.props.history.push(`/admin/expense/expense/create`)}
                          >
                            <i className="fas fa-plus mr-1" />
                            New Account
                          </Button>
                          <Button
                            color="warning"
                            className="btn-square"
                          >
                            <i className="fa glyphicon glyphicon-trash fa-trash mr-1" />
                            Bulk Delete
                          </Button>
                        </ButtonGroup>
                      </div>
                      <div className="filter-panel my-3 py-3">
                        <Form inline>
                          <FormGroup className="pr-3">
                            <Input type="text" placeholder="From" />
                          </FormGroup>
                          <FormGroup className="pr-3">
                            <Input type="text" placeholder="To" />
                          </FormGroup>
                          <FormGroup className="pr-3">
                            <Input type="text" placeholder="Reciept Number" />
                          </FormGroup>
                          <Button
                            type="submit"
                            color="primary"
                            className="btn-square"
                          >
                            <i className="fas fa-search mr-1"></i>Filter
                          </Button>
                        </Form>
                      </div>
                      <div>
                        <BootstrapTable
                          selectRow={ this.selectRowProp }
                          search={true}
                          options={ this.options }
                          data={expense_list}
                          version="4"
                          hover
                          pagination
                          totalSize={expense_list ? expense_list.length : 0}
                          className="expense-table"
                        >
                          <TableHeaderColumn
                            isKey
                            dataField="transactionCategoryName"
                            dataFormat={this.renderRecieptNumber}
                          >
                            Reciept Number
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="transactionCategoryCode"
                          >
                            Amount
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="parentTransactionCategory"
                          >
                            Description
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="transactionType"
                          >
                            Expense Date
                          </TableHeaderColumn>
                        </BootstrapTable>
                      </div>
                    </Col>
                  </Row>
              }
            </CardBody>
          </Card>
        </div>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Expense)
