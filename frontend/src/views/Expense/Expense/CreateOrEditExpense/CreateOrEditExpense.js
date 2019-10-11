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
import Autosuggest from 'react-autosuggest';
import sendRequest from "../../../../xhrRequest";
import Loader from "../../../../Loader";

class CreateOrEditExpense extends Component {
  constructor(props) {
    super(props);

    this.state = {
      value: "",
      suggestions: [],
      categorySuggestion: [],
      categoryValue: "",
      currencySuggestions: [],
      currencyValue: "",
      projectSuggestions: [],
      projectValue: "",
      collapseReceipt: true,
      collapseitemDetails: true,
      loading: false,
      large: false,
      expenseData: { description: "" },
      cliementList: []
    };
    this.toggleLarge = this.toggleLarge.bind(this);
  }

  componentDidMount() {
    this.getcliementList();
  }

  toggleLarge() {
    this.setState({
      large: !this.state.large
    });
  }

  getcliementList = () => {
    this.setState({ loading: true });
    const res = sendRequest(`rest/expense/claimants`, "get", "");
    res
      .then(res => {
        if (res.status === 200) {
          this.setState({ loading: false });
          return res.json();
        }
      })
      .then(data => {
        this.setState({ cliementList: data ? data : [] });
      });
  }

  getCurrencyList = (val) => {
    this.setState({ loading: true });
    const res = sendRequest(`rest/expense/currencys?currency=${val}`, "get", "");
    res
      .then(res => {
        if (res.status === 200) {
          this.setState({ loading: false });
          return res.json();
        }
      })
      .then(data => {
        this.setState({ currencySuggestions: data });
      });
  }

  getProjectList = (val) => {
    this.setState({ loading: true });
    const res = sendRequest(`rest/expense/projects?projectName=${val}`, "get", "");
    res
      .then(res => {
        if (res.status === 200) {
          this.setState({ loading: false });
          return res.json();
        }
      })
      .then(data => {
        this.setState({ projectSuggestions: data });
      });
  }

  getCategorySuggestions = (value) => {
    this.setState({ loading: true });
    const inputValue = value.trim().toLowerCase();
    const inputLength = inputValue.length;
    this.getCategoryList(inputValue);
    return inputLength === 0
      ? []
      : this.state.categorySuggestion.length ? this.state.categorySuggestion.filter(
        lang => lang.transactionCategoryName.toLowerCase().slice(0, inputLength) === inputValue
      ) : [];
  };

  getCurrencySuggestions = (value) => {
    this.setState({ loading: true });
    const inputValue = value.trim().toLowerCase();
    const inputLength = inputValue.length;
    this.getCurrencyList(inputValue);
    return inputLength === 0
      ? []
      : this.state.categorySuggestion.length ? this.state.categorySuggestion.filter(
        lang => lang.transactionCategoryName.toLowerCase().slice(0, inputLength) === inputValue
      ) : [];
  };

  getProjectSuggestions = (value) => {
    this.setState({ loading: true });
    const inputValue = value.trim().toLowerCase();
    const inputLength = inputValue.length;
    this.getProjectList(inputValue);
    return inputLength === 0
      ? []
      : this.state.projectSuggestions.length ? this.state.projectSuggestions.filter(
        lang => lang.transactionCategoryName.toLowerCase().slice(0, inputLength) === inputValue
      ) : [];
  };

  getCategoryList = (val) => {
    this.setState({ loading: true });
    const res = sendRequest(`rest/expense/categories?categoryName=${val}`, "get", "");
    res
      .then(res => {
        if (res.status === 200) {
          this.setState({ loading: false });
          return res.json();
        }
      })
      .then(data => {
        this.setState({ categorySuggestion: data });
      });
  }

  getCategorySuggestionValue = suggestion => suggestion.transactionCategoryName;

  renderCategorySuggestion = suggestion => <div>{suggestion.transactionCategoryName}</div>;

  getCurrencySuggestionValue = suggestion => suggestion.description;

  renderCurrencySuggestion = suggestion => <div>{suggestion.description}</div>;

  getProjectSuggestionValue = suggestion => suggestion.projectName;

  renderProjectSuggestion = suggestion => <div>{suggestion.projectName}</div>;

  handleChange = (e, name) => {
    this.setState({
      expenseData: _.set(
        { ...this.state.expenseData },
        e.target.name && e.target.name !== "" ? e.target.name : name,
        e.target.type === "checkbox" ? e.target.checked : e.target.value
      )
    });
  };

  // toggle = () => {
  //   this.setState({ collapse: !this.state.collapse });
  // };

  onChange = (event, { newValue }, value) => {
    let data = {};
    data[value] = newValue
    this.setState(data);
    // this.setState({
    //   value: newValue
    // });
  };

  onSuggestionsFetchRequested = ({ value }, suggestions) => {
    let data = {};
    if (suggestions === "categorySuggestion") {
      data[suggestions] = this.getCategorySuggestions(value, suggestions);
    } else if (suggestions === "currencySuggestions") {
      data[suggestions] = this.getCurrencySuggestions(value, suggestions);
    } else if (suggestions === "projectSuggestions") {
      data[suggestions] = this.getProjectSuggestions(value, suggestions);
    }
    this.setState(data)
    // this.setState({
    //   suggestions: this.getSuggestions(value)
    // });
  };

  onSuggestionsClearRequested = (e, suggestions) => {
    let data = {};
    data[suggestions] = [];
    this.setState(data)
    // this.setState({
    //   suggestions: []
    // });
  };

  render() {
    const { categorySuggestion, categoryValue, currencySuggestions, currencyValue, projectSuggestions, projectValue, expenseData, cliementList, loading } = this.state;
    const { expenseDate, description, attachmentDescription, recieptNumber } = expenseData;
    const categoryInputProps = {
      placeholder: "Type Category Name",
      value: categoryValue,
      onChange: (e, data) => this.onChange(e, data, "categoryValue")
    };
    const currencyInputProps = {
      placeholder: "Type Currency Name",
      value: currencyValue,
      onChange: (e, data) => this.onChange(e, data, "currencyValue")
    };
    const projectInputProps = {
      placeholder: "Type Project Name",
      value: projectValue,
      onChange: (e, data) => this.onChange(e, data, "projectValue")
    };
    return (
      <div className="animated fadeIn">
        <Card>
          <CardHeader>New Expense</CardHeader>
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
                    <CardHeader>Expense Details</CardHeader>
                    <CardBody>
                      <Row className="row-wrapper">
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="text-input">Cliement</Label>
                            {/* <Input
                              type="text"
                              id="Cliement"
                              name="Cliement"
                              required
                            /> */}
                            <Input
                              type="select"
                              name="cliement"
                              id="cliement"
                              required
                            >
                              {
                                cliementList.length ? cliementList.map((item, ind) => <option key={ind} value={item.userId}>{item.firstName}</option>) : ""
                              }
                            </Input>
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Category</Label>
                            <Autosuggest
                              suggestions={categorySuggestion}
                              onSuggestionsFetchRequested={
                                e => this.onSuggestionsFetchRequested(e, "categorySuggestion")
                              }
                              onSuggestionsClearRequested={
                                e => this.onSuggestionsClearRequested(e, "categorySuggestion")
                              }
                              getSuggestionValue={this.getCategorySuggestionValue}
                              renderSuggestion={this.renderCategorySuggestion}
                              inputProps={categoryInputProps}
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Expense Date</Label>
                            <Input
                              type="date"
                              name="expenseDate"
                              id="expenseDate"
                              value={expenseDate}
                              onChange={e => this.handleChange(e, "expenseDate")}
                            />
                          </FormGroup>
                        </Col>
                      </Row>
                      <Row>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Currency</Label>
                            <Autosuggest
                              suggestions={currencySuggestions}
                              onSuggestionsFetchRequested={
                                e => this.onSuggestionsFetchRequested(e, "currencySuggestions")
                              }
                              onSuggestionsClearRequested={
                                e => this.onSuggestionsClearRequested(e, "currencySuggestions")
                              }
                              getSuggestionValue={this.getCurrencySuggestionValue}
                              renderSuggestion={this.renderCurrencySuggestion}
                              inputProps={currencyInputProps}
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Project</Label>
                            {/* <Input
                              type="text"
                              name="project"
                              id="project"
                              onChange={e => this.handleChange(e, "project")}
                              required
                            /> */}
                            <Autosuggest
                              suggestions={projectSuggestions}
                              onSuggestionsFetchRequested={
                                e => this.onSuggestionsFetchRequested(e, "projectSuggestions")
                              }
                              onSuggestionsClearRequested={
                                e => this.onSuggestionsClearRequested(e, "projectSuggestions")
                              }
                              getSuggestionValue={this.getProjectSuggestionValue}
                              renderSuggestion={this.renderProjectSuggestion}
                              inputProps={projectInputProps}
                            />
                          </FormGroup>
                        </Col>
                      </Row>
                      <Row>
                        <Col md="8">
                          <FormGroup>
                            <Label htmlFor="Description">Description</Label>
                            <Input
                              type="textarea"
                              id="description"
                              name="description"
                              maxLength="255"
                              value={description}
                              onChange={e => this.handleChange(e, "description")}
                              required
                            />
                            <span>{`${255 - description.split('').length} characters remaining.`}</span>
                          </FormGroup>
                        </Col>
                      </Row>
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
                          onClick={() => { this.setState({ collapseReceipt: !this.state.collapseReceipt }) }}
                        >
                          <i className="icon-arrow-up"></i>
                        </Button>
                      </div>
                    </CardHeader>
                    <Collapse isOpen={this.state.collapseReceipt} id="collapseExample">
                      <CardBody>
                        <Row className="row-wrapper">

                          <Col md="6">
                            <FormGroup>
                              <Label htmlFor="text-input">Reciept Number</Label>
                              <Input
                                type="number"
                                name="recieptNumber"
                                id="recieptNumber"
                                value={recieptNumber}
                              />

                            </FormGroup>
                          </Col>
                          <Col md="6">
                            <FormGroup>
                              <Label htmlFor="text-input">Attachment Description</Label>
                              <Input
                                type="text"
                                name="attachmentDescription"
                                id="attachmentDescription"
                                value={attachmentDescription}
                              />

                            </FormGroup>
                          </Col>
                        </Row>
                        <Input type="file" id="file-input" name="file-input" />
                      </CardBody>
                    </Collapse>
                  </Card>
                  <Card>
                    <CardHeader>
                      Expense Item Details
                      <div className="card-header-actions">
                        <Button
                          color="link"
                          className="card-header-action btn-minimize"
                          data-target="#collapseExample"
                          onClick={() => this.setState({ collapseitemDetails: !this.state.collapseitemDetails })}
                        >
                          <i className="icon-arrow-up"></i>
                        </Button>
                      </div>
                    </CardHeader>
                    <Collapse isOpen={this.state.collapseitemDetails} id="collapseExample">
                      <CardBody>
                        <Button type="submit" className="submit-invoice" size="sm" color="primary">
                          <i className="fas fa-plus "></i> Add More
                      </Button>
                        <table className="expense-items-table">
                          <thead>
                            <tr>
                              <th></th>
                              <th>Product/Service</th>
                              <th>Quantity</th>
                              <th>Unit Price ()</th>
                              <th>VAT (%)</th>
                              <th>Subtotal ()</th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <td>
                                <Button
                                  block
                                  color="primary"
                                  className="btn-pill vat-actions"
                                  title="Delete Expense"
                                // onClick={() => this.setState({ selectedData:  }, () => this.setState({ openDeleteModal: true }))}
                                >
                                  <i className="fas fa-trash-alt"></i>
                                </Button>
                              </td>
                              <td>
                                <Input
                                  type="text"
                                  name="attachmentDescription"
                                  id="attachmentDescription"
                                  value={attachmentDescription}
                                />
                              </td>
                              <td>
                                <Input
                                  type="text"
                                  name="attachmentDescription"
                                  id="attachmentDescription"
                                  value={attachmentDescription}
                                />
                              </td>
                              <td>
                                <Input
                                  type="text"
                                  name="attachmentDescription"
                                  id="attachmentDescription"
                                  value={attachmentDescription}
                                />
                              </td>
                              <td>
                                <Input
                                  type="text"
                                  name="attachmentDescription"
                                  id="attachmentDescription"
                                  value={attachmentDescription}
                                />
                              </td>
                              <td>
                                120
                              </td>
                            </tr>
                          </tbody>
                        </table>
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
        {
          loading ?
            <Loader></Loader>
            : ""
        }
      </div>
    );
  }
}

export default CreateOrEditExpense;
