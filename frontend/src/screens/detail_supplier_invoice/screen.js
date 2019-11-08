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

class DetailSupplierInvoice extends React.Component {
  
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
    this.renderDescription = this.renderDescription.bind(this)
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
      <div className="d-flex align-items-center">
        <Select
          className="select-default-width flex-grow-1 mr-1"
          options={[]}
        />
        <Button
          size="sm"
          color="primary"
          className="btn-brand icon"
        >
          <i className="fas fa-plus"></i>
        </Button>
      </div>
    )
  }

  renderDescription (cell, row) {
    return (
      <Input
        type="text"
        value=""
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
        className="select-default-width"
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
      <div className="detail-supplier-invoice-screen">
        <div className="animated fadeIn">
          <Row>
            <Col lg={12} className="mx-auto">
              <Card>
                <CardHeader>
                  <Row>
                    <Col lg={12}>
                      <div className="h4 mb-0 d-flex align-items-center">
                        <i className="fas fa-address-book" />
                        <span className="ml-2">Update Invoice</span>
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
                              <Label htmlFor="reference_number">Invoice Reference Number</Label>
                              <Input
                                type="text"
                                id="reference_number"
                                name="reference_number"
                                placeholder=""
                                required
                              />
                            </FormGroup>
                          </Col>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="project">Project</Label>
                              <Select
                                className="select-default-width"
                                options={[]}
                                id="project"
                                name="project"
                              />
                            </FormGroup>
                          </Col>
                        </Row>
                        <Row>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="contact">Contact</Label>
                              <Select
                                className="select-default-width"
                                options={[]}
                                id="contact"
                                name="contact"
                              />
                            </FormGroup>
                          </Col>
                        </Row>
                        <Row>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Button color="primary" className="btn-square">
                                <i className="fa fa-plus"></i> Add a Contact
                              </Button>
                            </FormGroup>
                          </Col>
                        </Row>
                        <hr/>
                        <Row>
                          <Col lg={4}>
                            <FormGroup check inline className="mb-3">
                              <Input
                                className="form-check-input"
                                type="checkbox"
                                id="is_same_address"
                                name="is_same_address"
                              />
                              <Label className="form-check-label" check htmlFor="is_same_address">
                                Shipping Address is same as above address.
                              </Label>
                            </FormGroup>
                          </Col>
                        </Row>
                        <Row>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="contact">Shipping Contact</Label>
                              <Select
                                className="select-default-width"
                                options={[]}
                                id="contact"
                                name="contact"
                              />
                            </FormGroup>
                          </Col>
                        </Row>
                        <hr/>
                        <Row>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="date">Invoice Date</Label>
                              <Input
                                type="text"
                                id="date"
                                name="date"
                                placeholder=""
                                required
                              />
                            </FormGroup>
                          </Col>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="due_days">Invoice Due In Day(s)</Label>
                              <Input
                                type="text"
                                id="due_days"
                                name="due_days"
                                placeholder=""
                                required
                              />
                            </FormGroup>
                          </Col>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="due_date">Invoice Due Date</Label>
                              <Input
                                type="text"
                                id="due_date"
                                name="due_date"
                                placeholder=""
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
                                className="select-default-width"
                                options={[]}
                                id="currency"
                                name="currency"
                              />
                            </FormGroup>
                          </Col>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="contact_po_number">Contact PO Number</Label>
                              <Input
                                type="text"
                                id="contact_po_number"
                                name="contact_po_number"
                                placeholder=""
                                required
                              />
                            </FormGroup>
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
                              className="invoice-create-table"
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
                                dataField="description"
                                dataFormat={this.renderDescription}
                              >
                                Description
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
                        <Row>
                          <Col lg={8}>
                            <FormGroup className="py-3 mb-5">
                              <Label htmlFor="notes">Notes</Label>
                              <Input
                                type="textarea"
                                name="notes"
                                id="notes"
                                rows="6"
                                placeholder="notes..."
                              />
                            </FormGroup>
                          </Col>
                          <Col lg={4}>
                            <div className="mb-5">
                              <div className="total-item p-2">
                                <Row>
                                  <Col lg={6}>
                                  <FormGroup>
                                    <Label htmlFor="fixed_percentage">Fixed / Percentage Discount</Label>
                                    <Select
                                      className="select-default-width"
                                      options={[]}
                                      id="fixed_percentage"
                                      name="fixed_percentage"
                                    />
                                  </FormGroup>
                                  </Col>
                                </Row>
                              </div>
                              <div className="total-item p-2">
                                <Row>
                                  <Col lg={6}>
                                    <h5 className="mb-0 text-right">Total Net</h5>
                                  </Col>
                                  <Col lg={6} className="text-right">
                                    <label className="mb-0">0.00</label>
                                  </Col>
                                </Row>
                              </div>
                              <div className="total-item p-2">
                                <Row>
                                  <Col lg={6}>
                                    <h5 className="mb-0 text-right">Total Vat</h5>
                                  </Col>
                                  <Col lg={6} className="text-right">
                                    <label className="mb-0">0.00</label>
                                  </Col>
                                </Row>
                              </div>
                              <div className="total-item p-2">
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
                                <i className="fa fa-dot-circle-o"></i> Save
                              </Button>
                              <Button color="secondary" className="btn-square" 
                                onClick={() => {this.props.history.push('/admin/expense/supplier-invoice')}}>
                                <i className="fa fa-ban"></i> Cancel
                              </Button>
                            </FormGroup>
                          </Col>
                        </Row>
                      </Form>
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

export default connect(mapStateToProps, mapDispatchToProps)(DetailSupplierInvoice)
