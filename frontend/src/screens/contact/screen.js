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
  Input
} from 'reactstrap'
import { ToastContainer, toast } from 'react-toastify'
import { BootstrapTable, TableHeaderColumn, SearchField } from 'react-bootstrap-table'

import Loader from 'components/loader'

import 'react-toastify/dist/ReactToastify.css'
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css'

import * as ContactActions from './actions'

import './style.scss'

const mapStateToProps = (state) => {
  return ({
    contact_list: state.contact.contact_list
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    ContactActions: bindActionCreators(ContactActions, dispatch)
  })
}

class Contact extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      loading: false,
      clickedRow: {}
    }

    this.options = {
      onRowClick: this.onRowClick
    }

    this.selectRowProp = {
      mode: 'checkbox',
      bgColor: 'rgba(0,0,0, 0.05)',
      clickToSelect: false,
      onSelect: this.onRowSelect,
      onSelectAll: this.onSelectAll
    }

    this.renderUserName = this.renderUserName.bind(this)
    this.onRowSelect = this.onRowSelect.bind(this)
    this.onSelectAll = this.onSelectAll.bind(this)
    this.onRowClick = this.onRowClick.bind(this)
  }

  componentDidMount () {
    this.initializeData()
  }

  initializeData () {
    this.props.ContactActions.getContactList()
  }

  renderUserName (cell, row) {
    return (
      <label
        className="text-primary my-link mb-0"
        onClick={() => this.props.history.push('/admin/master/contact/detail')}
      >
        { row.transactionCategoryName }
      </label>
    )
  }

  onRowSelect (row, isSelected, e) {
    console.log('one row checked ++++++++', row)
  }
  onSelectAll (isSelected, rows) {
    console.log('current page all row checked ++++++++', rows)
  }

  onRowClick(row) {
    console.log(row)
  }

  render() {

    const { loading } = this.state
    const { contact_list } = this.props
    const containerStyle = {
      zIndex: 1999
    }

    return (
      <div className="contact-screen">
        <div className="animated fadeIn">
          <ToastContainer position="top-right" autoClose={5000} style={containerStyle} />
          <Card>
            <CardHeader>
              <Row>
                <Col lg={12}>
                  <div className="h4 mb-0 d-flex align-items-center">
                    <i className="nav-icon fas fa-id-card-alt" />
                    <span className="ml-2">Contact</span>
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
                          >
                            <i className="fa glyphicon glyphicon-export fa-upload mr-1" />
                            Import from CSV
                          </Button>
                          <Button
                            color="primary"
                            className="btn-square"
                            onClick={() => this.props.history.push(`/admin/master/contact/create`)}
                          >
                            <i className="fas fa-plus mr-1" />
                            New Contact
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
                            <Input type="text" placeholder="User Name" />
                          </FormGroup>
                          <FormGroup className="pr-3">
                            <Input type="text" placeholder="Email" />
                          </FormGroup>
                          <FormGroup className="pr-3">
                            <select className="form-control">
                              <option value="vendor">All</option>
                              <option value="customer">Customer</option>
                              <option value="vendor">Vendor</option>
                            </select>
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
                        <Row>
                          <Col xs="12" lg="8">
                            <BootstrapTable
                              selectRow={ this.selectRowProp }
                              search={true}
                              options={ this.options }
                              data={contact_list}
                              version="4"
                              hover
                              pagination
                              totalSize={contact_list ? contact_list.length : 0}
                              className="product-table"
                            >
                              <TableHeaderColumn
                                isKey
                                dataField="transactionCategoryName"
                                dataFormat={this.renderUserName}
                              >
                                Name
                              </TableHeaderColumn>
                              <TableHeaderColumn
                                dataField="transactionCategoryCode"
                              >
                                Email
                              </TableHeaderColumn>
                              <TableHeaderColumn
                                dataField="parentTransactionCategory"
                              >
                                Type
                              </TableHeaderColumn>
                            </BootstrapTable>
                          </Col>
                          <Col xs="12" lg="4">
                            <div className="contact-info mt-5 p-4">
                              <h4>Mr. Admin Admin</h4>
                              <hr/>
                              <div className="d-flex">
                                <div className="contact-name mr-4">AA</div>
                                <div className="info">
                                    <p><strong>Company: </strong> admin </p>
                                    <p><strong>Email: </strong> admin@admin.com </p>
                                    <p><strong>Tel No: </strong> 1231 </p>
                                    <p><strong>Next Due Date: </strong> 11/01/2019 </p>
                                    <p><strong>Due Amount: </strong> Lek 400 </p>
                                    <p><strong>Contact Type: </strong>Customer </p>
                                </div>
                                
                              </div>
                              <div className="text-right mt-3">
                                <Button
                                  color="primary"
                                  className="btn-square "
                                  onClick={() => this.props.history.push(`/admin/invoice/create`)}
                                >
                                  <i className="fas fa-plus mr-1" />
                                  New Invoice
                                </Button>
                              </div>
                            </div>
                          </Col>
                        </Row>
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

export default connect(mapStateToProps, mapDispatchToProps)(Contact)
