import React, { Component } from "react";
import {
  Card,
  CardHeader,
  Row,
  Col,
  FormGroup,
  Label,
  Input,
  Form,
} from "reactstrap";
import "react-bootstrap-table/dist/react-bootstrap-table-all.min.css";
import _ from "lodash";
import "react-toastify/dist/ReactToastify.css";

class Employee extends Component {
  constructor(props) {
    super(props);

    this.state = {
      value: "",
      collapse: true,
      loading: false,
      large: false
    };
    
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
    return (
      <div className="animated fadeIn">
        <Card>
          <CardHeader>Create Employee</CardHeader>
          <div className="create-Employee-wrapper">
            <Row>
              <Col xs="12">
                <Form
                  action=""
                  method="post"
                  encType="multipart/form-data"
                  className="form-horizontal"
                >
                 
                      <Row className="row-wrapper">
                     
                        <Col md="4">
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
                        New Employee
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
                        Already User
                      </Label>
                    </FormGroup>
                        </Col>
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

export default Employee;
