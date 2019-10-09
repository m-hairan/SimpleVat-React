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
  Collapse
} from "reactstrap";
import "react-bootstrap-table/dist/react-bootstrap-table-all.min.css";
// import sendRequest from "../../../xhrRequest";
import _ from "lodash";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Autosuggest from 'react-autosuggest';

class CreateOrEditPurchase extends Component {
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

  // componentDidMount() {
  //   this.getTransactionListData();
  //   this.getvatListData();
  //   const params = new URLSearchParams(this.props.location.search);
  //   const id = params.get("id");
  //   if (id) {
  //     this.setState({ loading: true });
  //     const res = sendRequest(
  //       `/transaction/edittransactioncategory?id=${id}`,
  //       "get",
  //       ""
  //     );
  //     res
  //       .then(res => {
  //         if (res.status === 200) {
  //           this.setState({ loading: false });
  //           return res.json();
  //         }
  //       })
  //       .then(data => {
  //         this.setState({ transactionData: data });
  //       });
  //   }
  // }

  // Teach Autosuggest how to calculate suggestions for any given input value.
  getSuggestions = value => {
    const inputValue = value.trim().toLowerCase();
    const inputLength = inputValue.length;

    return inputLength === 0
      ? []
      : this.state.projectList.filter(
        lang => lang.name.toLowerCase().slice(0, inputLength) === inputValue
      );
  };

  // When suggestion is clicked, Autosuggest needs to populate the input
  // based on the clicked suggestion. Teach Autosuggest how to calculate the
  // input value for every given suggestion.
  getSuggestionValue = suggestion => suggestion.name;

  // Use your imagination to render suggestions.
  renderSuggestion = suggestion => <div>{suggestion.name}</div>;

  // getTransactionListData = () => {
  //   const res = sendRequest(
  //     `transaction/gettransactioncategory`,
  //     "get",
  //     "",
  //     ""
  //   );
  //   res
  //     .then(res => {
  //       if (res.status === 200) {
  //         this.setState({ loading: false });
  //         return res.json();
  //       }
  //     })
  //     .then(data => {
  //       this.setState({ transactionCategoryList: data });
  //     });
  // };
  // getvatListData = () => {
  //   const res = sendRequest(`/transaction/getvatcategories`, "get", "", "");
  //   res
  //     .then(res => {
  //       if (res.status === 200) {
  //         this.setState({ loading: false });
  //         return res.json();
  //       }
  //     })
  //     .then(data => {
  //       this.setState({ vatCategoryList: data });
  //     });
  // };

  handleChange = (e, name) => {
    this.setState({
      transactionData: _.set(
        { ...this.state.transactionData },
        e.target.name && e.target.name !== "" ? e.target.name : name,
        e.target.type === "checkbox" ? e.target.checked : e.target.value
      )
    });
  };

  success = () => {
    return toast.success("Transaction Category Updated successfully... ", {
      position: toast.POSITION.TOP_RIGHT
    });
  };

  handleSubmit = e => {
    this.setState({ loading: true });
    e.preventDefault();
    const {
      categoryName,
      categoryCode,
      categoryDiscription,
      selectCategoryCode,
      selectTransactionType
    } = this.state.transactionData;
    const postObj = {
      categoryName: categoryName,
      categoryCode: categoryCode,
      categoryDiscription: categoryDiscription,
      selectCategoryCode: selectCategoryCode,
      selectTransactionType: selectTransactionType
    };
    console.log(postObj);
    //  const res = sendRequest(
    //   `/transaction/savetransaction?id=1`,
    //   "post",
    //   "",
    //   postObj
    // );
    // res.then(res => {
    //   if (res.status === 200) {
    //     this.success();
    //     this.props.history.push("settings/transaction-category");
    //   }
    // });
  };

  toggle = () => {
    this.setState({ collapse: !this.state.collapse });
  };

  onChange = (event, { newValue }) => {
    this.setState({
      value: newValue
    });
  };
  // Autosuggest will call this function every time you need to update suggestions.
  // You already implemented this logic above, so just use it.
  onSuggestionsFetchRequested = ({ value }) => {
    this.setState({
      suggestions: this.getSuggestions(value)
    });
  };

  // Autosuggest will call this function every time you need to clear suggestions.
  onSuggestionsClearRequested = () => {
    this.setState({
      suggestions: []
    });
  };

  render() {
    const { loading } = this.state;
    const { id, name, vat } = this.state.transactionData
      ? this.state.transactionData
      : {};

    const { value, suggestions } = this.state;

    // Autosuggest will pass through all these props to the input.
    const inputProps = {
      value,
      onChange: this.onChange
    };
    return (
      <div className="animated fadeIn">
        <Card>
          <CardHeader>{id ? "Edit New Purchase" : "New Purchase"}</CardHeader>
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
                    <CardHeader>Purchase Details</CardHeader>
                    <CardBody>
                      <Row className="row-wrapper">
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="text-input">Cliement</Label>
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
                            <Label htmlFor="select">Category</Label>
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
                            <Label htmlFor="select">Purchase Date</Label>
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
                            <Label htmlFor="select">Currency</Label>
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
                            <Label htmlFor="select">Project</Label>
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
                            <Label htmlFor="select">Purchase Date</Label>
                            <Input
                              type="text"
                              name="select"
                              id="select"
                              required
                            />

                          </FormGroup>
                        </Col>
                      </Row>
                      <Row>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Contact</Label>
                            <Input
                              type="text"
                              id="contact"
                              name="contact"
                              required
                            />
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
                      <Row>
                      <Col md="8">
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
                      </Row>

                      <Modal
                        isOpen={this.state.large}
                        toggle={this.toggleLarge}
                        className={"modal-lg " + this.props.className}
                      >
                        <ModalHeader toggle={this.toggleLarge}>
                          New Contact
                        </ModalHeader>
                        <ModalBody>
                          <Row className="row-wrapper">
                            <Col md="4">
                              <FormGroup>
                                <Label htmlFor="text-input">Title</Label>
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
                                <Label htmlFor="text-input">
                                  Middle Number
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
                                <Label htmlFor="text-input">Email</Label>
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
                                <Label htmlFor="text-input">Address1</Label>
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
                                <Label htmlFor="text-input">Address2</Label>
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
                                <Label htmlFor="text-input">State Region</Label>
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
                                <Label htmlFor="text-input">City</Label>
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
                                <Label htmlFor="text-input">Country</Label>
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
                                <Label htmlFor="text-input">
                                  Currency Code
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
                                  Billing Email
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
                  <Card>
                    <CardHeader>
                      Receipt
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
                         
                          <Col md="6">
                            <FormGroup>
                              <Label htmlFor="text-input">Reciept Number</Label>
                              <Input
                                type="text"
                                name="select"
                                id="select"
                                required
                              />
                            
                            </FormGroup>
                          </Col>
                          <Col md="6">
                            <FormGroup>
                              <Label htmlFor="text-input">Attachment Description</Label>
                              <Input
                                type="text"
                                name="select"
                                id="select"
                                required
                              />
                                
                            </FormGroup>
                          </Col>
                        </Row>
                      </CardBody>
                    </Collapse>
                  </Card>
                  <Card>
                    <CardHeader>
                    Purchase Item Details
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

                      </CardBody>
                    </Collapse>
                  </Card>
                  <Row className="bank-btn-wrapper">
                    <FormGroup>
                      <Button type="submit" className="submit-invoice" size="sm" color="primary">
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

export default CreateOrEditPurchase;
