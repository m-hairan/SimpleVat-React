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
} from "reactstrap";
import "react-bootstrap-table/dist/react-bootstrap-table-all.min.css";
// import sendRequest from "../../../xhrRequest";
import _ from "lodash";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Autosuggest from 'react-autosuggest';

class Imports extends Component {
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
          <CardHeader>{id ? "Edit Import Transaction File" : "Import Transaction File"}</CardHeader>
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
                    <CardHeader>Bank Account</CardHeader>
                    <CardBody>
                      <Row className="row-wrapper">
                     
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="select">Bank Account</Label>
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
                    </CardBody>
                  </Card>
                  <Card>
                    <CardHeader>Imported Transactions</CardHeader>
                    <CardBody>
                   
                    </CardBody>
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

export default Imports;
