import React from 'react'

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
  Col
} from 'reactstrap'
import { ToastContainer, toast } from 'react-toastify'
import { BootstrapTable, TableHeaderColumn, SearchField } from 'react-bootstrap-table'

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
    this.renderCheckBox = this.renderCheckBox.bind(this)
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

  renderCheckBox (cell, row) {
    return (
      <div className="table-action">
        
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
              <Row>
                <Col lg={12}>
                  <div className="d-flex align-items-center justify-content-between">
                    <div className="h5 mb-0 d-flex align-items-center">
                      <i className="fas fa-university" />
                      <span className="ml-2">Bank Accounts</span>
                    </div>
                    <div>
                      <Button
                        color="primary"
                        className="btn-square"
                        onClick={() => this.props.history.push(`/admin/bank-account/update`)}
                      >
                        <i className="fas fa-plus mr-1" />
                        New Account
                      </Button>
                    </div>
                  </div>
                </Col>
              </Row>
            </CardHeader>
            <CardBody>
              <Row>
                <Col lg={12}>
                  {
                    loading ?
                      <Loader />
                    :
                      <BootstrapTable
                        data={bank_account_list}
                        version="4"
                        hover
                        pagination
                        totalSize={bank_account_list ? bank_account_list.length : 0}
                        className="bank-account-table"
                      >
                        
                        </TableHeaderColumn>
                        <TableHeaderColumn isKey dataField="bankAccountName">Account Name</TableHeaderColumn>
                        <TableHeaderColumn dataField="accountNumber" >Account Number</TableHeaderColumn>
                        <TableHeaderColumn dataField="accountNumber" >Account Type</TableHeaderColumn>
                        <TableHeaderColumn dataField="accountNumber" >Bank Name</TableHeaderColumn>
                        <TableHeaderColumn dataField="swiftCode" >Swift Code</TableHeaderColumn>
                        <TableHeaderColumn dataFormat={this.setStatus} >Status</TableHeaderColumn>
                        <TableHeaderColumn dataField="openingBalance" >Opening Balance</TableHeaderColumn>
                      </BootstrapTable>
                  }
                </Col>
              </Row>
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

export default BankAccountList


