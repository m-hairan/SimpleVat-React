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


import './style.scss'

const mapStateToProps = (state) => {
  return ({
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
  })
}

class DetailProject extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      loading: false,
    }

  }

  render() {

    return (
      <div className="create-product-screen">
        <div className="animated fadeIn">
          <Row>
            <Col lg={12} className="mx-auto">
              <Card>
                <CardHeader>
                  <Row>
                    <Col lg={12}>
                      <div className="h4 mb-0 d-flex align-items-center">
                        <i className="nav-icon fas fa-project-diagram" />
                        <span className="ml-2">Update Project</span>
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
                              <Label htmlFor="name">Project Name</Label>
                              <Input
                                type="text"
                                id="name"
                                name="name"
                                placeholder="Enter Project Name"
                                required
                              />
                            </FormGroup>
                          </Col>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="product_code">Contact</Label>
                              <Input
                                type="text"
                                id="product_code"
                                name="product_code"
                                placeholder="Enter Contact"
                                required
                              />
                            </FormGroup>
                            <FormGroup className="mb-5 text-right">
                              <Button color="primary" className="btn-square ">
                                <i className="fa fa-plus"></i> Add a Contact
                              </Button>
                            </FormGroup>
                          </Col>
                          
                        </Row>
                        <Row>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="product_price">Contract PO Number</Label>
                              <Input
                                type="text"
                                id="product_price"
                                name="product_price"
                                placeholder="Enter Contract PO Number"
                                required
                              />
                            </FormGroup>
                          </Col>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="product_price">VAT Registration Number</Label>
                              <Input
                                type="text"
                                id="product_price"
                                name="product_price"
                                placeholder="Enter VAT Registration Number"
                                required
                              />
                            </FormGroup>
                          </Col>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="vat_percentage">Currency</Label>
                              <Select
                                options={[]}
                                id="vat_percentage"
                                name="vat_percentage"
                              />
                            </FormGroup>
                          </Col>
                        </Row>
                        <Row>
                        <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="product_price">Expense Budget</Label>
                              <Input
                                type="text"
                                id="product_price"
                                name="product_price"
                                placeholder="Enter Expense Budgets"
                                required
                              />
                            </FormGroup>
                          </Col>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="product_price">Revenue Budget</Label>
                              <Input
                                type="text"
                                id="product_price"
                                name="product_price"
                                placeholder="Enter VAT Revenue Budget"
                                required
                              />
                            </FormGroup>
                          </Col>
                          <Col lg={4}>
                            <FormGroup className="mb-3">
                              <Label htmlFor="vat_percentage">Invoice Language</Label>
                              <Select
                                options={[]}
                                id="vat_percentage"
                                name="vat_percentage"
                              />
                            </FormGroup>
                          </Col>
                        </Row>
                        <Row>
                          <Col lg={12}>
                            <FormGroup className="text-right">
                              <Button type="submit" color="primary" className="btn-square mr-3">
                                <i className="fa fa-dot-circle-o"></i> Update
                              </Button>
                              <Button color="secondary" className="btn-square" 
                                onClick={() => {this.props.history.push('/admin/master/project')}}>
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

export default connect(mapStateToProps, mapDispatchToProps)(DetailProject)
