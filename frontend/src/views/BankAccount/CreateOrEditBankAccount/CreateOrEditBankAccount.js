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

class CreateOrEditBankAccount extends Component {
  constructor(props) {
    super(props);

    this.state = {
      transactionCategoryList: [],
      vatCategoryList: [],
      transactionData: {},
      collapse: true,
      loading: false,
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

  toggle = () => {
    this.setState({ collapse: !this.state.collapse });
  }

  render() {
    const { loading } = this.state;
    const { id, name, vat } = this.state.transactionData
      ? this.state.transactionData
      : {};
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
                            <Label htmlFor="text-input">
                              Account Name
                          </Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              placeholder="Text"
                              required
                            />
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup >
                            <Label htmlFor="select">Invoicing Templates</Label>
                            <Input type="select" name="select" id="select" required>
                              <option value="0">Please select</option>
                              <option value="1">Option #1</option>
                              <option value="2">Option #2</option>
                              <option value="3">Option #3</option>
                            </Input>
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup >
                            <Label htmlFor="select">Opening Balance</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              placeholder="Text"
                              required
                            />
                          </FormGroup>
                        </Col>
                      </Row>
                      <Row>
                        <Col md="4">
                          <Label htmlFor="select" className="d-block">Is this your primary account?</Label>
                          <FormGroup check inline>
                            <Input className="form-check-input" type="radio" id="inline-radio1" name="inline-radios" value="option1" />
                            <Label className="form-check-label" check htmlFor="inline-radio1">True</Label>
                          </FormGroup>
                          <FormGroup check inline>
                            <Input className="form-check-input" type="radio" id="inline-radio2" name="inline-radios" value="option2" />
                            <Label className="form-check-label" check htmlFor="inline-radio2">False</Label>
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <Label htmlFor="select" className="d-block">Is this your personal account?</Label>
                          <FormGroup check inline>
                            <Input className="form-check-input" type="radio" id="inline-radio1" name="inline-radios" value="option1" />
                            <Label className="form-check-label" check htmlFor="inline-radio1">True</Label>
                          </FormGroup>
                          <FormGroup check inline>
                            <Input className="form-check-input" type="radio" id="inline-radio2" name="inline-radios" value="option2" />
                            <Label className="form-check-label" check htmlFor="inline-radio2">False</Label>
                          </FormGroup>
                        </Col>
                        <Col md="4">
                          <FormGroup >
                            <Label htmlFor="select">Opening Balance</Label>
                            <Input
                              type="text"
                              id="text-input"
                              name="text-input"
                              placeholder="Text"
                              required
                            />
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
                              <Label htmlFor="text-input">
                                Bank Name
                              </Label>
                              <Input
                                type="text"
                                id="text-input"
                                name="text-input"
                                placeholder="Text"
                                required
                              />
                            </FormGroup>
                          </Col>
                          <Col md="4">
                            <FormGroup >
                              <Label htmlFor="text-input">
                                Account Number
                              </Label>
                              <Input
                                type="text"
                                id="text-input"
                                name="text-input"
                                placeholder="Text"
                                required
                              />
                            </FormGroup>
                          </Col>
                          <Col md="4">
                            <FormGroup >
                              <Label htmlFor="text-input">
                                Swift Code
                              </Label>
                              <Input
                                type="text"
                                id="text-input"
                                name="text-input"
                                placeholder="Text"
                                required
                              />
                            </FormGroup>
                          </Col>
                        </Row>
                        <Row>
                          <Col md="4">
                            <FormGroup >
                              <Label htmlFor="text-input">
                                IBAN Number
                              </Label>
                              <Input
                                type="text"
                                id="text-input"
                                name="text-input"
                                placeholder="Text"
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
                        <i className="fa fa-dot-circle-o"></i> Submit
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

export default CreateOrEditBankAccount;
