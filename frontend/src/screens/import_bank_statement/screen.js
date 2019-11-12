import React from 'react'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'
import {
  Card,
  CardHeader,
  CardBody,
  Button,
  Row,
  Col,
  Form,
  FormGroup,
  Input,
  Label
} from 'reactstrap'
import Select from 'react-select'
import { BootstrapTable, TableHeaderColumn, SearchField } from 'react-bootstrap-table'

import BankStatement from '../bank_statement'

import './style.scss'

const mapStateToProps = (state) => {
  return ({
    bank_statement_list: state.bank_statement.bank_statement_list
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    bankStatementActions: bindActionCreators(BankStatement.actions, dispatch)
  })
}

class ImportBankStatement extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      
    }

    this.options = {
    }

    this.initializeData = this.initializeData.bind(this)
    this.renderTransactionStatus = this.renderTransactionStatus.bind(this)

  }

  componentDidMount () {
    this.initializeData()
  }

  initializeData () {
    this.props.bankStatementActions.getBankStatementList()
  }

  renderTransactionStatus (cell, row) {
    return (
      <span className="badge badge-success mb-0">Explained</span>
    )
  }
  

  render() {

    const { bank_statement_list } = this.props

    return (
      <div className="import-bank-statement-screen">
        <div className="animated fadeIn">
          <Row>
            <Col lg={12} className="mx-auto">
              <Card>
                <CardHeader>
                  <Row>
                    <Col lg={12}>
                      <div className="d-flex flex-wrap align-items-start justify-content-between">
                        <div>
                          <div className="h4 card-title d-flex align-items-center">
                            <i className="fa glyphicon glyphicon-export fa-upload" />
                            <span className="ml-2">Import Bank Statements from CSV</span>
                          </div>
                        </div>
                        <div className="filter-box p-2">
                          <Form onSubmit={this.handleSubmit} name="simpleForm">
                            <div className="flex-wrap d-flex"> 
                              <FormGroup>
                                <Label htmlFor="date_format">Date Format:</Label>
                                <div className="status-option">
                                  <Select
                                    id="date_format"
                                    name="date_format"
                                    options={[]}
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
                  <Row>
                    <Col lg={12}>
                      <FormGroup>
                        <Button color="primary" size="sm" className="btn-square mb-2">
                          <i className="fa glyphicon glyphicon-export fa-upload"></i> Upload File (.csv)
                        </Button>
                      </FormGroup>
                    </Col>
                  </Row>
                  <Row>
                    <Col lg={12}>
                      <div className="mb-3">
                        <Form>
                          <Row>
                            <Col lg={4}>
                              <FormGroup className="mb-3">
                                <Label htmlFor="delimiter">Delimiter</Label>
                                <Input
                                  type="text"
                                  id="delimiter"
                                  name="delimiter"
                                  value=","
                                  placeholder=""
                                />
                              </FormGroup>
                            </Col>
                            <Col lg={4}>
                              <FormGroup className="mb-3">
                                <Label htmlFor="skip_rows">Skip Rows</Label>
                                <Input
                                  type="text"
                                  id="skip_rows"
                                  name="skip_rows"
                                  value="0"
                                  placeholder=""
                                />
                              </FormGroup>
                            </Col>
                          </Row>
                          <Row>
                            <Col lg={4}>
                              <FormGroup check inline className="">
                                <Input
                                  className="form-check-input"
                                  type="checkbox"
                                  id="header_row"
                                  name="header_row"
                                />
                                <Label className="form-check-label" check htmlFor="header_row">Header Row</Label>
                              </FormGroup>
                            </Col>
                          </Row>
                        </Form>
                      </div>
                    </Col>
                  </Row>
                  <Row>
                    <Col lg={12}>
                      <BootstrapTable
                        search={false}
                        options={ this.options }
                        data={bank_statement_list}
                        version="4"
                        hover
                        pagination
                        totalSize={ bank_statement_list ? bank_statement_list.length : 0}
                        className="upload-bank-statement-table"
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
                          dataField="transaction_type"
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
                    </Col>
                  </Row>
                  <Row>
                    <Col lg={12} className="mt-5">
                      <FormGroup className="text-right">
                        <Button type="submit" color="primary" className="btn-square mr-3">
                          <i className="fa fa-dot-circle-o"></i> Import
                        </Button>
                        <Button color="secondary" className="btn-square" 
                          onClick={() => this.props.history.push('/admin/bank/bank-statement')}>
                          <i className="fa fa-ban"></i> Cancel
                        </Button>
                      </FormGroup>
                    </Col>
                  </Row>
                </CardBody>
              </Card>
            </Col>
          </Row>
        </div>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(ImportBankStatement)
