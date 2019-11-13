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
  ButtonDropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem
} from 'reactstrap'
import Select from 'react-select'
import { ToastContainer, toast } from 'react-toastify'
import { BootstrapTable, TableHeaderColumn, SearchField } from 'react-bootstrap-table'
import DateRangePicker from 'react-bootstrap-daterangepicker'

import { Loader } from 'components'

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
      actionButtons: {},

      selectedData: null
    }

    this.initializeData = this.initializeData.bind(this)
    this.toggleDangerModal = this.toggleDangerModal.bind(this)
    this.renderAccountNumber = this.renderAccountNumber.bind(this)
    this.renderTransactionStatus = this.renderTransactionStatus.bind(this)
    this.renderActions = this.renderActions.bind(this)
    this.onRowSelect = this.onRowSelect.bind(this)
    this.onSelectAll = this.onSelectAll.bind(this)
    this.toggleActionButton = this.toggleActionButton.bind(this)

    this.options = {
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

  toggleActionButton (index) {
    let temp = Object.assign({}, this.state.actionButtons)
    if (temp[index]) {
      temp[index] = false
    } else {
      temp[index] = true
    }
    this.setState({
      actionButtons: temp
    })
  }

  renderAccountNumber (cell, row) {
    return (
    <label
      className="mb-0 my-link"
      onClick={() => this.props.history.push('/admin/bank/bank-statement/detail')}
    >
      { row.reference_number }
    </label>
    )
  }

  renderTransactionStatus (cell, row) {
    return (
      <span className="badge badge-success mb-0">Reconciled</span>
    )
  }

  renderActions (cell, row) {
    return (
      <div>
        <ButtonDropdown
          isOpen={this.state.actionButtons[row.reference_number]}
          toggle={() => this.toggleActionButton(row.reference_number)}
        >
          <DropdownToggle size="sm" color="primary" className="btn-brand icon">
            {
              this.state.actionButtons[row.reference_number] == true ?
                <i className="fas fa-chevron-up" />
              :
                <i className="fas fa-chevron-down" />
            }
          </DropdownToggle>
          <DropdownMenu right>
            <DropdownItem onClick={() => this.props.history.push('/admin/bank/bank-statement/detail')}>
              <i className="fas fa-edit" /> Edit
            </DropdownItem>
            <DropdownItem>
              <i className="fas fa-wrench" /> Achive
            </DropdownItem>
            <DropdownItem>
              <i className="fa fa-trash" /> Delete
            </DropdownItem>
          </DropdownMenu>
        </ButtonDropdown>
      </div>
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
                  <div className="h4 mb-0 d-flex align-items-center">
                    <i className="icon-doc" />
                    <span className="ml-2">Bank Statements</span>
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
                      <div className="d-flex justify-content-end">
                        <ButtonGroup size="sm">
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
                        </ButtonGroup>
                      </div>
                      <div className="py-3">
                        <Form inline>
                          <FormGroup className="pr-3 my-1">
                            <h6 className="m-0">View By : </h6>
                          </FormGroup>
                          <FormGroup className="pr-3 my-1">
                            <Select
                              className="select-min-width"
                              options={[]}
                              placeholder="Bank"
                            />
                          </FormGroup>
                          <FormGroup className="pr-3 my-1">
                            <Input type="text" placeholder="Account Number" />
                          </FormGroup>
                          <FormGroup className="pr-3 my-1">
                            <Input type="text" placeholder="Account Name" />
                          </FormGroup>
                          <FormGroup className="pr-3 my-1">
                            <Select
                              className="select-min-width"
                              options={[]}
                              placeholder="Status"
                            />
                          </FormGroup>
                        </Form>
                      </div>
                      <div>
                        <BootstrapTable
                          search={false}
                          options={ this.options }
                          data={bank_statement_list}
                          version="4"
                          hover
                          pagination
                          totalSize={bank_statement_list ? bank_statement_list.length : 0}
                          className="bank-statement-table"
                        >
                          <TableHeaderColumn
                            width="110"
                            dataFormat={this.renderTransactionStatus}
                          >
                            Status
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            isKey
                            dataField="reference_number"
                            dataFormat={this.renderAccountNumber}
                            dataSort
                          >
                            Account Number
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="description"
                            dataSort
                          >
                            Account Name
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="amount"
                            dataSort
                          >
                            Number of Records
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="amount"
                            dataSort
                          >
                            Upload Date
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            className="text-right"
                            columnClassName="text-right"
                            width="55"
                            dataFormat={this.renderActions}
                          >
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
