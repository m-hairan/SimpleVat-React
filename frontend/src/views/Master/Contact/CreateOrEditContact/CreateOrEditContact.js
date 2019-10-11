import React, { Component } from "react";
import {
  Card,
  CardHeader,
  CardBody,
  Button,
  Row,
  Col,
  FormGroup,
  Label,
  Form,
  Input
} from "reactstrap";
import "react-bootstrap-table/dist/react-bootstrap-table-all.min.css";
import _ from "lodash";
import "react-toastify/dist/ReactToastify.css";

class CreateOrEditContact extends Component {
  constructor(props) {
    super(props);

    this.state = {
      value: "",
      suggestions: [],
      projectList: [],
      vatCategoryList: [],
      transactionData: {},
      collapse: true,
      loading: false,
      large: false
    };
    this.toggleLarge = this.toggleLarge.bind(this);
  }

  toggleLarge() {
    this.setState({
      large: !this.state.large
    });
  }

  getSuggestions = value => {
    const inputValue = value.trim().toLowerCase();
    const inputLength = inputValue.length;

    return inputLength === 0
      ? []
      : this.state.projectList.filter(
          lang => lang.name.toLowerCase().slice(0, inputLength) === inputValue
        );
  };

  getSuggestionValue = suggestion => suggestion.name;

  renderSuggestion = suggestion => <div>{suggestion.name}</div>;


  handleChange = (e, name) => {
    this.setState({
      transactionData: _.set(
        { ...this.state.transactionData },
        e.target.name && e.target.name !== "" ? e.target.name : name,
        e.target.type === "checkbox" ? e.target.checked : e.target.value
      )
    });
  };


  
  toggle = () => {
    this.setState({ collapse: !this.state.collapse });
  };

  onChange = (event, { newValue }) => {
    this.setState({
      value: newValue
    });
  };
  onSuggestionsFetchRequested = ({ value }) => {
    this.setState({
      suggestions: this.getSuggestions(value)
    });
  };

  onSuggestionsClearRequested = () => {
    this.setState({
      suggestions: []
    });
  };

  render() {
   
    return (
      <div className="animated fadeIn">
        <Card>
          <CardHeader>New Contact</CardHeader>
          <div className="create-bank-wrapper">
            <Row>
              <Col xs="12">
                <Form
                  action=""
                  method="post"
                  encType="multipart/form-data"
                  className="form-horizontal"
                >
                  <Card>
                    <CardHeader>Contact Name</CardHeader>
                    <CardBody>
                      <Row className="row-wrapper">
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Refrence Code</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Type</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                      </Row>
                      <Row className="row-wrapper">
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Full Name</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Middle Name</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Last Name</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                      </Row>
                    </CardBody>
                  </Card>
                  <Card>
                    <CardHeader>Contact Details</CardHeader>
                    <CardBody>
                      <Row className="row-wrapper">
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Contact Type</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Organization Name</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Po Box Number</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                      </Row>
                      <Row className="row-wrapper">
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Email</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Telephone</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Mobile Number</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                      </Row>
                      <Row className="row-wrapper">
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Address Line1</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Address Line2</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Address Line3</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                      </Row>
                      <Row className="row-wrapper">
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Country</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">State Region</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">City</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                      </Row>
                      <Row className="row-wrapper">
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Post Zip Code</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                      </Row>
                    </CardBody>
                  </Card>
                  <Card>
                    <CardHeader>Invoicing Details</CardHeader>
                    <CardBody>
                      <Row className="row-wrapper">
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Billing Email</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Contract Po Number</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                      </Row>
                      <Row className="row-wrapper">
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Vat Registration Number</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Currency Code</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              required
                            />
                          </FormGroup>
                        </Col>
                      </Row>
                    </CardBody>
                  </Card>

                  <Row className="bank-btn-wrapper">
                    <FormGroup>
                      <Button
                        type="submit"
                        className="submit-invoice"
                        size="sm"
                        color="primary"
                      >
                        <i className="fa fa-dot-circle-o "></i> Submit
                      </Button>
                      <Button type="submit" size="sm" color="primary">
                        <i className="fa fa-dot-circle-o"></i> Submit
                      </Button>
                    </FormGroup>
                  </Row>
                </Form>
              </Col>
            </Row>
          </div>
        </Card>
      </div>
    );
  }
}

export default CreateOrEditContact;
