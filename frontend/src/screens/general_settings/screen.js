import React from 'react'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'
import { Editor } from "react-draft-wysiwyg"
import { EditorState } from "draft-js"

import {
  Card,
  CardBody,
  CardHeader,
  Col,
  Form,
  FormGroup,
  Input,
  Label,
  Row
} from "reactstrap"

import './style.scss'
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css"

const mapStateToProps = (state) => {
  return ({
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
  })
}

class GeneralSettings extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      editorState: EditorState.createEmpty()
    }

    this.onEditorStateChange = this.onEditorStateChange.bind(this)
  }

  onEditorStateChange(editorState) {
    this.setState({
      editorState,
    })
  }

  render(){
    const { editorState } = this.state;

    return (
      <div className="general-settings-screen">
        <div className="animated fadeIn">
          <Row>
            <Col xs="12">
              <Card>
                <CardHeader> 
                  <i className="icon-menu" />
                  General Details
                </CardHeader>
                <CardBody>
                  <Form
                    action=""
                    method="post"
                    encType="multipart/form-data"
                    className="form-horizontal"
                  >
                    <FormGroup row>
                      <Col md="3">
                        <Label htmlFor="text-input">
                          Invoicing Refrence Pattern
                        </Label>
                      </Col>
                      <Col xs="12" md="9">
                        <Input
                          type="text"
                          id="text-input"
                          name="text-input"
                          placeholder="Text"
                        />
                      </Col>
                    </FormGroup>
                    <FormGroup row>
                      <Col md="3">
                        <Label htmlFor="select">Invoicing Templates</Label>
                      </Col>
                      <Col xs="12" md="9">
                        <Input type="select" name="select" id="select">
                          <option value="0">Please select</option>
                          <option value="1">Option #1</option>
                          <option value="2">Option #2</option>
                          <option value="3">Option #3</option>
                        </Input>
                      </Col>
                    </FormGroup>
                  </Form>
                </CardBody>
              </Card>
              <Card>
                <CardHeader>
                  <i className="icon-menu" />
                  Mail Configuration Detail
                </CardHeader>
                <CardBody>
                  <Form
                    action=""
                    method="post"
                    encType="multipart/form-data"
                    className="form-horizontal"
                  >
                    <FormGroup row>
                      <Col md="3">
                        <Label htmlFor="text-input">Mailing Host</Label>
                      </Col>
                      <Col xs="12" md="9">
                        <Input type="text" id="text-input" name="text-input" />
                      </Col>
                    </FormGroup>
                    <FormGroup row>
                      <Col md="3">
                        <Label htmlFor="text-input">Mailing Post</Label>
                      </Col>
                      <Col xs="12" md="9">
                        <Input type="text" id="text-input" name="text-input" />
                      </Col>
                    </FormGroup>
                    <FormGroup row>
                      <Col md="3">
                        <Label htmlFor="text-input">Mailing UserName</Label>
                      </Col>
                      <Col xs="12" md="9">
                        <Input type="text" id="text-input" name="text-input" />
                      </Col>
                    </FormGroup>
                    <FormGroup row>
                      <Col md="3">
                        <Label htmlFor="text-input">Mailing Password</Label>
                      </Col>
                      <Col xs="12" md="9">
                        <Input type="text" id="text-input" name="text-input" />
                      </Col>
                    </FormGroup>
                    <FormGroup row>
                      <Col md="3">
                        <Label>Mailing SMTP Authorization</Label>
                      </Col>
                      <Col md="9">
                        <div>
                          <FormGroup check inline>
                            <div className="custom-radio custom-control">
                              <input 
                                className="custom-control-input"
                                type="radio"
                                id="inline-radio1"
                                name="SMTP-auth"
                                value="Y"
                                checked
                                onChange={this.handleChange} />
                              <label className="custom-control-label" htmlFor="inline-radio1">Yes</label>
                            </div>
                          </FormGroup>
                          <FormGroup check inline>
                            <div className="custom-radio custom-control">
                              <input 
                                className="custom-control-input"
                                type="radio"
                                id="inline-radio2"
                                name="SMTP-auth"
                                value="N"
                                onChange={this.handleChange}/>
                              <label className="custom-control-label" htmlFor="inline-radio2">No</label>
                            </div>
                          </FormGroup>
                        </div>
                      </Col>
                    </FormGroup>
                    <FormGroup row>
                      <Col md="3">
                        <Label>Mailing Smtp Starttls Enable</Label>
                      </Col>
                      <Col md="9">
                        <div>
                          <FormGroup check inline>
                            <div className="custom-radio custom-control">
                              <input 
                                className="custom-control-input"
                                type="radio"
                                id="inline-radio3"
                                name="SMTP-enable"
                                value="Y"
                                checked
                                onChange={this.handleChange} />
                              <label className="custom-control-label" htmlFor="inline-radio3">Yes</label>
                            </div>
                          </FormGroup>
                          <FormGroup check inline>
                            <div className="custom-radio custom-control">
                              <input 
                                className="custom-control-input"
                                type="radio"
                                id="inline-radio4"
                                name="SMTP-enable"
                                value="N"
                                onChange={this.handleChange}/>
                              <label className="custom-control-label" htmlFor="inline-radio4">No</label>
                            </div>
                          </FormGroup>
                        </div>
                      </Col>
                    </FormGroup>
                  </Form>
                </CardBody>
              </Card>
              <Card>
                <CardHeader>
                  <i className="icon-menu" />
                  Invoice Mail Configuration
                </CardHeader>
                <CardBody>
                  <Form
                    action=""
                    method="post"
                    encType="multipart/form-data"
                    className="form-horizontal"
                  >
                    <FormGroup row>
                      <Col md="3">
                        <Label htmlFor="text-input">Subject</Label>
                      </Col>
                      <Col xs="12" md="9">
                        <Input
                          type="text"
                          id="text-input"
                          name="text-input"
                          placeholder="Text"
                        />
                      </Col>
                    </FormGroup>
                    <FormGroup row>
                      <Col md="3">
                        <Label htmlFor="text-input">Message</Label>
                      </Col>
                      <Col xs="12" md="9">
                      <Editor
                        editorState={editorState}
                        toolbarClassName="editor-toolbar"
                        wrapperClassName="wrapperClassName"
                        editorClassName="massage-editor"
                        onEditorStateChange={this.onEditorStateChange}
                      />
                      </Col>
                    </FormGroup>
                    
                  </Form>
                </CardBody>
              </Card>
            </Col>
          </Row>
        </div>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(GeneralSettings)
