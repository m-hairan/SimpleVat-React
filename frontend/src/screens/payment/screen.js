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
  Input,
  Label,

} from 'reactstrap'
import Select from 'react-select'
import { ToastContainer, toast } from 'react-toastify'
import { BootstrapTable, TableHeaderColumn, SearchField } from 'react-bootstrap-table'

import Loader from 'components/loader'

import 'react-toastify/dist/ReactToastify.css'
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css'

import * as PaymentActions from './actions'

import './style.scss'

const mapStateToProps = (state) => {
  return ({
    payment_list: state.payment.payment_list
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    paymentActions: bindActionCreators(PaymentActions, dispatch)
  })
}

class Payment extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      loading: false,
      stateOptions: [
        { value: 'Paid', label: 'Paid' },
        { value: 'Unpaid', label: 'Unpaid' },
        { value: 'Partially Paid', label: 'Partially Paid' },
      ],
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

    this.initializeData = this.initializeData.bind(this)
    this.renderRecieptNumber = this.renderRecieptNumber.bind(this)
    this.onRowSelect = this.onRowSelect.bind(this)
    this.onSelectAll = this.onSelectAll.bind(this)

  }

  componentDidMount () {
    this.initializeData()
  }

  initializeData () {
    this.props.paymentActions.getPaymentList()
  }

  renderRecieptNumber (cell, row) {
    return (
      <label
        className="text-primary my-link mb-0"
        onClick={() => this.props.history.push('/admin/expense/payment/detail')}
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
    const { payment_list } = this.props
    const containerStyle = {
      zIndex: 1999
    }

    return (
      <div className="payment-screen">
        <div className="animated fadeIn">
          <ToastContainer position="top-right" autoClose={5000} style={containerStyle} />
          <Card>
            <CardHeader>
              <Row>
                <Col lg={12}>
                  <div className="d-flex flex-wrap align-items-start justify-content-between">
                    <div>
                      <div className="h4 card-title d-flex align-items-center">
                        <i className="fas fa-money-check" />
                        <span className="ml-2">Payments</span>
                      </div>
                    </div>
                    <div className="filter-box p-2">
                      <Form onSubmit={this.handleSubmit} name="simpleForm">
                        <div className="flex-wrap d-flex">
                          <FormGroup>
                            <Label htmlFor="name">Status:</Label>
                            <div className="filter-wrapper">
                              <Select
                                options={this.state.stateOptions}
                                value={this.state.status}
                                onChange={this.changeStatus}
                              />
                            </div>
                          </FormGroup>
                        </div>
                      </Form>
                    </div>
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
                            color="info"
                            className="btn-square"
                          >
                            <i className="fa glyphicon glyphicon-export fa-upload mr-1" />
                            Import from CSV
                          </Button>
                          <Button
                            color="primary"
                            className="btn-square"
                            onClick={() => this.props.history.push(`/admin/expense/payment/create`)}
                          >
                            <i className="fas fa-plus mr-1" />
                            New Payment
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
                          data={payment_list}
                          version="4"
                          hover
                          pagination
                          totalSize={payment_list ? payment_list.length : 0}
                          className="payment-table"
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
                            Payment Date
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

export default connect(mapStateToProps, mapDispatchToProps)(Payment)
