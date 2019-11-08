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
  Label,
  ButtonGroup
} from 'reactstrap'
import Select from 'react-select'
import { BootstrapTable, TableHeaderColumn, SearchField } from 'react-bootstrap-table'

import * as ImportsActions from './actions'

import './style.scss'


const mapStateToProps = (state) => {
  return ({
    import_list: state.imports.import_list
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    importsActions: bindActionCreators(ImportsActions, dispatch)
  })
}


class Imports extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
    }

    this.options = {
    }

    this.renderTransactionStatus = this.renderTransactionStatus.bind(this)
  }

  componentDidMount () {
    this.props.importsActions.getImportList()
  }

  renderTransactionStatus (cell, row) {
    return (
      <span className="badge badge-success mb-0">Explained</span>
    )
  }

  render() {

    const { import_list } = this.props

    return (
      <div className="imports-screen">
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
                            <i className="nav-icon fas fa-file-import" />
                            <span className="ml-2">Import Transaction File</span>
                          </div>
                        </div>
                        <div className="filter-box p-2">
                          <Form onSubmit={this.handleSubmit} name="simpleForm">
                            <div className="flex-wrap d-flex"> 
                              <FormGroup>
                                <Label htmlFor="date_format">Bank Account:</Label>
                                <div className="status-option">
                                  <Select
                                    id="date_format"
                                    name="date_format"
                                    options={[]}
                                  />
                                </div>
                              </FormGroup>
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
                      <div className="mb-3">
                        <ButtonGroup size="sm">
                          <Button
                            color="primary"
                            className="btn-square"
                          >
                            <i className="fa glyphicon glyphicon-export fa-upload mr-1" />
                            Upload File (.csv)
                          </Button>
                          <Button
                            color="success"
                            className="btn-square"
                          >
                            <i className="fa glyphicon glyphicon-export fa-download mr-1" />
                            Download Sample File
                          </Button>
                        </ButtonGroup>
                      </div>
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
                        options={ this.options }
                        data={import_list}
                        search={true}
                        version="4"
                        hover
                        pagination
                        totalSize={ import_list ? import_list.length : 0}
                        className="imports-table"
                      >
                        <TableHeaderColumn
                          isKey
                          dataField="status"
                          dataFormat={this.renderTransactionStatus}
                        >
                          Status
                        </TableHeaderColumn>
                        <TableHeaderColumn
                          dataField="transactionType"
                        >
                          Transaction Date
                        </TableHeaderColumn>
                        <TableHeaderColumn
                          dataField="transactionCategoryCode"
                        >
                          Debit Amount
                        </TableHeaderColumn>
                        <TableHeaderColumn
                          dataField="parentTransactionCategory"
                        >
                          Description
                        </TableHeaderColumn>
                        <TableHeaderColumn
                          dataField="transactionCategoryId"
                        >
                          Credit Amount
                        </TableHeaderColumn>
                      </BootstrapTable>
                    </Col>
                  </Row>
                  <div className="mt-5 text-right">
                    <Button color="primary" className="btn-square mb-2 mr-2">
                      <i className="fa glyphicon glyphicon-export fa-save"></i> Import
                    </Button>
                  </div>
                </CardBody>
              </Card>
            </Col>
          </Row>
        </div>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Imports)
