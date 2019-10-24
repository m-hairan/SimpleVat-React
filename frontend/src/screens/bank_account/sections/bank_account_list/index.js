import React from 'react'

import { Card, CardHeader, CardBody, Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap'
import { ToastContainer, toast } from 'react-toastify'
import { BootstrapTable, TableHeaderColumn, SearchField } from 'react-bootstrap-table'
import paginationFactory from 'react-bootstrap-table2-paginator'

import Loader from 'components/loader'

import 'react-toastify/dist/ReactToastify.css'
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css'

import './style.scss'

class BankAccountList extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      loading: false,
      openDeleteModal: false,

      selectedData: null
    }

    this.options = {
      paginationSize: 5,
      sortIndicator: true,
      hideSizePerPage: true,
      hidePageListOnlyOnePage: true,
      clearSearch: true,
      alwaysShowAllBtns: false,
      withFirstAndLast: false,
      showTotal: true,
      paginationTotalRenderer: this.customTotal,
      sizePerPageList: [{
        text: '5', value: 5
      }, {
        text: '10', value: 10
      }, {
        text: 'All', value: this.state.bankAccountList ? this.state.bankAccountList.length : 0
      }]
    }

    this.toggleDangerModal = this.toggleDangerModal.bind(this)
    this.startDelete = this.startDelete.bind(this)
    this.successDelete = this.successDelete.bind(this)
    this.deleteBank = this.deleteBank.bind(this)
    this.bankAccounttActions = this.bankAccounttActions.bind(this)
    this.setStatus = this.setStatus.bind(this)

  }

  componentDidMount () {
    if (this.props.is_authed === true) {
      this.initializeData()
    }
  }

  componentWillReceiveProps (newProps) {
    if (newProps.is_authed !== this.props.is_authed && newProps.is_authed === true) {
      this.initializeData()
    }
  }

  initializeData () {
    this.setState({
      loading: true
    })
    this.props.bankAccountActions.getBankAccountList().then(() => {
      this.setState({
        loading: false
      })
    }).catch(err => {
      console.log(err)
      this.setState({
        loading: false
      })
    })
  }

  toggleDangerModal () {
    this.setState({
      openDeleteModal: !this.state.openDeleteModal
    })
  }

  startDelete (data) {
    this.setState({
      selectedData: data
    }, () => {
      this.toggleDangerModal()
    })
  }

  successDelete () {
    return toast.success('Bank Account Deleted Successfully... ', {
      position: toast.POSITION.TOP_RIGHT
    })
  }

  deleteBank () {
    this.setState({
      loading: true
    })
    this.toggleDangerModal()
    this.props.bankAccountActions.deleteBankAccount(this.state.selectedData.bankAccountId).then(res => {
      this.setState({ loading: false })
      this.successDelete()
      this.getBankListData()
    }).catch(err => {
      console.log(err)
      this.setState({ loading: false })
    })
  }

  bankAccounttActions (cell, row) {
    return (
      <div className="d-flex flex-wrap">
        <Button
          block
          color="primary"
          className="btn-pill vat-actions"
          title="Edit Vat Category"
          onClick={() => this.props.history.push(`/admin/bank-account/update?id=${row.bankAccountId}`)}
        >
          <i className="far fa-edit"></i>
        </Button>
        <Button
          block
          color="primary"
          className="btn-pill vat-actions"
          title="Transaction"
          onClick={() => this.props.history.push(`/admin/bank-account/update?id=${row.id}`)}
        >
          <i className="fas fa-university"></i>
        </Button>
        <Button
          block
          color="primary"
          className="btn-pill vat-actions"
          title="Delete Vat Ctegory"
          onClick={() => this.startDelete(row)}
        >
          <i className="fas fa-trash-alt"></i>
        </Button>
      </div>
    )
  }

  setStatus (cell, row) {
    return row.bankAccountStatus.bankAccountStatusName
  }

  render() {

    const { loading } = this.state
    const { bank_account_list } = this.props
    const containerStyle = {
      zIndex: 1999
    }

    return (
      <div className="bank-account-section">
        <div className="animated">
          <ToastContainer position="top-right" autoClose={5000} style={containerStyle} />
          <Card>
            <CardHeader>
              Bank Account
            </CardHeader>
            <CardBody>
              <Button
                className="mb-3"
                onClick={() => this.props.history.push(`/admin/bank-account/update`)}
              >
                New
              </Button>
              {
                loading ?
                  <Loader />
                :
                  <BootstrapTable data={bank_account_list} version="4" striped hover pagination={paginationFactory(this.options)} totalSize={bank_account_list ? bank_account_list.length : 0} >
                    <TableHeaderColumn isKey dataField="bankAccountName">Account Name</TableHeaderColumn>
                    <TableHeaderColumn dataField="accountNumber" >Account Number</TableHeaderColumn>
                    <TableHeaderColumn dataField="accountNumber" >Account Type</TableHeaderColumn>
                    <TableHeaderColumn dataField="accountNumber" >Bank Name</TableHeaderColumn>
                    <TableHeaderColumn dataField="accountNumber" >IBAN Number</TableHeaderColumn>
                    <TableHeaderColumn dataField="accountNumber" >Currency</TableHeaderColumn>
                    <TableHeaderColumn dataField="swiftCode" >Swift Code</TableHeaderColumn>
                    <TableHeaderColumn dataFormat={this.setStatus} >Status</TableHeaderColumn>
                    <TableHeaderColumn dataField="openingBalance" >Current Balance</TableHeaderColumn>
                    <TableHeaderColumn dataField="accountNumber" >Country</TableHeaderColumn>
                    <TableHeaderColumn dataFormat={this.bankAccounttActions}>Action</TableHeaderColumn>
                  </BootstrapTable>
              }
            </CardBody>
          </Card>
          <Modal
            isOpen={this.state.openDeleteModal}
            className="modal-danger"
          >
            <ModalHeader toggle={this.toggleDangerModal}>Delete</ModalHeader>
            <ModalBody>
              Are you sure want to delete this record?
            </ModalBody>
            <ModalFooter>
              <Button color="danger" onClick={this.deleteBank}>Yes</Button>{' '}
              <Button color="secondary" onClick={this.toggleDangerModal}>No</Button>
            </ModalFooter>
          </Modal>
        </div>
      </div>
    )
  }
}

export default BankAccountList


