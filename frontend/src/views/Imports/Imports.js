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
import _ from "lodash";
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

  getSuggestions = value => {
    const inputValue = value.trim().toLowerCase();
    const inputLength = inputValue.length;

    return inputLength === 0
      ? []
      : this.state.projectList.filter(
        lang => lang.name.toLowerCase().slice(0, inputLength) === inputValue
      );
  };

  getSuggestionValue = suggestion => suggestion.name;

  renderSuggestion = suggestion => <div>{suggestion.name}</div>;


  handleChange = (e, name) => {
    this.setState({
      transactionData: _.set(
        { ...this.state.transactionData },
        e.target.name && e.target.name !== "" ? e.target.name : name,
        e.target.type === "checkbox" ? e.target.checked : e.target.value
      )
    });
  };


  

  toggle = () => {
    this.setState({ collapse: !this.state.collapse });
  };

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
  onSuggestionsClearRequested = () => {
    this.setState({
      suggestions: []
    });
  };

  render() {
   
    const { value, suggestions } = this.state;

    const inputProps = {
      value,
      onChange: this.onChange
    };
    return (
      <div className="animated fadeIn">
        <Card>
          <CardHeader>Import Transaction File</CardHeader>
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
