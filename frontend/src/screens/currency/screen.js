import React from 'react'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'
import { Card, CardHeader, CardBody, Button, Modal, ModalHeader, 
        ModalBody, ModalFooter, Row, Input, ButtonGroup, Col, Form, 
        FormGroup, Label, Table} from 'reactstrap'
import { ToastContainer, toast } from 'react-toastify'
import { BootstrapTable, TableHeaderColumn, SearchField } from 'react-bootstrap-table'
import Loader from "components/loader"
import moment from 'moment'

import 'react-toastify/dist/ReactToastify.css'
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css'
import './style.scss'

import * as TransactionActions from './actions'

const mapStateToProps = (state) => {
  return ({
    transaction_list: state.transaction.transaction_list
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    transactionActions: bindActionCreators(TransactionActions, dispatch)
  })
}

class Currency extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      openCurrencyModal: false,
      loading: false,
      currencies: [
        {name: 'AED- UAE Dirham', symbol: 'AED'},
        {name: 'AUD- Australian Dollar', symbol: '$'},
      ]
    }

    this.selectRowProp = {
      mode: 'checkbox',
      bgColor: 'rgba(0,0,0, 0.05)',
      onSelect: this.onRowSelect,
      onSelectAll: this.onSelectAll
    }

    this.closeCurrencyModal = this.closeCurrencyModal.bind(this)
    this.showCurrencyModal = this.showCurrencyModal.bind(this)
    this.getCurrencyName = this.getCurrencyName.bind(this)
  }

  componentDidMount() {
  }

  // Show Invite User Modal
  showCurrencyModal() {
    this.setState({ openCurrencyModal: true })
  }
  // Cloase Confirm Modal
  closeCurrencyModal() {
    this.setState({ openCurrencyModal: false })
  }

  getCurrencyName(cell, row) {
    return(
          <label
            className="text-primary my-link mb-0"
            onClick={this.showCurrencyModal}
          >
            {row.name}
          </label>
        )
  }

  render() {
    const { loading, currencies, openCurrencyModal } = this.state;
    const containerStyle = {
      zIndex: 1999
    };

    return (
      <div className="transaction-category-screen">
        <div className="animated">
          <ToastContainer
            position="top-right"
            autoClose={5000}
            style={containerStyle}
          />

          <Card>
            <CardHeader>
              <div className="h4 mb-0 d-flex align-items-center">
                <i className="nav-icon fas fa-money" />
                <span className="ml-2">Currencies</span>
              </div>
            </CardHeader>
            <CardBody>
            {
              loading ?
                <Loader></Loader>: 
                <Row>
                  <Col lg='12'>
                    <div className="mb-3">
                        <ButtonGroup className="toolbar" size="sm">
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
                            onClick={this.showCurrencyModal}
                          >
                            <i className="fas fa-plus mr-1" />
                            New Currency
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
          
                        <BootstrapTable 
                          data={currencies} 
                          hover
                          pagination
                          version="4"
                          search={true}
                          selectRow={ this.selectRowProp }
                        >
                          <TableHeaderColumn isKey dataField="name" dataFormat={this.getCurrencyName}>
                            Currency Name
                          </TableHeaderColumn>
                          <TableHeaderColumn dataField="symbol">
                            Symbol
                          </TableHeaderColumn>
                        </BootstrapTable>
                  </Col>
                </Row>
            }
            </CardBody>
          </Card>

          <Modal isOpen={openCurrencyModal}
            className={"modal-success " + this.props.className}
          >
            <ModalHeader toggle={this.toggleDanger}>Create & Update Currency</ModalHeader>
            <ModalBody>
              <Form onSubmit={this.handleSubmit} name="simpleForm">
                <FormGroup>
                  <Label htmlFor="categoryCode">Currency Code</Label>
                  <select className="form-control" placeholder="User Role">
                    <option value='admin'>Option1</option>
                    <option value='employee'>Option2</option>
                    <option value='accountant'>Option3</option>
                  </select>
                </FormGroup>
                <FormGroup>
                  <Label htmlFor="categoryName">*Currency Name</Label>
                  <Input
                    type="text"
                    id="categoryName"
                    name="categoryName"
                    placeholder="Enter Name"
                    required
                  />
                </FormGroup>
                <FormGroup>
                  <Label htmlFor="categoryCode">*Symbol</Label>
                  <Input
                    type="text"
                    id="categoryCode"
                    name="categoryCode"
                    placeholder="Enter Symbol"
                    required
                  />
                </FormGroup>
                
              </Form>
            </ModalBody>
            <ModalFooter>
              <Button color="success" onClick={this.closeCurrencyModal}>Save</Button>&nbsp;
              <Button color="secondary"onClick={this.closeCurrencyModal}>Cancel</Button>
            </ModalFooter>
          </Modal>
        </div>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Currency)
