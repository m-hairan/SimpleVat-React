import React, { Component } from "react";
import {
  Card,
  CardHeader,
  CardBody,
  Button,
  Col,
  Row,
  FormGroup,
  Label,
  Input,
  Form
} from "reactstrap";
import { NavLink } from "react-router-dom";
import "react-bootstrap-table/dist/react-bootstrap-table-all.min.css";
import sendRequest from "../../../../xhrRequest";
import _ from "lodash";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

class CreateOrEditCategory extends Component {
  constructor(props) {
    super(props);

    this.state = {
      vatData: {},
      loading: false
    };
  }

  componentDidMount() {
    const params = new URLSearchParams(this.props.location.search);
    const id = params.get("id");
    if (id) {
      this.setState({ loading: true });
      const res = sendRequest(`vat/getvatbyid?id=${id}`, "get", "");
      res
        .then(res => {
          if (res.status === 200) {
            this.setState({ loading: false });
            return res.json();
          }
        })
        .then(data => {
          this.setState({ vatData: data });
        });
    }
  }

  handleChange = (e, name) => {
    this.setState({
      vatData: _.set(
        { ...this.state.vatData },
        e.target.name && e.target.name !== "" ? e.target.name : name,
        e.target.type === "checkbox" ? e.target.checked : e.target.value
      )
    });
  };

  success = () => {
    return toast.success("Vat Category Updated successfully... ", {
      position: toast.POSITION.TOP_RIGHT
    });
  };

  handleSubmit = e => {
    this.setState({ loading: true });
    e.preventDefault();
    const { name, vat, id } = this.state.vatData;
    let postObj;
    if (id) {
      postObj = { ...this.state.vatData };
    } else {
      postObj = { name, vat };
    }
    const res = sendRequest(`vat/savevat?id=1`, "post", postObj);
    res.then(res => {
      if (res.status === 200) {
        this.success();
        this.props.history.push("settings/vat-category");
      }
    });
  };

  render() {
    const { loading } = this.state;
    const { id, name, vat } = this.state.vatData ? this.state.vatData : {};
    return (
      <div className="animated">
        <Card>
          <CardHeader>
            {id ? "Edit Vat Category" : "New Vat Category"}
          </CardHeader>
          <CardBody>
            <Form onSubmit={this.handleSubmit} name="simpleForm">
              {/* <Row> */}
              <FormGroup>
                <Col xs="4">
                  <FormGroup>
                    <Label htmlFor="name">Vat Category Name</Label>
                    <Input
                      type="text"
                      id="name"
                      name="name"
                      value={name}
                      placeholder="Enter Vat Category Name"
                      onChange={this.handleChange}
                      required
                    />
                  </FormGroup>
                </Col>
              </FormGroup>
              <FormGroup>
                <Col xs="4">
                  <FormGroup>
                    <Label htmlFor="name">Percentage</Label>
                    <Input
                      type="number"
                      id="name"
                      name="vat"
                      value={vat}
                      placeholder="Enter Percentage"
                      onChange={this.handleChange}
                      required
                    />
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

export default CreateOrEditCategory;
