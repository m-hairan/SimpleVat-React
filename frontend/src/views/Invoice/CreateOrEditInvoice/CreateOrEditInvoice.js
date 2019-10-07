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

class CreateOrEditInvoice extends Component {
  constructor(props) {
    super(props);

    this.state = {
      transactionCategoryList: [],
      vatCategoryList: [],
      transactionData: {},
      collapse: true,
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

  toggle = () => {
    this.setState({ collapse: !this.state.collapse });
  };

  render() {
    const { loading } = this.state;
    const { id, name, vat } = this.state.transactionData
      ? this.state.transactionData
      : {};
    return (
      <div className="animated fadeIn">
        <Card>
          <CardHeader>{id ? "Edit Bank Account" : "New Invoice"}</CardHeader>
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
                    <CardHeader>Contact and Project Details</CardHeader>
                    <CardBody>
                      <Row className="row-wrapper">
                        <Col md="4">
                          <FormGroup>
                            <Label htmlFor="text-input">Invoice Ref No.</Label>
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
                            <Label htmlFor="select">Project</Label>
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
                      </Row>
                      <Row>
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
                            type="submit"
                            size="sm"
                            color="primary"
                            onClick={this.toggleLarge}
                            className="mr-1 add-btn"
                          >
                            <i class="fas fa-plus"></i> Add
                          </Button>
                        </Col>
                      </Row>
                      <Modal
                        isOpen={this.state.large}
                        toggle={this.toggleLarge}
                        className={"modal-lg " + this.props.className}
                      >
                        <ModalHeader toggle={this.toggleLarge}>
                          Modal title
                        </ModalHeader>
                        <ModalBody>
                          Lorem ipsum dolor sit amet, consectetur adipisicing
                          elit, sed do eiusmod tempor incididunt ut labore et
                          dolore magna aliqua. Ut enim ad minim veniam, quis
                          nostrud exercitation ullamco laboris nisi ut aliquip
                          ex ea commodo consequat. Duis aute irure dolor in
                          reprehenderit in voluptate velit esse cillum dolore eu
                          fugiat nulla pariatur. Excepteur sint occaecat
                          cupidatat non proident, sunt in culpa qui officia
                          deserunt mollit anim id est laborum.
                        </ModalBody>
                        <ModalFooter>
                          <Button color="primary" onClick={this.toggleLarge}>
                            Do Something
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
                      Shipping Details
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
                          <Col md="4">
                            <FormGroup check className="checkbox address">
                              <Input
                                className="form-check-input"
                                type="checkbox"
                                id="checkbox1"
                                name="checkbox1"
                                value="option1"
                              />
                              <Label
                                check
                                className="form-check-label"
                                htmlFor="checkbox1"
                              >
                                Address is same as above Address
                              </Label>
                            </FormGroup>
                          </Col>
                          <Col md="4">
                            <FormGroup>
                              <Label htmlFor="text-input">Contact</Label>
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
                        </Row>
                      </CardBody>
                    </Collapse>
                  </Card>
                  <Card>
                    <CardHeader>
                      Invoice Details
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
                          <Col md="4">
                            <FormGroup>
                              <Label htmlFor="text-input">Bank Name</Label>
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
                            <FormGroup>
                              <Label htmlFor="text-input">Account Number</Label>
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
                            <FormGroup>
                              <Label htmlFor="text-input">Swift Code</Label>
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

export default CreateOrEditInvoice;
