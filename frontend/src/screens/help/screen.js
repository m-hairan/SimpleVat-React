import React from 'react'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'
import {
  Card,
  CardBody,
  CardHeader,
  Col,
  Form,
  FormGroup,
  Input,
  Label,
  Row,
  Table,
  Button
} from "reactstrap"

import './style.scss'

const mapStateToProps = (state) => {
  return ({
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
  })
}

class Help extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      
    }

  }

  render() {
    const faqIcon = require('assets/images/settings/faq.png')
    const userIcon = require('assets/images/settings/user.png')

    return (
      <div className="help-screen">
        <Row>
          <Col lg='8' className="mx-auto my-auto">
            <div className="search-box">
              <input className="form-control" placeholder="Search question ..."></input>
              <Card className="mt-4">
                <CardBody>
                  <h3>Question title</h3>
                  <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Scelerisque in dictum non consectetur a erat nam. Metus aliquam eleifend mi in nulla posuere sollicitudin aliquam ultrices. Viverra mauris in aliquam sem fringilla ut morbi tincidunt. Quis eleifend quam adipiscing vitae proin. Metus aliquam eleifend mi in nulla posuere sollicitudin aliquam. Placerat orci nulla pellentesque dignissim. Adipiscing commodo elit at imperdiet dui accumsan sit amet. Risus ultricies tristique nulla aliquet enim tortor at. Et tortor at risus viverra.
Nunc sed velit dignissim sodales ut eu sem integer. Nunc pulvinar sapien et ligula ullamcorper malesuada proin. Amet consectetur adipiscing elit ut aliquam purus sit amet. Sit amet nisl purus in mollis. Sodales ut eu sem integer vitae justo eget magna. Tellus orci ac auctor augue mauris augue neque gravida. Sagittis purus sit amet volutpat consequat mauris. Mauris a diam maecenas sed enim ut sem viverra. Eu non diam phasellus vestibulum lorem sed risus ultricies. Mattis ullamcorper velit sed ullamcorper morbi tincidunt. In cursus turpis massa tincidunt dui ut ornare lectus. Viverra nibh cras pulvinar mattis nunc sed blandit. Sed odio morbi quis commodo odio aenean sed. Ultricies mi quis hendrerit dolor. Sit amet massa vitae tortor condimentum lacinia. Eu mi bibendum neque egestas. Sit amet cursus sit amet dictum sit. Ultricies mi quis hendrerit dolor magna eget est lorem ipsum. Arcu non sodales neque sodales. </p>
                </CardBody>
              </Card>
            </div>
            <Row>
              <Col md="6">
                <Card>
                  <CardBody>
                    <div><img src={faqIcon} width="40%"></img></div>
                    <h3>Have a question?</h3>
                    <p>
                      Find detailed answers to the most common questions you might have while using our site
                    </p>
                    <a href="">Go to FAQ</a>
                  </CardBody>
                </Card>
              </Col>
              <Col md="6">
                <Card>
                  <CardBody>
                    <div><img src={userIcon} width="40%"></img></div>
                    <h3>Customer Support</h3>
                    <p>
                      Find detailed answers to the most common questions you might have while using our site
                    </p>
                    <div className="d-flex">
                      <button className="btn-pill btn btn-danger btn-lg">
                        <i className="icon-phone icons font-2xl d-block"></i>
                      </button>
                      <button className="btn-pill btn btn-danger btn-lg">
                        <i className="cui-comment-square icons font-2xl d-block"></i>
                      </button>
                      <button className="btn-pill btn btn-danger btn-lg">
                        <i className="cui-envelope-closed icons font-2xl d-block" style={{marginTop: -5}}></i>
                      </button>
                    </div>
                  </CardBody>
                </Card>
              </Col>
            </Row>
          </Col>
        </Row>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Help)
