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

import './style.scss'

const mapStateToProps = (state) => {
  return ({
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
  })
}

class CreatePayment extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      loading: false,
      data: [
        {},
        {}
      ]
    }

    this.options = {
    }

    this.renderActions = this.renderActions.bind(this)
    this.renderProductName = this.renderProductName.bind(this)
    this.renderQuantity = this.renderQuantity.bind(this)
    this.renderUnitPrice = this.renderUnitPrice.bind(this)
    this.renderVat = this.renderVat.bind(this)
    this.renderSubTotal = this.renderSubTotal.bind(this)

  }

  renderActions (cell, row) {
    return (
      <Button size="sm" className="btn-twitter btn-brand icon"><i className="fas fa-trash"></i></Button>
    )
  }

  renderProductName (cell, row) {
    return (
      <Input
        type="text"
      />
    )
  }

  renderQuantity (cell, row) {
    return (
      <Input
        type="text"
        value="0"
      />
    )
  }

  renderUnitPrice (cell, row) {
    return (
      <Input
        type="text"
        value="0.00"
      />
    )
  }

  renderVat (cell, row) {
    return (
      <Select
        options={[]}
        id="currency"
        name="currency"
      />
    )
  }

  renderSubTotal (cell, row) {

  }

  render() {

    const { data } = this.state

    return (
      <div className="create-payment-screen">
        <div className="animated fadeIn">
          <Row>
            <Col lg={12} className="mx-auto">
              <Card>
                <CardHeader>
                  <Row>
                    <Col lg={12}>
                      <div className="h4 mb-0 d-flex align-items-center">
                        <i className="fas fa-money-check" />
                        <span className="ml-2">Create Payment</span>
                      </div>
                    </Col>
                  </Row>
                </CardHeader>
                <CardBody>
                  <Row>
                    <Col lg={12}>
                      <Form>
                        <Row>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="claimant">Claimant</Label>
                              <Input
                                type="text"
                                id="claimant"
                                name="claimant"
                                placeholder="Enter Claimant"
                                required
                              />
                            </FormGroup>
                          </Col>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="category">Category</Label>
                              <Select
                                options={[]}
                                id="category"
                                name="category"
                              />
                            </FormGroup>
                          </Col>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="payment_date">Payment Date</Label>
                              <Input
                                type="text"
                                id="payment_date"
                                name="payment_date"
                                placeholder="Enter Payment Date"
                                required
                              />
                            </FormGroup>
                          </Col>
                        </Row>
                        <Row>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="currency">Currency</Label>
                              <Select
                                options={[]}
                                id="currency"
                                name="currency"
                              />
                            </FormGroup>
                          </Col>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="project">Project</Label>
                              <Select
                                options={[]}
                                id="project"
                                name="project"
                              />
                            </FormGroup>
                          </Col>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="payment_due">Payment Due</Label>
                              <Input
                                type="text"
                                id="payment_due"
                                name="payment_due"
                                placeholder="Enter Payment Due"
                                required
                              />
                            </FormGroup>
                          </Col>
                        </Row>
                        <Row>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="contact">Contact</Label>
                              <Select
                                options={[]}
                                id="contact"
                                name="contact"
                              />
                            </FormGroup>
                          </Col>
                        </Row>
                        <Row>
                          <Col lg={8}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="description">Description</Label>
                              <Input
                                type="textarea"
                                name="description"
                                id="description"
                                rows="9"
                                placeholder="Description..."
                              />
                            </FormGroup>
                          </Col>
                        </Row>
                        <hr/>
                        <Row>
                          <Col lg={8}>
                            <Row>
                              <Col lg={6}>
                                <FormGroup className="mb-3">
                                  <Label htmlFor="reciept_number">Reciept Number</Label>
                                  <Input
                                    type="text"
                                    id="reciept_number"
                                    name="reciept_number"
                                    placeholder="Enter Reciept Number"
                                    required
                                  />
                                </FormGroup>
                              </Col>
                            </Row>
                            <Row>
                              <Col lg={12}>
                                <FormGroup className="mb-3">
                                  <Label htmlFor="attachment_description">Attachment Description</Label>
                                  <Input
                                    type="textarea"
                                    name="attachment_description"
                                    id="attachment_description"
                                    rows="5"
                                    placeholder="Description..."
                                  />
                                </FormGroup>
                              </Col>
                            </Row>
                          </Col>
                          <Col lg={4}>
                            <Row>
                              <Col lg={12}>
                                <FormGroup className="mb-3">
                                  <Label>Reciept Attachment</Label><br/>
                                  <Button color="primary" className="btn-square mr-3">
                                    <i className="fa fa-upload"></i> Upload
                                  </Button>
                                </FormGroup>
                              </Col>
                            </Row>
                          </Col>
                        </Row>
                        <hr/>
                        <Row>
                          <Col lg={12} className="mb-3">
                            <Button color="primary" className="btn-square mr-3">
                              <i className="fa fa-plus"></i> Add More
                            </Button>
                          </Col>
                        </Row>
                        <Row>
                          <Col lg={12}>
                            <BootstrapTable
                              options={ this.options }
                              data={data}
                              version="4"
                              hover
                              className="payment-create-table"
                            >
                              <TableHeaderColumn
                                width="55"
                                dataAlign="center"
                                dataFormat={this.renderActions}
                              >
                              </TableHeaderColumn>
                              <TableHeaderColumn
                                isKey
                                dataField="product_name"
                                dataFormat={this.renderProductName}
                              >
                                Product
                              </TableHeaderColumn>
                              <TableHeaderColumn
                                dataField="quantity"
                                dataFormat={this.renderQuantity}
                              >
                                Quantity
                              </TableHeaderColumn>
                              <TableHeaderColumn
                                dataField="unit_price"
                                dataFormat={this.renderUnitPrice}
                              >
                                Unit Price (All)
                              </TableHeaderColumn>
                              <TableHeaderColumn
                                dataField="vat"
                                dataFormat={this.renderVat}
                              >
                                Vat (%)
                              </TableHeaderColumn>
                              <TableHeaderColumn
                                dataField="sub_total"
                                dataFormat={this.renderSubTotal}
                              >
                                Sub Total (All)
                              </TableHeaderColumn>
                            </BootstrapTable>
                          </Col>
                        </Row>
                      </Form>
                    </Col>
                  </Row>
                  <Row>
                    <Col lg={4} className="ml-auto">
                      <div className="mb-5">
                        <div className="total-item p-3">
                          <Row>
                            <Col lg={6}>
                              <h5 className="mb-0 text-right">Total Net</h5>
                            </Col>
                            <Col lg={6} className="text-right">
                              <label className="mb-0">0.00</label>
                            </Col>
                          </Row>
                        </div>
                        <div className="total-item p-3">
                          <Row>
                            <Col lg={6}>
                              <h5 className="mb-0 text-right">Total Vat</h5>
                            </Col>
                            <Col lg={6} className="text-right">
                              <label className="mb-0">0.00</label>
                            </Col>
                          </Row>
                        </div>
                        <div className="total-item p-3">
                          <Row>
                            <Col lg={6}>
                              <h5 className="mb-0 text-right">Total</h5>
                            </Col>
                            <Col lg={6} className="text-right">
                              <label className="mb-0">0.00</label>
                            </Col>
                          </Row>
                        </div>
                      </div>
                    </Col>
                  </Row>
                  <Row>
                    <Col lg={12}>
                      <FormGroup className="text-right">
                        <Button type="submit" color="primary" className="btn-square mr-3">
                          <i className="fa fa-dot-circle-o"></i> Create
                        </Button>
                        <Button type="submit" color="primary" className="btn-square mr-3">
                          <i className="fa fa-repeat"></i> Create and More
                        </Button>
                        <Button color="secondary" className="btn-square" 
                          onClick={() => {this.props.history.push('/admin/expense/payment')}}>
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

export default connect(mapStateToProps, mapDispatchToProps)(CreatePayment)
