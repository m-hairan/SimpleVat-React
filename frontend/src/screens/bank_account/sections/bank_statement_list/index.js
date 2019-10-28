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

class BankStatementList extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      loading: false,
      openDeleteModal: false,

      selectedData: null
    }

    this.options = {
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
    this.props.bankAccountActions.getBankAccountList()
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
      <div className="table-action text-right">
        <Button
          color="primary"
          className="btn-pill vat-actions ml-1"
          title="Edit Vat Category"
          onClick={() => this.props.history.push(`/admin/bank-account/update?id=${row.bankAccountId}`)}
        >
          <i className="far fa-edit"></i>
        </Button>
        <Button
          color="primary"
          className="btn-pill vat-actions ml-1"
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
      <div className="bank-statement-section">
        <div className="animated">
          <ToastContainer position="top-right" autoClose={5000} style={containerStyle} />
          <Card>
            <CardHeader>
              <Row>
                <Col lg={12}>
                  <div className="d-flex align-items-center justify-content-between">
                    <div className="h5 mb-0 d-flex align-items-center">
                      <i className="icon-doc" />
                      <span className="ml-2">Bank Statements</span>
                    </div>
                    <div>
                      <Button
                        color="primary"
                        className="btn-square"
                        onClick={() => this.props.history.push(`/admin/bank-account/update`)}
                      >
                        <i className="fas fa-plus mr-1" />
                        New Statement
                      </Button>
                    </div>
                  </div>
                </Col>
              </Row>
            </CardHeader>
            <CardBody>
              {/* <Row>
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
                        className="bank-statement-table"
                      >
                        <TableHeaderColumn isKey dataField="bankAccountName">No</TableHeaderColumn>
                        <TableHeaderColumn dataField="accountNumber">Transaction Type</TableHeaderColumn>
                        <TableHeaderColumn dataField="swiftCode" >Amount</TableHeaderColumn>
                        <TableHeaderColumn dataFormat={this.setStatus} >Reference Number</TableHeaderColumn>
                        <TableHeaderColumn dataField="openingBalance" >Description</TableHeaderColumn>
                        <TableHeaderColumn dataField="openingBalance">Transaction Date</TableHeaderColumn>
                        <TableHeaderColumn className="text-right" dataFormat={this.bankAccounttActions}>Action</TableHeaderColumn>
                      </BootstrapTable>
                  }
                </Col>
              </Row> */}
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

export default BankStatementList


