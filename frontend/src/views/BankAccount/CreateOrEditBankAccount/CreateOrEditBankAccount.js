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
import sendRequest from "../../../xhrRequest";
import _ from "lodash";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Autosuggest from "react-autosuggest";

class CreateOrEditBankAccount extends Component {
  constructor(props) {
    super(props);

    this.state = {
      countryValue: "",
      selectedCountry: {},
      countryList: [],
      accountTypeList: [],
      vatCategoryList: [],
      bankData: {
        countryName: "",
        primaryAccount: "Y",
        personalAccount: "N"
      },
      collapse: true,
      loading: false,
    };
  }

  componentDidMount() {

    // this.getCountryListData();
    this.getAccountType();
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
          this.setState({ transactionData: data });
        });
    }
  }

  getCountryListData = (countryStr) => {
    const res = sendRequest(`rest/bank/getcountry?countryStr=${countryStr}`, "get", "");
    res
      .then(res => {
        if (res.status === 200) {
          this.setState({ loading: false });
          return res.json();
        }
      })
      .then(data => {
        this.setState({ countryList: data })
        return data;
      });
  };

  getAccountType = () => {
    const res = sendRequest(`rest/bank/getaccounttype`, "get", "");
    res
      .then(res => {
        if (res.status === 200) {
          this.setState({ loading: false });
          return res.json();
        }
      })
      .then(data => {
        this.setState({ accountTypeList: data });
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
    this.setState({ loading: true });
    e.preventDefault();
    const { } = this.state.transactionData;
    const postObj = {

    };
    console.log(postObj);
    //  const res = sendRequest(
    //   `rest//transaction/savetransaction?id=1`,
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
  }

  getSuggestions = value => {
    const inputValue = value.trim().toLowerCase();
    const inputLength = inputValue.length;

    this.getCountryListData(inputValue);
    return inputLength === 0
      ? []
      : this.state.countryList.filter(
        transaction =>
          transaction.countryName
            .toLowerCase()
            .slice(0, inputLength) === inputValue
      )
  };

  onSuggestionsFetchRequested = ({ value }) => {
    this.setState({
      countryList: this.getSuggestions(value)
    });
  };

  onSuggestionsClearRequested = () => {
    this.setState({
      countryList: []
    });
  };

  getSuggestionValue = suggestion => suggestion.countryFullName;

  onSuggestionSelected = (e, val) => {
    console.log("val ==> ", val)
    this.setState({ selectedCountry: val.suggestion });
  };

  renderSuggestion = suggestion => <div>{suggestion.countryFullName}</div>;

  onCountryChange = (event, { newValue }) => {
    console.log(newValue);
    this.setState({
      bankData: { ...this.state.bankData, countryName: newValue }
    });
  };

  render() {
    const { loading, countryList, accountTypeList } = this.state;
    const { id, countryName, primaryAccount, personalAccount, accountType, bankName, accountNumber, swiftCode, IBANNumber } = this.state.bankData
      ? this.state.bankData
      : {};
    const { currencyCode } = this.state.selectedCountry;
    const inputProps = {
      placeholder: "Type Country Name",
      value: countryName,
      onChange: this.onCountryChange
    };
    return (
      <div className="animated fadeIn">
        <Card>
          <CardHeader>
            {id ? "Edit Bank Account" : "New Bank Account"}
          </CardHeader>
          <div className="create-bank-wrapper">
            <Row >
              <Col xs="12">
                <Form
                  action=""
                  method="post"
                  encType="multipart/form-data"
                  className="form-horizontal"
                >
                  <Card>
                    <CardHeader> Bank Account</CardHeader>
                    <CardBody>
                      <Row className="row-wrapper">
                        <Col md="4">
                          <FormGroup >
                            <Label htmlFor="accountName">
                              Account Name
                          </Label>
                            <Input
                              type="text"
                              id="accountName"
                              name="accountName"
                              placeholder="Enter Account Name"
                              required
                              onChange={this.handleChange}
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup >
                            <Label htmlFor="select">Currency</Label>
                            <Autosuggest
                              className="autoSuggest"
                              suggestions={countryList}
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
                        <Col md="4">
                          <FormGroup >
                            <Label htmlFor="select">Opening Balance</Label>
                            <Input
                              type="text"
                              id="openingBalance"
                              name="openingBalance"
                              placeholder="Enter Openning Balance"
                              value={`${currencyCode ? currencyCode.currencySymbol : ""} `}
                              required
                              onChange={this.handleChange}
                            />
                          </FormGroup>
                        </Col>
                      </Row>
                      <Row>
                        <Col md="4">
                          <Label htmlFor="select" className="d-block">Is this your primary account?</Label>
                          <FormGroup check inline>
                            <Input className="form-check-input" type="radio" id="inline-radio1" name="primaryAccount" value="Y" onChange={this.handleChange} checked={primaryAccount === "Y"} />
                            <Label className="form-check-label" check htmlFor="inline-radio1">Yes</Label>
                          </FormGroup>
                          <FormGroup check inline>
                            <Input className="form-check-input" type="radio" id="inline-radio2" name="primaryAccount" value="N" onChange={this.handleChange} checked={primaryAccount === "N"} />
                            <Label className="form-check-label" check htmlFor="inline-radio2">No</Label>
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <Label htmlFor="select" className="d-block">Is this your personal account?</Label>
                          <FormGroup check inline>
                            <Input className="form-check-input" type="radio" id="inline-radio1" name="personalAccount" value="Y" onChange={this.handleChange} checked={personalAccount === "Y"} />
                            <Label className="form-check-label" check htmlFor="inline-radio1">Yes</Label>
                          </FormGroup>
                          <FormGroup check inline>
                            <Input className="form-check-input" type="radio" id="inline-radio2" name="personalAccount" value="N" onChange={this.handleChange} checked={personalAccount === "N"} />
                            <Label className="form-check-label" check htmlFor="inline-radio2">No</Label>
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup >
                            <Label htmlFor="select">Account Type</Label>
                            <Input
                              type="select"
                              name="accountType"
                              value={accountType}
                              id="accountType"
                              onChange={e => this.handleChange(e, "accountType")}
                              required
                            >
                              {accountTypeList.map((item, index) => (
                                <option key={index} value={item.id}>
                                  {" "}
                                  {item.name}
                                </option>
                              ))}
                            </Input>
                          </FormGroup>
                        </Col>
                      </Row>
                    </CardBody>
                  </Card>
                  <Card>
                    <CardHeader>
                      Bank Account
                     <div className="card-header-actions">
                        <Button color="link" className="card-header-action btn-minimize" data-target="#collapseExample" onClick={this.toggle}><i className="icon-arrow-up"></i></Button>
                      </div>
                    </CardHeader>
                    <Collapse isOpen={this.state.collapse} id="collapseExample">
                      <CardBody>

                        <Row className="row-wrapper">
                          <Col md="4">
                            <FormGroup >
                              <Label htmlFor="bankName">
                                Bank Name
                              </Label>
                              <Input
                                type="text"
                                id="bankName"
                                name="bankName"
                                placeholder="Enter Bank Name"
                                value={bankName}
                                onChange={this.handleChange}
                                required
                              />
                            </FormGroup>
                          </Col>
                          <Col md="4">
                            <FormGroup >
                              <Label htmlFor="accountNumber">
                                Account Number
                              </Label>
                              <Input
                                type="number"
                                id="accountNumber"
                                name="accountNumber"
                                value={accountNumber}
                                placeholder="Enter Account Number"
                                onChange={this.handleChange}
                                required
                              />
                            </FormGroup>
                          </Col>
                          <Col md="4">
                            <FormGroup >
                              <Label htmlFor="swiftCode">
                                Swift Code
                              </Label>
                              <Input
                                type="text"
                                id="swiftCode"
                                name="swiftCode"
                                placeholder="Enter Swift Code"
                                value={swiftCode}
                                onChange={this.handleChange}
                                required
                              />
                            </FormGroup>
                          </Col>
                        </Row>
                        <Row>
                          <Col md="4">
                            <FormGroup >
                              <Label htmlFor="IBANNumber">
                                IBAN Number
                              </Label>
                              <Input
                                type="text"
                                id="IBANNumber"
                                name="IBANNumber"
                                placeholder="Text"
                                value={IBANNumber}
                                onChange={this.handleChange}
                                required
                              />
                            </FormGroup>
                          </Col>
                          <Col md="4">
                            <FormGroup >
                              <Label htmlFor="text-input">
                                Country
                              </Label>
                              <Input
                                type="text"
                                id="text-input"
                                name="text-input"
                                placeholder="Text"
                                onChange={this.handleChange}
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
                      <Button type="submit" size="sm" color="primary">
                        <i className="fa fa-dot-circle-o "></i> Save
                      </Button>
                      <Button type="submit" size="sm" color="primary">
                        <i className="fa fa-dot-circle-o"></i> Save & Add More
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

export default CreateOrEditBankAccount;
