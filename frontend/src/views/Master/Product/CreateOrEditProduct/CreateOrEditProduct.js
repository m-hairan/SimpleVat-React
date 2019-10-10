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
  Modal,
  ModalBody,
  ModalFooter,
  ModalHeader,
  Form,
} from "reactstrap";
import "react-bootstrap-table/dist/react-bootstrap-table-all.min.css";
import _ from "lodash";
import "react-toastify/dist/ReactToastify.css";
import Autosuggest from "react-autosuggest";

class CreateOrEditProduct extends Component {
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
    const { value, suggestions } = this.state;

    const inputProps = {
      value,
      onChange: this.onChange
    };
    return (
      <div className="animated fadeIn">
        <Card>
          <CardHeader>New Product And Service</CardHeader>
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
                    <CardHeader>Product And Service Details</CardHeader>
                    <CardBody>
                      <Row className="row-wrapper">
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="text-input">Name</Label>
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
                            <Label htmlFor="select">Product Code</Label>
                            <Autosuggest
                              suggestions={suggestions}
                              onSuggestionsFetchRequested={
                                this.onSuggestionsFetchRequested
                              }
                              onSuggestionsClearRequested={
                                this.onSuggestionsClearRequested
                              }
                              getSuggestionValue={this.getSuggestionValue}
                              renderSuggestion={this.renderSuggestion}
                              inputProps={inputProps}
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Parent Code</Label>
                            <Autosuggest
                              suggestions={suggestions}
                              onSuggestionsFetchRequested={
                                this.onSuggestionsFetchRequested
                              }
                              onSuggestionsClearRequested={
                                this.onSuggestionsClearRequested
                              }
                              getSuggestionValue={this.getSuggestionValue}
                              renderSuggestion={this.renderSuggestion}
                              inputProps={inputProps}
                            />
                          </FormGroup>
                        </Col>
                      </Row>
                      <Row>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Product Price</Label>
                            <Input
                              type="select"
                              name="select"
                              id="select"
                              required
                            >
                              <option value="0">Please select</option>
                              <option value="1">Option #1</option>
                              <option value="2">Option #2</option>
                              <option value="3">Option #3</option>
                            </Input>
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Vat Percentage</Label>
                            <Input
                              type="text"
                              name="select"
                              id="select"
                              required
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Input
                              className="form-check-input vat-includeInput"
                              type="checkbox"
                              id="checkbox1"
                              name="checkbox1"
                              value="option1"
                            />
                            <Label
                              check
                              className="form-check-label vat-includeLable"
                              htmlFor="checkbox1"
                            >
                              Vat Include
                            </Label>
                          </FormGroup>
                        </Col>
                      </Row>
                      <Row>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Description</Label>
                            <Input
                              type="textarea"
                              id="categoryDiscription"
                              name="categoryDiscription"
                              required
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Contact</Label>
                            <Input
                              type="select"
                              name="select"
                              id="select"
                              required
                            >
                              <option value="0">Please select</option>
                              <option value="1">Option #1</option>
                              <option value="2">Option #2</option>
                              <option value="3">Option #3</option>
                            </Input>
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <Button
                            size="sm"
                            color="primary"
                            onClick={this.toggleLarge}
                            className="mr-1 add-btn"
                          >
                            <i className="fas fa-plus"></i> Add
                          </Button>
                        </Col>
                      </Row>

                      <Modal
                        isOpen={this.state.large}
                        toggle={this.toggleLarge}
                        className={"modal-lg " + this.props.className}
                      >
                        <ModalHeader toggle={this.toggleLarge}>
                          New warehouse
                        </ModalHeader>
                        <ModalBody>
                          <Row className="row-wrapper">
                            <Col md="12">
                              <FormGroup>
                                <Label htmlFor="text-input">
                                  warehouse Name
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
                        </ModalBody>
                        <ModalFooter>
                          <Button color="primary" onClick={this.toggleLarge}>
                            Save
                          </Button>{" "}
                          <Button color="secondary" onClick={this.toggleLarge}>
                            Cancel
                          </Button>
                        </ModalFooter>
                      </Modal>
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

export default CreateOrEditProduct;
