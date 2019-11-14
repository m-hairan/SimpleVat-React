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
  ButtonDropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem
} from 'reactstrap'
import Select from 'react-select'
import { ToastContainer, toast } from 'react-toastify'
import { BootstrapTable, TableHeaderColumn, SearchField } from 'react-bootstrap-table'

import { Loader } from 'components'

import * as BankAccountActions from './actions'

import 'react-toastify/dist/ReactToastify.css'
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css'
import './style.scss'

const mapStateToProps = (state) => {
  return ({
    is_authed: state.auth.is_authed,
    bank_account_list: state.bank_account.bank_account_list
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    bankAccountActions: bindActionCreators(BankAccountActions, dispatch)
  })
}

class BankAccount extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      loading: false,
      openDeleteModal: false,
      actionButtons: {},

      selectedData: null
    }

    this.initializeData = this.initializeData.bind(this)
    this.toggleDangerModal = this.toggleDangerModal.bind(this)
    this.renderAccountNumber = this.renderAccountNumber.bind(this)
    this.renderAccountType = this.renderAccountType.bind(this)
    this.renderActions = this.renderActions.bind(this)
    this.onRowSelect = this.onRowSelect.bind(this)
    this.onSelectAll = this.onSelectAll.bind(this)
    this.toggleActionButton = this.toggleActionButton.bind(this)

    this.options = {
      paginationPosition: 'top'
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
    this.props.bankAccountActions.getBankAccountList()
  }

  toggleDangerModal () {
    this.setState({
      openDeleteModal: !this.state.openDeleteModal
    })
  }

  renderAccountType (cell, row) {
    return (
      <label className="badge badge-primary mb-0">{ row.account_type }</label>
    )
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
      onClick={() => this.props.history.push('/admin/bank/transactions')}
    >
      { row.account_number }
    </label>
    )
  }

  renderActions (cell, row) {
    return (
      <div>
        <ButtonDropdown
          isOpen={this.state.actionButtons[row.account_number]}
          toggle={() => this.toggleActionButton(row.account_number)}
        >
          <DropdownToggle size="sm" color="primary" className="btn-brand icon">
            {
              this.state.actionButtons[row.account_number] == true ?
                <i className="fas fa-chevron-up" />
              :
                <i className="fas fa-chevron-down" />
            }
          </DropdownToggle>
          <DropdownMenu right>
            <DropdownItem onClick={() => this.props.history.push('/admin/bank/detail')}>
              <i className="fas fa-edit" /> Edit
            </DropdownItem>
            <DropdownItem onClick={() => this.props.history.push('/admin/bank/transactions')}>
              <i className="fas fa-eye" /> View Transactions
            </DropdownItem>
            <DropdownItem>
              <i className="fas fa-upload" /> Import Statement
            </DropdownItem>
            <DropdownItem>
              <i className="fa fa-connectdevelop" /> Reconcile
            </DropdownItem>
            <DropdownItem>
              <i className="fa fa-trash" /> Close
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
    const { bank_account_list } = this.props
    const containerStyle = {
      zIndex: 1999
    }

    return (
      <div className="bank-account-screen">
        <div className="animated fadeIn">
          <ToastContainer position="top-right" autoClose={5000} style={containerStyle} />
          <Card>
            <CardHeader>
              <Row>
                <Col lg={12}>
                  <div className="h4 mb-0 d-flex align-items-center">
                    <i className="fas fa-university" />
                    <span className="ml-2">Bank Accounts</span>
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
                            Import Statement
                          </Button>
                          <Button
                            color="primary"
                            className="btn-square"
                            onClick={() => this.props.history.push(`/admin/bank/create`)}
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
                            <Select
                              className="select-min-width"
                              options={[]}
                              placeholder="Account Type"
                            />
                          </FormGroup>
                          <FormGroup className="pr-3 my-1">
                            <Select
                              className="select-min-width"
                              options={[]}
                              placeholder="Currency"
                            />
                          </FormGroup>
                        </Form>
                      </div>
                      <div>
                        <BootstrapTable
                          selectRow={ this.selectRowProp }
                          search={false}
                          options={ this.options }
                          data={bank_account_list}
                          version="4"
                          hover
                          pagination
                          totalSize={bank_account_list ? bank_account_list.length : 0}
                          className="bank-account-table"
                        >
                          <TableHeaderColumn
                            dataField="account_name"
                            dataSort
                          >
                            Bank
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataFormat={this.renderAccountType}
                            dataSort
                          >
                            Account Type
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            isKey
                            dataField="account_number"
                            dataFormat={this.renderAccountNumber}
                            dataSort
                          >
                            Account Number
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="bank_name"
                            dataSort
                          >
                            Account Name
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="IBAN_number"
                            dataSort
                          >
                            Currency
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="swift_code"
                            dataSort
                          >
                            Balance
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

export default connect(mapStateToProps, mapDispatchToProps)(BankAccount)
