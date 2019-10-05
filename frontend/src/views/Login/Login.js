import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Button, Card, CardBody, CardGroup, Col, Container, Form, Input, InputGroup, InputGroupAddon, InputGroupText, Row } from 'reactstrap';
import _ from "lodash";
import { local } from "../../constant";

class Login extends Component {

  state = {
    data: {}
  }

  componentDidMount() {
    // sessionStorage.setItem("login", true)
    // this.props.history.push('/dashboard');
    // window.location.replace('/');
  }

  handleChange = (e, name) => {
    this.setState({
      data: _.set(
        { ...this.state.data },
        e.target.name && e.target.name !== "" ? e.target.name : name,
        e.target.type === "checkbox" ? e.target.checked : e.target.value
      )
    });
  };

  login = (e) => {
    e.preventDefault();
    const { username, password } = this.state.data;
    let loginSuccess = false;

    const request = fetch(local.baseUrl + "auth/token", {
      method: "post",
      headers: new Headers({
        Accept: "application/json",
        "Content-Type": "application/json",
        // Authorization: "Basic " + btoa("trusted-app:secret")
      }),
      body: JSON.stringify({
        username: username,
        password: password
      })
    });

    return request
      .then(response => {
        if (response.ok) {
          loginSuccess = true;
          return response.json();
        }
      })
      .then(data => {
        if (loginSuccess) {
          sessionStorage.setItem("accessToken", data.token);
          sessionStorage.setItem("login", true);
          window.location.replace('/');
          // fetch(
          //   local.baseUrl + "rest/user/getUserByUserName?userName=" + username,
          //   {
          //     method: "post",
          //     headers: new Headers({
          //       Accept: "application/json",
          //       "Content-Type": "application/json",
          //       Authorization: "Bearer " + data.token
          //     })
          //   }
          // )
          // .then(response => response.json())
          // .then(data => {
          //   sessionStorage.setItem("userName", data.userName);
          //   sessionStorage.setItem("traderId", data.userId === 1 ? "-1" : data.userId);
          //   sessionStorage.setItem("userId", data.userId);
          //   sessionStorage.setItem("userRole", JSON.stringify(data.roles));
          //   sessionStorage.setItem("userPermision", JSON.stringify(data.permissions));
          //   sessionStorage.setItem("createdDate", data.createdDate);
          //   // window.location.replace(process.env.PUBLIC_URL + "/ListSystemTrades?status=OpenReal");
          //   dispatch(
          //     setUserData({ data: data, role: sessionStorage.getItem("role") })
          //   );
          //   return dispatch({
          //     type: LOGIN_SUCCESS
          //   });
          // });

        } else {
          window.alert("Invalid Username or Password");
        }
      });
  }

  render() {
    return (
      <div className="app flex-row align-items-center">
        <Container>
          <Row className="justify-content-center">
            <Col md="8">
              <CardGroup>
                <Card className="p-4">
                  <CardBody>
                    <Form onSubmit={this.login}>
                      <h1>Login</h1>
                      <p className="text-muted">Sign In to your account</p>
                      <InputGroup className="mb-3">
                        <InputGroupAddon addonType="prepend">
                          <InputGroupText>
                            <i className="icon-user"></i>
                          </InputGroupText>
                        </InputGroupAddon>
                        <Input type="text" placeholder="Username" name="username" onChange={this.handleChange} autoComplete="username" required />
                      </InputGroup>
                      <InputGroup className="mb-4">
                        <InputGroupAddon addonType="prepend">
                          <InputGroupText>
                            <i className="icon-lock"></i>
                          </InputGroupText>
                        </InputGroupAddon>
                        <Input type="password" placeholder="Password" name="password" onChange={this.handleChange} autoComplete="current-password" required />
                      </InputGroup>
                      <Row>
                        <Col xs="6">
                          <Button color="primary" type="submit" className="px-4">Login</Button>
                        </Col>
                        <Col xs="6" className="text-right">
                          <Button color="link" className="px-0">Forgot password?</Button>
                        </Col>
                      </Row>
                    </Form>
                  </CardBody>
                </Card>
                <Card className="text-white bg-primary py-5 d-md-down-none" style={{ width: '44%' }}>
                  <CardBody className="text-center">
                    <div>
                      <h2>Sign up</h2>
                      <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut
                        labore et dolore magna aliqua.</p>
                      <Link to="/register">
                        <Button color="primary" className="mt-3" active tabIndex={-1}>Register Now!</Button>
                      </Link>
                    </div>
                  </CardBody>
                </Card>
              </CardGroup>
            </Col>
          </Row>
        </Container>
      </div>
    );
  }
}

export default Login;
