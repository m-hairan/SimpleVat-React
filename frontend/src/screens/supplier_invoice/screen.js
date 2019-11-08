import React from 'react'
import { connect } from 'react-redux'
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
import DateRangePicker from 'react-bootstrap-daterangepicker'

import Loader from 'components/loader'

import 'react-toastify/dist/ReactToastify.css'
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css'
import 'bootstrap-daterangepicker/daterangepicker.css'

import * as SupplierInvoiceActions from './actions'

import './style.scss'

const mapStateToProps = (state) => {
  return ({
    supplier_invoice_list: state.supplier_invoice.supplier_invoice_list
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    supplierInvoiceActions: bindActionCreators(SupplierInvoiceActions, dispatch)
  })
}

class SupplierInvoice extends React.Component {

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

    this.renderInvoiceStatus = this.renderInvoiceStatus.bind(this)
    this.renderActions = this.renderActions.bind(this)
    this.onRowSelect = this.onRowSelect.bind(this)
    this.onSelectAll = this.onSelectAll.bind(this)
    this.goToDetail = this.goToDetail.bind(this)

    this.options = {
      onRowClick: this.goToDetail
    }
    this.selectRowProp = {
      mode: 'checkbox',
      bgColor: 'rgba(0,0,0, 0.05)',
      clickToSelect: false,
      onSelect: this.onRowSelect,
      onSelectAll: this.onSelectAll
    }
  }

  componentDidMount () {
    this.initializeData()
  }

  initializeData () {
    this.props.supplierInvoiceActions.getSupplierInoviceList()
  }

  renderInvoiceStatus (cell, row) {
    return (
      <span className="badge badge-success mb-0">{ row.status }</span>
    )
  }

  renderActions (cell, row) {
    return (
      <div>
        <Button
          size="sm"
          color="primary"
          className="btn-brand icon mr-1"
          title="Post"
        >
          <i className="fas fa-heart" />
        </Button>
        <Button
          size="sm"
          color="success"
          className="btn-brand icon mr-1"
          title="Adjust"
        >
          <i className="fas fa-adjust" />
        </Button>
        <Button
          size="sm"
          color="info"
          className="btn-brand icon mr-1"
          title="Send"
        >
          <i className="fas fa-upload" />
        </Button>
        <Button
          size="sm"
          color="warning"
          className="btn-brand icon mr-1"
          title="Print"
        >
          <i className="fas fa-print" />
        </Button>
        <Button
          size="sm"
          color="danger"
          className="btn-brand icon"
          title="Cancel"
        >
          <i className="fa fa-trash-o" />
        </Button>
      </div>
    )
  }

  goToDetail (row) {
    this.props.history.push('/admin/expense/supplier-invoice/detail')
  }

  onRowSelect (row, isSelected, e) {
    console.log('one row checked ++++++++', row)
  }
  onSelectAll (isSelected, rows) {
    console.log('current page all row checked ++++++++', rows)
  }


  render() {

    const { loading } = this.state
    const { supplier_invoice_list } = this.props
    const containerStyle = {
      zIndex: 1999
    }

    return (
      <div className="supplier-invoice-screen">
        <div className="animated fadeIn">
          <ToastContainer position="top-right" autoClose={5000} style={containerStyle} />
          <Card>
            <CardHeader>
              <Row>
                <Col lg={12}>
                  <div className="d-flex flex-wrap align-items-start justify-content-between">
                    <div>
                      <div className="h4 card-title d-flex align-items-center">
                        <i className="fas fa-address-book" />
                        <span className="ml-2">Supplier Invoices</span>
                      </div>
                    </div>
                    <div className="filter-box p-2">
                      <Form onSubmit={this.handleSubmit} name="simpleForm">
                        <div className="flex-wrap d-flex">
                          <FormGroup>
                            <Label htmlFor="name">Contact:</Label>
                            <div className="filter-wrapper">
                              <Select
                                options={[]}
                              />
                            </div>
                          </FormGroup>
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
                      <div className="my-4 status-panel p-3">
                        <Row>
                          <Col lg={3}>
                            <h5>Overdue</h5>
                            <h3 className="status-title">$53.25 USD</h3>
                          </Col>
                          <Col lg={3}>
                            <h5>Comming due within 30 days</h5>
                            <h3 className="status-title">$220.28 USD</h3>
                          </Col>
                          <Col lg={3}>
                            <h5>Average time get paid</h5>
                            <h3 className="status-title">0 day</h3>
                          </Col>
                          <Col lg={3}>
                            <h5>Next payment</h5>
                            <h3 className="status-title">None</h3>
                          </Col>
                        </Row>
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
                            onClick={() => this.props.history.push(`/admin/expense/supplier-invoice/create`)}
                          >
                            <i className="fas fa-plus mr-1" />
                            New Invoice
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
                            <Input type="text" placeholder="Supplier Name" />
                          </FormGroup>
                          <FormGroup className="pr-3">
                            <Input type="text" placeholder="Reference Number" />
                          </FormGroup>
                          <FormGroup className="pr-3">
                            <DateRangePicker>
                              <Input type="text" placeholder="Date Range" />
                            </DateRangePicker>
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
                          data={supplier_invoice_list}
                          version="4"
                          hover
                          pagination
                          totalSize={supplier_invoice_list ? supplier_invoice_list.length : 0}
                          className="supplier-invoice-table"
                          trClassName="cursor-pointer"
                        >
                          <TableHeaderColumn
                            dataFormat={this.renderInvoiceStatus}
                          >
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            isKey
                            dataField="transactionCategoryCode"
                          >
                            Conact Name
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="transactionCategoryName"
                          >
                            Invoice Number
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="transactionCategoryDescription" 
                          >
                            Invoice Date
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="parentTransactionCategory"
                          >
                            Due Date
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="transactionType"
                          >
                            Invocice Amount
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="transactionType"
                          >
                            VAT Amount
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            className="text-right"
                            columnClassName="text-right"
                            dataFormat={this.renderActions}
                          >
                            Actions
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

export default connect(mapStateToProps, mapDispatchToProps)(SupplierInvoice)
