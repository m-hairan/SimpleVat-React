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
import DateRangePicker from 'react-bootstrap-daterangepicker'

import Loader from 'components/loader'

import 'react-toastify/dist/ReactToastify.css'
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css'


import * as BankStatementActions from './actions'

import './style.scss'

const mapStateToProps = (state) => {
  return ({
    bank_statement_list: state.bank_statement.bank_statement_list
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    bankStatementActions: bindActionCreators(BankStatementActions, dispatch)
  })
}

class BankStatement extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      loading: false,
      openDeleteModal: false,
      stateOptions: [
        { value: 'Explained', label: 'Explained' },
        { value: 'Unexplained', label: 'Unexplained' },
        { value: 'Partially Explained', label: 'Partially Explained' },
      ],

      selectedData: null
    }

    this.initializeData = this.initializeData.bind(this)
    this.toggleDangerModal = this.toggleDangerModal.bind(this)
    this.renderTransactionType = this.renderTransactionType.bind(this)
    this.renderTransactionStatus = this.renderTransactionStatus.bind(this)
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
    this.props.bankStatementActions.getBankStatementList()
  }

  toggleDangerModal () {
    this.setState({
      openDeleteModal: !this.state.openDeleteModal
    })
  }

  goToDetail (row) {
    this.props.history.push('/admin/bank/bank-statement/detail')
  }

  renderTransactionStatus (cell, row) {
    return (
      <span className="badge badge-success mb-0">Explained</span>
    )
  }

  renderTransactionType (cell, row) {
    return (
      <span className="badge badge-primary mb-0">{ row.transaction_type }</span>
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
    const { bank_statement_list } = this.props
    const containerStyle = {
      zIndex: 1999
    }

    return (
      <div className="bank-statement-screen">
        <div className="animated fadeIn">
          <ToastContainer position="top-right" autoClose={5000} style={containerStyle} />
          <Card>
            <CardHeader>
              <Row>
                <Col lg={12}>
                  <div className="d-flex flex-wrap align-items-start justify-content-between">
                    <div>
                      <div className="h4 card-title d-flex align-items-center">
                        <i className="icon-doc" />
                        <span className="ml-2">Bank Statements</span>
                      </div>
                    </div>
                    <div className="filter-box p-2">
                      <Form onSubmit={this.handleSubmit} name="simpleForm">
                        <div className="flex-wrap d-flex"> 
                          <FormGroup>
                            <Label htmlFor="name">Status:</Label>
                            <div className="status-option">
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
                            onClick={() => this.props.history.push('/admin/bank/bank-statement/import')}
                          >
                            <i className="fa glyphicon glyphicon-export fa-upload mr-1" />
                            Import from CSV
                          </Button>
                          <Button
                            color="primary"
                            className="btn-square"
                            onClick={() => this.props.history.push('/admin/bank/bank-statement/create')}
                          >
                            <i className="fas fa-plus mr-1" />
                            New Statement
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
                            <Input type="text" placeholder="Reference Name" />
                          </FormGroup>
                          <FormGroup className="pr-3">
                            <Select
                              className="select-min-width"
                              options={[]}
                              placeholder="Transaction Type"
                            />
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
                          data={bank_statement_list}
                          version="4"
                          hover
                          pagination
                          totalSize={bank_statement_list ? bank_statement_list.length : 0}
                          className="bank-statement-table"
                          trClassName="cursor-pointer"
                        >
                          <TableHeaderColumn
                            dataFormat={this.renderTransactionStatus}
                          >
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            isKey
                            dataField="reference_number"
                          >
                            Reference Number
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataFormat={this.renderTransactionType}
                          >
                            Transaction Type
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="amount" 
                          >
                            Amount
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="description"
                          >
                            Description
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="transaction_date"
                          >
                            Transaction Date
                          </TableHeaderColumn>
                        </BootstrapTable>
                      </div>
                    </Col>
                  </Row>
              }
            </CardBody>
          </Card>
          <Modal
            isOpen={this.state.openDeleteModal}
            centered
            className="modal-primary"
          >
            <ModalHeader toggle={this.toggleDangerModal}>
              <h4 className="mb-0">Are you sure ?</h4>
            </ModalHeader>
            <ModalBody>
              <h5 className="mb-0">This record will be deleleted permanently.</h5>
            </ModalBody>
            <ModalFooter>
              <Button color="primary" className="btn-square" onClick={this.deleteBank}>Yes</Button>{' '}
              <Button color="secondary" className="btn-square" onClick={this.toggleDangerModal}>No</Button>
            </ModalFooter>
          </Modal>
        </div>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(BankStatement)
