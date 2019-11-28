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

import { BootstrapTable, TableHeaderColumn, SearchField } from 'react-bootstrap-table'

import { Loader } from 'components'
import {
  selectOptionsFactory,
  filterFactory
} from 'utils'

import * as BankAccountActions from './actions'

import 'react-toastify/dist/ReactToastify.css'
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css'
import './style.scss'

const mapStateToProps = (state) => {
  return ({
    is_authed: state.auth.is_authed,
    account_type_list: state.bank_account.account_type_list,
    currency_list: state.bank_account.currency_list,
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

      selectedData: null,

      filter_bank: '',
      filter_account_type: null,
      filter_account_name: '',
      filter_account_number: '',
      filter_currency: null
    }

    this.initializeData = this.initializeData.bind(this)
    this.inputHandler = this.inputHandler.bind(this)
    this.toggleDangerModal = this.toggleDangerModal.bind(this)
    this.filterBankAccountList = this.filterBankAccountList.bind(this)

    this.renderAccountNumber = this.renderAccountNumber.bind(this)
    this.renderAccountType = this.renderAccountType.bind(this)
    this.renderCurrency = this.renderCurrency.bind(this)
    this.renderActions = this.renderActions.bind(this)
    this.renderLastReconciled = this.renderLastReconciled.bind(this)

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
    this.props.bankAccountActions.getAccountTypeList()
    this.props.bankAccountActions.getCurrencyList()
    this.props.bankAccountActions.getBankAccountList()
  }

  inputHandler (key, value) {
    this.setState({
      [key]: value
    })
  }

  toggleDangerModal () {
    this.setState({
      openDeleteModal: !this.state.openDeleteModal
    })
  }

  filterBankAccountList (bank_account_list) {
    let data = []
    const {
      filter_bank,
      filter_account_type,
      filter_account_name,
      filter_account_number,
      filter_currency
    } = this.state
    
    data = filterFactory.filterDataList(filter_bank, 'bankName', 'contain', bank_account_list)
    let data_temp = []
    data.map(item => {
      let flag = true
      flag = filterFactory.compareString(
        filter_account_type ? filter_account_type.value : '',
        item.bankAccountType && item.bankAccountType.id ? item.bankAccountType.id : '',
        'match'
      )
      if (flag) {
        let temp = Object.assign({}, item)
        data_temp.push(temp)
      }
    })
    data = filterFactory.filterDataList(filter_account_name, 'bankAccountName', 'contain', data_temp)
    data_temp = filterFactory.filterDataList(filter_account_number, 'accountNumber', 'contain', data)
    data = []
    data_temp.map(item => {
      let flag = true
      flag = filterFactory.compareString(
        filter_currency ? filter_currency.value : '',
        item.bankAccountCurrency && item.bankAccountCurrency.currencyCode ? item.bankAccountCurrency.currencyCode : '',
        'match'
      )
      if (flag) {
        let temp = Object.assign({}, item)
        data.push(temp)
      }
    })
    return data
  }

  renderAccountType (cell, row) {
    if (row.bankAccountType) {
      let data = null
      switch(row.bankAccountType.name) {
        case 'Saving':
          data = <label className="badge badge-primary mb-0">{ row.bankAccountType.name }</label>
          break
        case 'Current':
          data = <label className="badge badge-info mb-0">{ row.bankAccountType.name }</label>
          break
        default:
          data = <label className="badge badge-default mb-0">No Specified</label>
          break
      }
      return data
    } else {
      return (
        <label className="badge badge-danger mb-0">No Specified</label>
      )
    }
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
        onClick={() => this.props.history.push('/admin/banking/bank-account/transaction',
          {bankAccountId: row.bankAccountId })}
      >
        { row.accountNumber }
      </label>
    )
  }

  renderCurrency (cell, row) {
    if (
      row.bankAccountCurrency &&
      row.bankAccountCurrency.currencyIsoCode
    ) {
      return (
        <label className="badge badge-primary mb-0">{ row.bankAccountCurrency.currencyIsoCode }</label>
      )  
    } else {
      return (
        <label className="badge badge-danger mb-0">No Specified</label>
      )
    }
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
            <DropdownItem onClick={() => this.props.history.push('/admin/banking/bank-account/detail', {
              bankAccountId: row.bankAccountId
            })}>
              <i className="fas fa-edit" /> Edit
            </DropdownItem>
            <DropdownItem onClick={() => this.props.history.push('/admin/banking/bank-account/transaction')}>
              <i className="fas fa-eye" /> View Transactions
            </DropdownItem>
            <DropdownItem onClick={() => this.props.history.push('/admin/banking/upload-statement')}>
              <i className="fas fa-upload" /> Upload Statement
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

  renderLastReconciled (cell, row) {
    return (
      <div>
        <div>
          <label className="font-weight-bold mr-2">Balance : </label>
          <label className="badge badge-success mb-0">Test</label>
        </div>
        <div>
          <label className="font-weight-bold mr-2">Date : </label><label>2019/12/05</label>
        </div>
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

    const {
      loading,
      filter_bank,
      filter_account_type,
      filter_account_name,
      filter_account_number,
      filter_currency
    } = this.state
    const {
      account_type_list,
      currency_list,
      bank_account_list
    } = this.props

    let displayData = this.filterBankAccountList(bank_account_list)

    return (
      <div className="bank-account-screen">
        <div className="animated fadeIn">
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
                            onClick={() => this.props.history.push('/admin/banking/upload-statement')}
                          >
                            <i className="fa glyphicon glyphicon-export fa-upload mr-1" />
                            Upload Statement
                          </Button>
                          <Button
                            color="primary"
                            className="btn-square"
                            onClick={() => this.props.history.push(`/admin/banking/bank-account/create`)}
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
                        <h5>Filter : </h5>
                        <Row>
                          <Col lg={2} className="mb-1">
                            <Input
                              type="text"
                              placeholder="Bank"
                              value={filter_bank}
                              onChange={e => this.inputHandler('filter_bank', e.target.value)}
                            />
                          </Col>
                          <Col lg={2} className="mb-1">
                            <Select
                              className=""
                              options={selectOptionsFactory.renderOptions('name', 'id', account_type_list)}
                              value={filter_account_type}
                              onChange={option => this.setState({
                                filter_account_type: option
                              })}
                              placeholder="Account Type"
                            />
                          </Col>
                          <Col lg={2} className="mb-1">
                            <Input
                              type="text"
                              placeholder="Account Name"
                              value={filter_account_name}
                              onChange={e => this.inputHandler('filter_account_name', e.target.value)}
                            />
                          </Col>
                          <Col lg={2} className="mb-1">
                            <Input
                              type="text"
                              placeholder="Account Number"
                              value={filter_account_number}
                              onChange={e => this.inputHandler('filter_account_number', e.target.value)}
                            />
                          </Col>
                          <Col lg={2} className="mb-1">
                            <Select
                              className=""
                              options={selectOptionsFactory.renderOptions('currencyName', 'currencyCode', currency_list)}
                              value={filter_currency}
                              onChange={option => this.setState({
                                filter_currency: option
                              })}
                              placeholder="Currency"
                            />
                          </Col>
                        </Row>
                      </div>
                      <div>
                        <BootstrapTable
                          selectRow={ this.selectRowProp }
                          search={false}
                          options={ this.options }
                          data={displayData}
                          version="4"
                          hover
                          pagination
                          totalSize={displayData ? displayData.length : 0}
                          className="bank-account-table"
                        >
                          <TableHeaderColumn
                            dataField="bankName"
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
                            dataField="accountNumber"
                            dataFormat={this.renderAccountNumber}
                            dataSort
                          >
                            Account Number
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="bankAccountName"
                            dataSort
                          >
                            Account Name
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataFormat={this.renderCurrency}
                            dataSort
                          >
                            Currency
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="openingBalance"
                            dataSort
                          >
                            Book Balance
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="swift_code"
                            dataFormat={this.renderLastReconciled}
                          >
                            Last Reconciled
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
