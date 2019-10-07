import React, { Component } from "react";

import {
  Card,
  CardHeader,
  CardBody,
  Button,
  Col,
  FormGroup,
  Label,
  Input,
  Form
} from "reactstrap";
import "react-bootstrap-table/dist/react-bootstrap-table-all.min.css";
import sendRequest from "../../../../xhrRequest";
import _ from "lodash";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Autosuggest from "react-autosuggest";

class CreateOrEditTransactionCategory extends Component {
  constructor(props) {
    super(props);

    this.state = {
      value: "",
      suggestions: [],
      transactionCategoryList: [],
      vatCategoryList: [],
      transactionData: {
        defaltFlag: "N",
        transactionTypeName: ""
      },
      loading: false
    };
  }

  componentDidMount() {
    this.getTransactionListData();
    this.getvatListData();
    const params = new URLSearchParams(this.props.location.search);
    const id = params.get("id");
    if (id) {
      this.setState({ loading: true });
      const res = sendRequest(
        `rest/transaction/edittransactioncategory?id=${id}`,
        "get",
        ""
      );
      res
        .then(res => {
          if (res.status === 200) {
            this.setState({ loading: false });
            return res.json();
          }
        })
        .then(data => {
          const {
            transactionCategoryId,
            transactionCategoryName,
            transactionCategoryCode,
            transactionCategoryDescription,
            parentTransactionCategory,
            defaltFlag,
            parentTransactionType,
            selectTransactionType,
            transactionType
          } = data;
          this.setState({
            transactionData: {
              transactionCategoryId,
              categoryName: transactionCategoryName,
              categoryCode: transactionCategoryCode,
              defaultFlag: defaltFlag,
              categoryDiscription: transactionCategoryDescription,
              transactionTypeCode: transactionType.transactionTypeCode,
              parentTransactionCategory:
                parentTransactionCategory.transactionCategoryName,
              transactionTypeName: transactionType.transactionTypeName
            },
            selectedTransactionCategory: transactionType.transactionTypeName
          });
        });
    }
  }
  getSuggestions = value => {
    const inputValue = value.trim().toLowerCase();
    const inputLength = inputValue.length;

    return inputLength === 0
      ? []
      : this.state.transactionCategoryList.filter(
          transaction =>
            transaction.transactionTypeName
              .toLowerCase()
              .slice(0, inputLength) === inputValue
        );
  };
  getSuggestionValue = suggestion => suggestion.transactionTypeName;

  // Use your imagination to render suggestions.
  renderSuggestion = suggestion => <div>{suggestion.transactionTypeName}</div>;

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

  // Autosuggest will call this function every time you need to clear suggestions.
  onSuggestionsClearRequested = () => {
    this.setState({
      suggestions: []
    });
  };

  getTransactionListData = () => {
    const res = sendRequest(`rest/transaction/gettransactiontype`, "get", "", "");
    res
      .then(res => {
        if (res.status === 200) {
          this.setState({ loading: false });
          return res.json();
        }
      })
      .then(data => {
        this.setState({ transactionCategoryList: data });
      });
  };
  getvatListData = () => {
    const res = sendRequest(`rest/transaction/getvatcategories`, "get", "", "");
    res
      .then(res => {
        if (res.status === 200) {
          this.setState({ loading: false });
          return res.json();
        }
      })
      .then(data => {
        this.setState({ vatCategoryList: data });
      });
  };

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
    const params = new URLSearchParams(this.props.location.search);
    const id = params.get("id");
    this.setState({ loading: true });
    e.preventDefault();
    console.log(this.state.transactionData);
    const {
      categoryName,
      categoryCode,
      categoryDiscription,
      selectVatCategoryCode,
      defaltFlag,
      parentTransactionType,
      selectTransactionType
    } = this.state.transactionData;
    let postObj;
    if (!id) {
      postObj = {
        transactionCategoryName: categoryName,
        transactionCategoryCode: categoryCode,
        defaltFlag: defaltFlag,
        parentTransactionCategory: parentTransactionType,
        transactionCategoryDescription: categoryDiscription,
        vatCategory: selectVatCategoryCode,
        transactionType: this.state.selectedTransactionCategory
          ? this.state.selectedTransactionCategory.transactionTypeCode
          : ""
      };
    } else {
      postObj = { ...this.state.transactionData };
    }
    const res = sendRequest(
      `rest/transaction/savetransaction?id=1`,
      "post",
      postObj
    );
    res.then(res => {
      if (res.status === 200) {
        this.success();
        this.props.history.push("settings/transaction-category");
      }
    });
  };

  onSuggestionSelected = (e, val) => {
    this.setState({ selectedTransactionCategory: val.suggestion });
  };

  render() {
    const { loading, selectedTransactionCategory } = this.state;
    const {
      id,
      transactionTypeName,
      categoryName,
      categoryCode,
      defaultFlag,
      categoryDiscription,
      parentTransactionCategory
    } = this.state.transactionData ? this.state.transactionData : {};
    const { value, suggestions } = this.state;

    // Autosuggest will pass through all these props to the input.
    const inputProps = {
      placeholder: "Type Transaction CategoryType",
      value: transactionTypeName,
      onChange: this.onChange
    };

    return (
      <div className="animated">
        <Card>
          <CardHeader>
            {id ? "Edit Vat Category" : "New Transaction Category"}
          </CardHeader>
          <CardBody>
            <Form onSubmit={this.handleSubmit} name="simpleForm">
              {/* <Row> */}
              <FormGroup>
                <Col xs="4">
                  <FormGroup>
                    <Label htmlFor="categoryName">Category Name</Label>
                    <Input
                      type="text"
                      id="categoryName"
                      name="categoryName"
                      placeholder="Enter Category Name"
                      value={categoryName}
                      onChange={this.handleChange}
                      required
                    />
                  </FormGroup>
                </Col>
              </FormGroup>
              <FormGroup>
                <Col xs="4">
                  <FormGroup>
                    <Label htmlFor="categoryCode">Category Code</Label>
                    <Input
                      type="text"
                      id="categoryCode"
                      name="categoryCode"
                      placeholder="Enter Category Code"
                      value={categoryCode}
                      onChange={this.handleChange}
                      required
                    />
                  </FormGroup>
                </Col>
              </FormGroup>
              <FormGroup>
                <Col xs="4">
                  <FormGroup>
                    <Label htmlFor="categoryDiscription">
                      Category Description
                    </Label>
                    <Input
                      type="textarea"
                      id="categoryDiscription"
                      name="categoryDiscription"
                      value={categoryDiscription}
                      placeholder="Enter  Category Description"
                      onChange={this.handleChange}
                      required
                    />
                  </FormGroup>
                </Col>
              </FormGroup>
              <FormGroup>
                <Col md="3">
                  <Label>Default Flag</Label>
                </Col>
                <Col md="9">
                  <FormGroup check inline>
                    <Input
                      className="form-check-input"
                      type="radio"
                      id="inline-radio1"
                      name="defaltFlag"
                      value="Y"
                      checked={defaultFlag === "Y"}
                      onChange={this.handleChange}
                    />
                    <Label
                      className="form-check-label"
                      check
                      htmlFor="inline-radio1"
                    >
                      Yes
                    </Label>
                  </FormGroup>
                  <FormGroup check inline>
                    <Input
                      className="form-check-input"
                      type="radio"
                      id="inline-radio2"
                      name="defaltFlag"
                      value="N"
                      checked={defaultFlag === "N"}
                      onChange={this.handleChange}
                    />
                    <Label
                      className="form-check-label"
                      check
                      htmlFor="inline-radio2"
                    >
                      No
                    </Label>
                  </FormGroup>
                </Col>
              </FormGroup>
              <FormGroup>
                <Col xs="4">
                  <FormGroup>
                    <Label htmlFor="selectCategoryCode">
                      Vat Category Code
                    </Label>
                    <Input
                      type="select"
                      name="selectVatCategoryCode"
                      value={transactionTypeName}
                      id="selectCategoryCode"
                      onChange={this.handleChange}
                      required
                    >
                      {this.state.vatCategoryList.map((item, index) => (
                        <option key={index} value={item.id}>
                          {" "}
                          {item.name}
                        </option>
                      ))}
                    </Input>
                  </FormGroup>
                </Col>
              </FormGroup>
              <FormGroup>
                <Col xs="4">
                  <FormGroup>
                    <Label htmlFor="selectTransactionType">
                      Transaction Type
                    </Label>
                    <Autosuggest
                      className="autoSuggest"
                      suggestions={suggestions}
                      onSuggestionsFetchRequested={
                        this.onSuggestionsFetchRequested
                      }
                      onSuggestionsClearRequested={
                        this.onSuggestionsClearRequested
                      }
                      getSuggestionValue={this.getSuggestionValue}
                      onSuggestionSelected={this.onSuggestionSelected}
                      renderSuggestion={this.renderSuggestion}
                      inputProps={inputProps}
                    />
                  </FormGroup>
                </Col>
              </FormGroup>
              {selectedTransactionCategory ? (
                <FormGroup>
                  <Col xs="4">
                    <FormGroup>
                      <Label htmlFor="selectTransactionType">
                        Parent Transaction Type
                      </Label>
                      <Input
                        type="text"
                        name="parentTransactionType"
                        id="parentTransactionType"
                        value={parentTransactionCategory}
                        onChange={this.handleChange}
                        required
                      ></Input>
                    </FormGroup>
                  </Col>
                </FormGroup>
              ) : (
                ""
              )}
              <FormGroup>
                <Col>
                  <Button type="submit" size="sm" color="primary">
                    <i className="fa fa-dot-circle-o"></i> Submit
                  </Button>
                </Col>
              </FormGroup>
              {/* </Row> */}
            </Form>
          </CardBody>
        </Card>
        {loading ? (
          <div className="sk-double-bounce loader">
            <div className="sk-child sk-double-bounce1"></div>
            <div className="sk-child sk-double-bounce2"></div>
          </div>
        ) : (
          ""
        )}
      </div>
    );
  }
}

export default CreateOrEditTransactionCategory;
