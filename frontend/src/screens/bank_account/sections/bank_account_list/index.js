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
    }

    this.selectRowProp = {
      mode: 'checkbox',
      bgColor: 'rgba(0,0,0, 0.05)',
      clickToSelect: false,
      onSelect: this.onRowSelect,
      onSelectAll: this.onSelectAll
    }

    this.toggleDangerModal = this.toggleDangerModal.bind(this)
    this.renderAccountName = this.renderAccountName.bind(this)
    this.renderAccountType = this.renderAccountType.bind(this)
    this.onRowSelect = this.onRowSelect.bind(this)
    this.onSelectAll = this.onSelectAll.bind(this)

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

  renderAccountName (cell, row) {
    return (
      <label
        className="text-primary my-link mb-0"
        onClick={() => this.props.history.push('/admin/bank-account/detail')}
      >
        { row.account_name }
      </label>
    )
  }

  renderAccountType (cell, row) {
    return (
      <label className="badge badge-primary mb-0">{ row.account_type }</label>
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
      <div className="bank-account-section">
        <div className="animated">
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
                            onClick={() => this.props.history.push(`/admin/bank-account/create`)}
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
                            <Input type="text" placeholder="Account Name" />
                          </FormGroup>
                          <FormGroup className="pr-3">
                            <Input type="text" placeholder="Account Number" />
                          </FormGroup>
                          <FormGroup className="pr-3">
                            <Input type="text" placeholder="Bank Name" />
                          </FormGroup>
                          <FormGroup className="pr-3">
                            <Input type="text" placeholder="Swift Code" />
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
                          data={bank_account_list}
                          version="4"
                          hover
                          pagination
                          totalSize={bank_account_list ? bank_account_list.length : 0}
                          className="bank-account-table"
                        >
                          <TableHeaderColumn
                            dataFormat={this.renderAccountName}
                          >
                            Account Name
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            isKey
                            dataField="account_number"
                          >
                            Account Number
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataFormat={this.renderAccountType}
                          >
                            Account Type
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="bank_name"
                          >
                            Bank Name
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="IBAN_number"
                          >
                            IBAN Number
                          </TableHeaderColumn>

                          <TableHeaderColumn
                            dataField="swift_code"
                          >
                            Swift Code
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="currency"
                          >
                            Currency
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="opening_balance"
                          >
                            Opening Balance
                          </TableHeaderColumn>
                          <TableHeaderColumn
                            dataField="country"
                          >
                            Country
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

export default BankAccountList


