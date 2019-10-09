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
      currencyValue: "",
      selectedCountry: {},
      selectedCurrency: {},
      countryList: [],
      currencylist: [],
      accountTypeList: [],
      vatCategoryList: [],
      bankData: {
        countryName: "",
        currencyName: "",
        primaryAccount: "true",
        personalCorporateAccountInd: "C",
        openingBalance: "",
        accountType: {}
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
          this.setState({ bankData: data });
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

  getCurrencyListData = (currencyStr) => {
    const res = sendRequest(`rest/bank/getcurrenncy?currencyStr=${currencyStr}`, "get", "");
    res
      .then(res => {
        if (res.status === 200) {
          this.setState({ loading: false });
          return res.json();
        }
      })
      .then(data => {
        this.setState({ currencylist: data })
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
        this.setState({ accountTypeList: data, bankData: { ...this.state.bankData, accountType: data[0] } });
      });
  };

  handleChange = (e, name) => {
    this.setState({
      bankData: _.set(
        { ...this.state.bankData },
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

  handleSubmit = (e, status) => {
    this.setState({ loading: true });
    e.preventDefault();
    const { accountName, bankName, personalCorporateAccountInd, primaryAccount, accountNumber, IBANNumber, swiftCode, accountType, countryName, currencyName } = this.state.bankData;
    let bal = this.state.bankData.openingBalance.split(`${this.state.currencySymbol} `);
    console.log(bal, bal[1])
    const postObj = {
      bankAccountId: "0",
      bankAccountName: accountName,
      bankName,
      bankCountry: this.state.selectedCountry.countryCode,
      personalCorporateAccountInd,
      isprimaryAccountFlag: primaryAccount,
      bankAccountCurrency: this.state.selectedCurrency.currencyCode,
      bankAccountStatus: accountType.id,
      // bankAccountStatus: this.state.bankData.accountType instanceof Object ? this.state.bankData.accountType : JSON.parse(this.state.bankData.accountType),
      openingBalance: bal[1],
      accountNumber,
      ibanNumber: IBANNumber,
      swiftCode
    };
    const res = sendRequest(
      `rest/bank/savebank?id=1`,
      "post",
      postObj
    );
    res.then(res => {
      if (res.status === 200) {
        this.success();
        this.props.history.push("bankAccount");
      }
    });
  };

  toggle = () => {
    this.setState({ collapse: !this.state.collapse });
  }

  getCountrySuggestions = value => {
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

  onCountrySuggestionsFetchRequested = ({ value }) => {
    this.setState({
      countryList: this.getCountrySuggestions(value)
    });
  };

  onCountrySuggestionsClearRequested = () => {
    this.setState({
      countryList: []
    });
  };

  getCountrySuggestionValue = suggestion => suggestion.countryFullName;

  onCountrySuggestionSelected = (e, val) => {
    this.setState({ selectedCountry: val.suggestion });
  };

  renderCountrySuggestion = suggestion => <div>{suggestion.countryFullName}</div>;

  onCountryChange = (event, { newValue }) => {
    this.setState({
      bankData: { ...this.state.bankData, countryName: newValue }
    });
  };

  getCurrencySuggestions = value => {
    const inputValue = value.trim().toLowerCase();
    const inputLength = inputValue.length;

    this.getCurrencyListData(inputValue);
    return inputLength === 0
      ? []
      : this.state.currencylist.filter(
        transaction =>
          transaction.currencyName
            .toLowerCase()
            .slice(0, inputLength) === inputValue
      )
  };

  onCurrencySuggestionsFetchRequested = ({ value }) => {
    this.setState({
      currencylist: this.getCurrencySuggestions(value)
    });
  };

  onCurrencySuggestionsClearRequested = () => {
    this.setState({
      currencylist: []
    });
  };

  getCurrencySuggestionValue = suggestion => suggestion.description;

  onCurrencySuggestionSelected = (e, val) => {
    this.setState({ selectedCurrency: val.suggestion });
    this.setState({ currencySymbol: val.suggestion.currencySymbol, bankData: { ...this.state.bankData, currencyName: val.suggestion.description, openingBalance: `${val.suggestion.currencySymbol} ` } });
  };

  renderCurrencySuggestion = suggestion => <div>{suggestion.description}</div>;

  onCurrencyChange = (event, { newValue }) => {
    this.setState({
      bankData: { ...this.state.bankData, currencyName: newValue }
    });
  };

  render() {
    const { loading, countryList, accountTypeList, currencylist, selectedCurrency } = this.state;
    const { id, countryName, accountName, currencyName, primaryAccount, personalCorporateAccountInd, accountType, bankName, accountNumber, swiftCode, IBANNumber, openingBalance } = this.state.bankData
      ? this.state.bankData
      : {};

    const countryInputProps = {
      placeholder: "Type Country Name",
      value: countryName,
      onChange: this.onCountryChange
    };

    const currencyInputProps = {
      placeholder: "Type Currency Name",
      value: currencyName,
      onChange: this.onCurrencyChange
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
                <Form onSubmit={this.handleSubmit} name="simpleForm">
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
                              value={accountName}
                              onChange={this.handleChange}
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup >
                            <Label htmlFor="select">Currency</Label>
                            <Autosuggest
                              className="autoSuggest"
                              suggestions={currencylist}
                              onSuggestionsFetchRequested={
                                this.onCurrencySuggestionsFetchRequested
                              }
                              onSuggestionsClearRequested={
                                this.onCurrencySuggestionsClearRequested
                              }
                              getSuggestionValue={this.getCurrencySuggestionValue}
                              onSuggestionSelected={this.onCurrencySuggestionSelected}
                              renderSuggestion={this.renderCurrencySuggestion}
                              inputProps={currencyInputProps}
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
                              value={openingBalance}
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
                            <Input className="form-check-input" type="radio" id="inline-radio1" name="primaryAccount" value="true" onChange={this.handleChange} checked={primaryAccount === "true"} />
                            <Label className="form-check-label" check htmlFor="inline-radio1">Yes</Label>
                          </FormGroup>
                          <FormGroup check inline>
                            <Input className="form-check-input" type="radio" id="inline-radio2" name="primaryAccount" value="false" onChange={this.handleChange} checked={primaryAccount === "false"} />
                            <Label className="form-check-label" check htmlFor="inline-radio2">No</Label>
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <Label htmlFor="select" className="d-block">Is this your personal account?</Label>
                          <FormGroup check inline>
                            <Input className="form-check-input" type="radio" id="inline-radio1" name="personalCorporateAccountInd" value="P" onChange={this.handleChange} checked={personalCorporateAccountInd === "P"} />
                            <Label className="form-check-label" check htmlFor="inline-radio1">Yes</Label>
                          </FormGroup>
                          <FormGroup check inline>
                            <Input className="form-check-input" type="radio" id="inline-radio2" name="personalCorporateAccountInd" value="C" onChange={this.handleChange} checked={personalCorporateAccountInd === "C"} />
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
                                // <option key={index} value={JSON.stringify(item)}>
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
                                placeholder="Enter IBAN Number"
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
                              <Autosuggest
                                className="autoSuggest"
                                suggestions={countryList}
                                onSuggestionsFetchRequested={
                                  this.onCountrySuggestionsFetchRequested
                                }
                                onSuggestionsClearRequested={
                                  this.onCountrySuggestionsClearRequested
                                }
                                getSuggestionValue={this.getCountrySuggestionValue}
                                onSuggestionSelected={this.onCountrySuggestionSelected}
                                renderSuggestion={this.renderCountrySuggestion}
                                inputProps={countryInputProps}
                              />
                              {/* <Input
                                type="text"
                                id="text-input"
                                name="text-input"
                                placeholder="Enter Country"
                                onChange={this.handleChange}
                                required
                              /> */}
                            </FormGroup>
                          </Col>
                        </Row>
                      </CardBody>
                    </Collapse>
                  </Card>
                  <Row className="bank-btn-wrapper">
                    <FormGroup>
                      <Button type="submit" size="sm" color="primary" value="Submit" name="simpleForm">
                        <i className="fa fa-dot-circle-o "></i> Save
                      </Button>
                    </FormGroup>
                    <FormGroup>
                      <Button type="submit" size="sm" color="primary" value="SubmitAndAdd" name="simpleForm">
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
