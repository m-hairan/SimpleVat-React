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

import './style.scss'

const mapStateToProps = (state) => {
  return ({
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
  })
}

class CreateBankAccount extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      loading: false,

      account_type_list: [
        { id: 'Saving', name: 'Saving' },
        { id: 'Checking', name: 'Checking' },
        { id: 'Credit Card', name: 'Credit Card' },
        { id: 'Paypal', name: 'Paypal' },
        { id: 'Others', name: 'Others' },
      ]
    }

  }

  render() {

    return (
      <div className="create-bank-account-screen">
        <div className="animated">
          <Row>
            <Col lg={6} className="mx-auto">
              <Card>
                <CardHeader>
                  <Row>
                    <Col lg={12}>
                      <div className="h4 mb-0 d-flex align-items-center">
                        <i className="fas fa-university" />
                        <span className="ml-2">Create Bank Account</span>
                      </div>
                    </Col>
                  </Row>
                </CardHeader>
                <CardBody>
                  <Row>
                    <Col lg={12}>
                      <div className="px-5 py-3">
                        <Form>
                          <FormGroup className="mb-3">
                            <Label htmlFor="bank_name">Bank Name</Label>
                            <Input
                              type="text"
                              id="bank_name"
                              name="bank_name"
                              placeholder="Enter Bank Name"
                              required
                            />
                          </FormGroup>
                          <FormGroup className="mb-3">
                            <Label htmlFor="account_name">Account Name</Label>
                            <Input
                              type="text"
                              id="account_name"
                              name="account_name"
                              placeholder="Enter Account Name"
                              required
                            />
                          </FormGroup>
                          <FormGroup className="mb-3">
                            <Label htmlFor="account_number">Account Number</Label>
                            <Input
                              type="text"
                              id="account_number"
                              name="account_number"
                              placeholder="Enter Account Number"
                              required
                            />
                          </FormGroup>
                          <FormGroup className="mb-3">
                            <Label htmlFor="account_number">Account Number</Label>
                            <Input
                              type="text"
                              id="account_number"
                              name="account_number"
                              placeholder="Enter Account Number"
                              required
                            />
                          </FormGroup>
                          <FormGroup className="mb-3">
                            <Label htmlFor="account_type">
                              Account Type
                            </Label>
                            <Input
                              type="select"
                              name="account_type"
                              id="account_type"
                              required
                            >
                              {this.state.account_type_list.map((item, index) => (
                                <option key={index} value={item.id}>
                                  {item.name}
                                </option>
                              ))}
                            </Input>
                          </FormGroup>
                          <FormGroup className="mb-3">
                            <Label htmlFor="IBAN_number">IBAN Number</Label>
                            <Input
                              type="text"
                              id="IBAN_number"
                              name="IBAN_number"
                              placeholder="Enter IBAN Number"
                              required
                            />
                          </FormGroup>
                          <FormGroup className="mb-3">
                            <Label htmlFor="currency">Currency</Label>
                            <Input
                              type="text"
                              id="currency"
                              name="currency"
                              placeholder="Enter Currency"
                              required
                            />
                          </FormGroup>
                          <FormGroup className="mb-3">
                            <Label htmlFor="swift_code">Swift Code</Label>
                            <Input
                              type="text"
                              id="swift_code"
                              name="swift_code"
                              placeholder="Enter Swift Code"
                              required
                            />
                          </FormGroup>
                          <FormGroup className="mb-3">
                            <Label htmlFor="country">Country</Label>
                            <Input
                              type="text"
                              id="country"
                              name="country"
                              placeholder="Enter Country"
                              required
                            />
                          </FormGroup>
                          <FormGroup className="text-right">
                            <Button type="submit" color="primary" className="btn-square mr-3">
                              <i className="fa fa-dot-circle-o"></i> Create
                            </Button>
                            <Button type="submit" color="primary" className="btn-square mr-3">
                              <i className="fa fa-repeat"></i> Create and More
                            </Button>
                            <Button color="secondary" className="btn-square" 
                              onClick={() => {this.props.history.push("/admin/bank-account")}}>
                              <i className="fa fa-ban"></i> Cancel
                            </Button>
                          </FormGroup>
                        </Form>
                      </div>
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

export default connect(mapStateToProps, mapDispatchToProps)(CreateBankAccount)
