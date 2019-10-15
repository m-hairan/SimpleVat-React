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
  Collapse,
  Alert
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
      selectedCategory: {},
      categoryValue: "",
      selectedCurrency: {},
      currencySuggestions: [],
      currencyValue: "",
      projectSuggestions: [],
      projectValue: "",
      currencySymbol: "",
      collapseReceipt: true,
      collapseitemDetails: true,
      loading: false,
      large: false,
      expenseData: { description: "" },
      cliementList: [],
      vatList: [],
      expenseItem: [{ expenseItemId: 0, productName: "", quatity: "0", unitPrice: "0.00", vatId: "", subTotal: "" }],
      totalNet: 0.00,
      totalVat: 0.00,
      total: 0.00
    };
    this.toggleLarge = this.toggleLarge.bind(this);
  }

  componentDidMount() {
    this.getcliementList();
    this.getVatList();
  }

  getVatList = () => {
    this.setState({ loading: true })
    const res = sendRequest(`rest/transaction/getvatcategories`, "get", "");
    res
      .then(res => {
        if (res.status === 200) {
          this.setState({ loading: false });
          return res.json();
        }
      })
      .then(data => {
        this.setState({ vatList: data });
      });
  };

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

  onCategorySuggestionSelected = (e, val) => {
    this.setState({ selectedCategory: val.suggestion });
  }

  onCurrencySuggestionSelected = (e, val) => {
    this.setState({ selectedCurrency: val.suggestion, currencySymbol: val.suggestion.currencySymbol });
  }

  addExpense = () => {
    const d = [...this.state.expenseItem];
    if (!d[this.state.expenseItem.length - 2] || d[this.state.expenseItem.length - 2].quatity > 0 && d[this.state.expenseItem.length - 2].unitPrice > 0) {
      this.state.expenseItem.push({ expenseItemId: this.state.expenseItem.length, productName: "", quatity: "0", unitPrice: "0.00", vatId: "", subTotal: "" });
    } else if (!parseInt(d[this.state.expenseItem.length - 2].quatity) > 0 && !parseInt(d[this.state.expenseItem.length - 2].unitPrice) > 0) {
      this.setState({ alertMsg: "Unit price should be greater than 0 and Quantity should be greater than 0 in expense items." })
    } else if (!parseInt(d[this.state.expenseItem.length - 2].quatity) > 0) {
      this.setState({ alertMsg: "Please enter Quantity should be greater than 0 in expense items." })
    } else if (!parseInt(d[this.state.expenseItem.length - 2].unitPrice) > 0) {
      this.setState({ alertMsg: "Unit price should be greater than 0 in expense items." })
    }

    this.setState({ expenseItem: this.state.expenseItem });
  }

  deleteExpense = (e, id, item) => {
    // console.log("detele --> ", id, item)
    this.state.expenseItem.splice(id, 1);
    this.setState({ expenseItem: this.state.expenseItem }, () => { this.getTotalNet(); this.getTotal(); this.getTotalVat(); });
  }

  handleEnpeseTableChange = (e, name, item, ind) => {
    let expenseData = [...this.state.expenseItem];
    this.setState({ alertMsg: "" })
    // console.log("chaged --> ", expenseData, ind, expenseData[ind])
    if (name === "productName") {
      expenseData[ind].productName = e.target.value;
    } else if (name === "quatity") {
      expenseData[ind].quatity = e.target.value;
    } else if (name === "unitPrice") {
      expenseData[ind].unitPrice = e.target.value;
    } else if (name === "vatId") {
      expenseData[ind].vatId = e.target.value;
    }
    this.calculateSubTotal(ind, item);
    this.setState({ expenseItem: expenseData }, () => {
      this.getTotalNet();
      if (name === "vatId") this.getTotalVat()
    })
  }

  calculateSubTotal = (ind, item) => {
    let subTotal;
    let expenseData = [...this.state.expenseItem];
    if (item) {
      subTotal = `${item.quatity * item.unitPrice}.00`;
      expenseData[ind].subTotal = subTotal;
      this.setState({ expenseItem: expenseData })
      return subTotal > 0 ? subTotal : 0.00;
    }
  }

  getTotal = () => {
    let { totalNet, totalVat } = this.state;
    if (totalNet && totalVat) {
      this.setState({ total: parseFloat(totalNet) + parseFloat(totalVat) })
    }
  }

  getTotalNet = () => {
    let totalNet;
    this.state.expenseItem.reduce((accumulator, currVal) => {
      totalNet = currVal.subTotal ? `${parseFloat(accumulator) + parseFloat(currVal.subTotal)}.00` : accumulator
      this.setState({ totalNet: totalNet ? totalNet : 0.00 }, () => this.getTotal())
      return totalNet
    }, 0.00);
  }

  getTotalVat = () => {
    let totalVat, prevVat;
    this.state.expenseItem.reduce((accumulator, currVal) => {
      prevVat = ((this.state.vatList[currVal.vatId].vat * parseFloat(currVal.subTotal)) / 100);
      totalVat = currVal.subTotal ? `${parseFloat(accumulator) + prevVat}` : accumulator
      this.setState({ totalVat: totalVat ? totalVat : 0.00 }, () => this.getTotal())
      return totalVat
    }, this.state.totalVat);
  }

  handleSubmit = (e, status) => {
    // console.log("state --> ", this.state)
    const params = new URLSearchParams(this.props.location.search);
    const id = params.get("id");
    this.setState({ loading: true });
    e.preventDefault();
    const { expenseData, expenseItem } = this.state;
    var fileInput = document.querySelector("#file-input");
    var files = fileInput.files;
    var fl = files.length;
    var i = 0;
    let flag = true;
    while (i < fl) {
      var file = files[i];
      if (file.size > 1610612736) {
        flag = false;
        alert("File size should be less than 1.5 GB !");
        this.setState({ loading: false });
        return;
      } else {
        flag = true;
      }
      i++;
    }
    if (flag) {
      // let postObj;
      // postObj = {
      //   expenseData, expenseItem
      // };
      let data = new FormData();
      data.append("expenseData", expenseData);
      data.append("expenseItem", expenseItem);
      // console.log("data --> ", data)
      // const res = sendRequest(
      //   `rest/transaction/savetransaction?id=1`,
      //   "post",
      //   postObj
      // );
      // res.then(res => {
      //   if (res.status === 200) {
      //     this.success();
      //     if (status === "addMore") {
      //       this.setState({ transaction: {} })
      //       this.props.history.push("create-transaction-category");
      //     } else {
      //       this.props.history.push("settings/transaction-category");
      //     }
      //   }
      // });
    }
  };

  render() {
    // console.log("state --> ", this.state)
    const { categorySuggestion, categoryValue, currencySuggestions, currencyValue, projectSuggestions, projectValue, expenseData, cliementList, loading, expenseItem,
      vatList, totalNet, totalVat, alertMsg, total, currencySymbol } = this.state;
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
                  onSubmit={this.handleSubmit}
                  className="form-horizontal"
                >
                  <Card>
                    <CardHeader>Expense Details</CardHeader>
                    <CardBody>
                      <Row className="row-wrapper">
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="text-input">Cliement</Label>
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
                              onSuggestionSelected={this.onCategorySuggestionSelected}
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
                              onSuggestionSelected={this.onCurrencySuggestionSelected}
                              renderSuggestion={this.renderCurrencySuggestion}
                              inputProps={currencyInputProps}
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Project</Label>
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
                        <Input ref={ref => this.uploadInput = ref} type="file" id="file-input" name="file-input" />
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
                        {
                          alertMsg ?
                            <Alert color="danger">
                              {alertMsg}
                            </Alert>
                            : ""
                        }
                        <Button type="button" className="submit-invoice" onClick={this.addExpense} size="sm" color="primary">
                          <i className="fas fa-plus "></i> Add More
                      </Button>
                        <table className="expense-items-table">
                          <thead>
                            <tr>
                              <th></th>
                              <th>Product/Service</th>
                              <th>Quantity</th>
                              <th>Unit Price ({currencySymbol})</th>
                              <th>VAT (%)</th>
                              <th>Subtotal ({currencySymbol})</th>
                            </tr>
                          </thead>
                          <tbody>
                            {
                              expenseItem.map((item, ind) => <tr key={ind}>
                                <td width="3%">
                                  {
                                    ind > 0 ?
                                      <Button
                                        block
                                        color="primary"
                                        className="btn-pill vat-actions"
                                        title="Delete Expense"
                                        onClick={e => this.deleteExpense(e, ind, item)}
                                      >
                                        <i className="fas fa-trash-alt"></i>
                                      </Button> : ""
                                  }
                                </td>
                                <td width="20%">
                                  <Input
                                    type="text"
                                    name="productName"
                                    id="productName"
                                    value={item.productName}
                                    onChange={e => this.handleEnpeseTableChange(e, "productName", item, ind)}
                                  />
                                </td>
                                <td width="7%">
                                  <Input
                                    type="text"
                                    name="quatity"
                                    id="quatity"
                                    value={item.quatity}
                                    onChange={e => this.handleEnpeseTableChange(e, "quatity", item, ind)}
                                  />
                                </td>
                                <td width="25%">
                                  <Input
                                    type="text"
                                    name="unitPrice"
                                    id="unitPrice"
                                    value={item.unitPrice}
                                    onChange={e => this.handleEnpeseTableChange(e, "unitPrice", item, ind)}
                                  />
                                </td>
                                <td width="25%">
                                  <Input
                                    type="select"
                                    name="vatId"
                                    value={item.vatId}
                                    id="vatId"
                                    onChange={e => this.handleEnpeseTableChange(e, "vatId", item, ind)}
                                    required
                                  >
                                    {vatList.map((item, index) => (
                                      <option key={index} value={item.id}>
                                        {item.name}
                                      </option>
                                    ))}
                                  </Input>
                                </td>
                                <td width="20%">
                                  <label>{item.subTotal ? `${item.subTotal}${currencySymbol}` : ""}</label>
                                </td>
                              </tr>)
                            }
                          </tbody>
                          <tfoot>
                            <tr>
                              <td rowSpan="3" colSpan="4" className="expense-table-footer"></td>
                              <td className="expense-table-border">Total</td>
                              <td className="expense-table-border">{`${total}${currencySymbol}`}</td>
                            </tr>
                          </tfoot>
                          <tfoot>
                            <tr>
                              <td rowSpan="3" colSpan="4" className="expense-table-footer"></td>
                              <td className="expense-table-border">Total Net</td>
                              <td className="expense-table-border">{`${totalNet}${currencySymbol}`}</td>
                            </tr>
                          </tfoot>
                          <tfoot>
                            <tr>
                              <td rowSpan="3" colSpan="4" className="expense-table-footer"></td>
                              <td className="expense-table-border">Total VAT</td>
                              <td className="expense-table-border">{`${totalVat}${currencySymbol}`}</td>
                            </tr>
                          </tfoot>
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
