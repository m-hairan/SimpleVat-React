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

class CreateOrEditTransactionCategory extends Component {
  constructor(props) {
    super(props);

    this.state = {
      transactionCategoryList: [],
      vatCategoryList: [],
      transactionData: {},
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
        `/transaction/edittransactioncategory?id=${id}`,
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

  getTransactionListData = () => {
    const res = sendRequest(
      `transaction/gettransactioncategory`,
      "get",
      "",
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
        this.setState({ transactionCategoryList: data });
      });
  };
  getvatListData = () => {
    const res = sendRequest(`/transaction/getvatcategories`, "get", "", "");
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

  render() {
    const { loading } = this.state;
    const { id, name, vat } = this.state.transactionData
      ? this.state.transactionData
      : {};
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
                      name="inline-radios"
                      value="option1"
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
                      name="inline-radios"
                      value="option2"
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
                      name="selectCategoryCode"
                      id="selectCategoryCode"
                      onChange={this.handleChange}
                      required
                    >
                      {this.state.vatCategoryList.map((item, index) => (
                        <option key={index} value={item.name}>
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
                    <Input
                      type="select"
                      name="selectTransactionType"
                      id="selectTransactionType"
                      onChange={this.handleChange}
                      required
                    >
                      {this.state.transactionCategoryList.map((item, index) => (
                        <option key={index} value={item.id}>
                          {" "}
                          {item.transactionType.transactionTypeName}
                        </option>
                      ))}
                    </Input>
                  </FormGroup>
                </Col>
              </FormGroup>
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
