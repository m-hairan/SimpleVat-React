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
  Input,
  Form,
  Collapse
} from "reactstrap";
import "react-bootstrap-table/dist/react-bootstrap-table-all.min.css";
import _ from "lodash";
import "react-toastify/dist/ReactToastify.css";

class CreateOrEditUsers extends Component {
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
          <CardHeader>New User</CardHeader>
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
                    <CardHeader>Basic Detail</CardHeader>
                    <CardBody>
                      <Row className="row-wrapper">
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="text-input">First Name</Label>
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
                            <Label htmlFor="text-input">Last Name</Label>
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
                            <Label htmlFor="text-input">Email Id</Label>
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
                            <Label htmlFor="text-input">Date Of Birth</Label>
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
                            <Label htmlFor="text-input">Role</Label>
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
                            <Label htmlFor="text-input">Status</Label>
                            <Row className="row-wrapper">
                              <FormGroup check inline>
                                <Input
                                  className="form-check-input"
                                  type="radio"
                                  id="inline-radio1"
                                  name="inline-radios"
                                  value="option1"
                                />
                                <Label
                                  className="form-check-label"
                                  check
                                  htmlFor="inline-radio1"
                                >
                                  Active
                                </Label>
                              </FormGroup>
                              <FormGroup check inline>
                                <Input
                                  className="form-check-input"
                                  type="radio"
                                  id="inline-radio2"
                                  name="inline-radios"
                                  value="option2"
                                />
                                <Label
                                  className="form-check-label"
                                  check
                                  htmlFor="inline-radio2"
                                >
                                  Inactive
                                </Label>
                              </FormGroup>
                            </Row>
                          </FormGroup>
                        </Col>
                      </Row>
                    </CardBody>
                  </Card>
                  <Card>
                    <CardHeader>
                      Password
                      <div className="card-header-actions">
                        <Button
                          color="link"
                          className="card-header-action btn-minimize"
                          data-target="#collapseExample"
                          onClick={this.toggle}
                        >
                          <i className="icon-arrow-up"></i>
                        </Button>
                      </div>
                    </CardHeader>
                    <Collapse isOpen={this.state.collapse} id="collapseExample">
                      <CardBody>
                        <Row className="row-wrapper">
                          <Col md="4">
                            <FormGroup>
                              <Label htmlFor="text-input">
                                Password
                              </Label>
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
                              <Label htmlFor="text-input">
                                Confirm Password
                              </Label>
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
                    </Collapse>
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

export default CreateOrEditUsers;
