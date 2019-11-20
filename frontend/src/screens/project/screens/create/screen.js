import React from 'react'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'
import {
  Card,
  CardHeader,
  CardBody,
  Button,
  Row,
  Col,
  Form,
  FormGroup,
  Input,
  Label,
  Modal,
  ModalHeader, 
  ModalBody,
  ModalFooter,
} from 'reactstrap'
import Select from 'react-select'
import _ from 'lodash'

import { Formik } from 'formik';
import * as Yup from "yup";

import * as ProjectActions from '../../actions'

import './style.scss'

const mapStateToProps = (state) => {
  return ({

  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    projectActions: bindActionCreators(ProjectActions, dispatch)
  })
}


const INVOICE_LANGUAGE_OPTIONS = [
  { value: 'english', label: 'English'},
  { value: 'arabic', label: 'Arabic'}
]

class CreateProject extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      openContactModel: false,
      loading: false,

      projectData: {
        projectName: null,
        contact: null,
        invoiceLanguageCode: null,

      },
    }

    this.showContactModel = this.showContactModel.bind(this)
    this.closeContactModel = this.closeContactModel.bind(this)

    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
    this.success = this.success.bind(this)
  }


   // Show Invite User Modal
  showContactModel() {
    this.setState({ openContactModel: true })
  }
  // Cloase Confirm Modal
  closeContactModel() {
    this.setState({ openContactModel: false })
  }

  componentDidMount(){
    this.props.projectActions.getProjectCountryList()
    this.props.projectActions.getProjectCurrencyList()
  }

  // Show Success Toast
  success() {
    // toast.success('Vat Code Updated successfully... ', {
    //   position: toast.POSITION.TOP_RIGHT
    // })
  }

  // Save Updated Field's Value to State
  handleChange(e, name) {
    this.setState({
      vatData: _.set(
        { ...this.state.projectData },
        e.target.name && e.target.name !== '' ? e.target.name : name,
        e.target.type === 'checkbox' ? e.target.checked : e.target.value
      )
    })
  }

  // Create or Edit Vat
  handleSubmit(data) {
    this.props.vatActions.createAndSaveProject(data).then(res => {
      if (res.status === 200) {
        // this.success()

        if(this.state.readMore){
          this.setState({
            readMore: false
          })
        } else this.props.history.push('/admin/master/project')
      }
    })
  }

  render() {

    return (
      <div className="create-product-screen">
        <div className="animated fadeIn">
          <Row>
            <Col lg={12} className="mx-auto">
              <Card>
                <CardHeader>
                  <Row>
                    <Col lg={12}>
                      <div className="h4 mb-0 d-flex align-items-center">
                        <i className="nav-icon fas fa-project-diagram" />
                        <span className="ml-2">Create Project</span>
                      </div>
                    </Col>
                  </Row>
                </CardHeader>
                <CardBody>
                  <Row>
                    <Col lg={12}>
                      <Formik
                          initialValues={{name: '', invoiceLanguageCode: ''}}
                          onSubmit={(values, {resetForm}) => {
                            console.log(values)
                            alert()
                            // this.handleSubmit(values)
                            // resetForm({name: '', vat: ''})
                          }}
                          validationSchema={Yup.object().shape({
                            name: Yup.string()
                              .required("Vat Code Name is Required"),
                            invoiceLanguageCode: Yup.string()
                              .required("Invoice Language is Required")
                          })}>
                            {props => (
                              <Form onSubmit={props.handleSubmit}>
                                <Row>
                                  <Col lg={4}>
                                    <FormGroup className="mb-3">
                                      <Label htmlFor="name">Project Name</Label>
                                      <Input
                                        type="text"
                                        id="name"
                                        name="name"
                                        onChange={props.handleChange}
                                        placeholder="Enter Project Name"
                                        value={props.values.name}
                                        className={
                                          props.errors.name && props.touched.name
                                            ? "is-invalid"
                                            : ""
                                        }
                                      />
                                      {props.errors.name && props.touched.name && (
                                        <div className="invalid-feedback">{props.errors.name}</div>
                                      )}
                                    </FormGroup>
                                  </Col>
                                  <Col lg={4}>
                                    <FormGroup className="mb-3">
                                      <Label htmlFor="product_code">Contact</Label>
                                      <Input
                                        type="text"
                                        id="product_code"
                                        name="product_code"
                                        onChange={props.handleChange}
                                        placeholder="Enter Contact"
                                        required
                                      />
                                    </FormGroup>
                                    <FormGroup className="mb-5 text-right">
                                      <Button color="primary" className="btn-square " onClick={this.showContactModel}>
                                        <i className="fa fa-plus"></i> Add a Contact
                                      </Button>
                                    </FormGroup>
                                  </Col>
                                  
                                </Row>
                                <Row>
                                  <Col lg={4}>
                                    <FormGroup className="mb-3">
                                      <Label htmlFor="product_price">Contract PO Number</Label>
                                      <Input
                                        type="text"
                                        id="product_price"
                                        name="product_price"
                                        onChange={props.handleChange}
                                        placeholder="Enter Contract PO Number"
                                        required
                                      />
                                    </FormGroup>
                                  </Col>
                                  <Col lg={4}>
                                    <FormGroup className="mb-3">
                                      <Label htmlFor="product_price">VAT Registration Number</Label>
                                      <Input
                                        type="text"
                                        id="product_price"
                                        name="product_price"
                                        onChange={props.handleChange}
                                        placeholder="Enter VAT Registration Number"
                                        required
                                      />
                                    </FormGroup>
                                  </Col>
                                  <Col lg={4}>
                                    <FormGroup className="mb-3">
                                      <Label htmlFor="currency">Currency</Label>
                                      <Select
                                        className="select-default-width"
                                        options={[]}
                                        onChange={props.handleChange}
                                        id="currency"
                                        name="currency"
                                      />
                                    </FormGroup>
                                  </Col>
                                </Row>
                                <Row>
                                  <Col lg={4}>
                                    <FormGroup className="">
                                      <Label htmlFor="product_price">Expense Budget</Label>
                                      <Input
                                        type="text"
                                        id="product_price"
                                        name="product_price"
                                        onChange={props.handleChange}
                                        placeholder="Enter Expense Budgets"
                                        required
                                      />
                                    </FormGroup>
                                  </Col>
                                  <Col lg={4}>
                                    <FormGroup className="">
                                      <Label htmlFor="product_price">Revenue Budget</Label>
                                      <Input
                                        type="text"
                                        id="product_price"
                                        name="product_price"
                                        onChange={props.handleChange}
                                        placeholder="Enter VAT Revenue Budget"
                                        required
                                      />
                                    </FormGroup>
                                  </Col>
                                  <Col lg={4}>
                                    <FormGroup className="">
                                      <Label htmlFor="invoiceLanguageCode">Invoice Language</Label>
                                      <Select
                                        className="select-default-width"
                                        options={INVOICE_LANGUAGE_OPTIONS}
                                        id="invoiceLanguageCode"
                                        onChange={props.handleChange}
                                        name="invoiceLanguageCode"
                                        className={
                                          props.errors.invoiceLanguageCode && props.touched.invoiceLanguageCode
                                            ? "is-invalid"
                                            : ""
                                        }
                                      />
                                      {props.errors.invoiceLanguageCode && props.touched.invoiceLanguageCode && (
                                        <div className="invalid-feedback">{props.errors.invoiceLanguageCode}</div>
                                      )}
                                    </FormGroup>
                                  </Col>
                                </Row>
                                <Row>
                                  <Col lg={12} className="mt-5">
                                    <FormGroup className="text-right">
                                      <Button type="submit" color="primary" className="btn-square mr-3">
                                        <i className="fa fa-dot-circle-o"></i> Create
                                      </Button>
                                      <Button type="submit" color="primary" className="btn-square mr-3">
                                        <i className="fa fa-repeat"></i> Create and More
                                      </Button>
                                      <Button color="secondary" className="btn-square" 
                                        onClick={() => {this.props.history.push('/admin/master/project')}}>
                                        <i className="fa fa-ban"></i> Cancel
                                      </Button>
                                    </FormGroup>
                                  </Col>
                                </Row>
                              </Form>
                            )}
                        </Formik>
                    </Col>
                  </Row>
                </CardBody>
              </Card>
            </Col>
          </Row>
        </div>

        <Modal isOpen={this.state.openContactModel}
            className={"modal-success " + this.props.className}
          >
            <Form name="simpleForm">
              <ModalHeader toggle={this.toggleDanger}>Create & Update Currency</ModalHeader>
              <ModalBody>
                
                  <FormGroup>
                    <Label htmlFor="categoryCode">Currency Code</Label>
                    <Select
                      className="select-min-width"
                      options={[]}
                      placeholder="User Role"
                    />
                  </FormGroup>
                  <FormGroup>
                    <Label htmlFor="categoryName"><span className="text-danger">*</span>Currency Name</Label>
                    <Input
                      type="text"
                      id="categoryName"
                      name="categoryName"
                      placeholder="Enter Name"
                      required
                    />
                  </FormGroup>
                  <FormGroup>
                    <Label htmlFor="categoryCode"><span className="text-danger">*</span>Symbol</Label>
                    <Input
                      type="text"
                      id="categoryCode"
                      name="categoryCode"
                      placeholder="Enter Symbol"
                      required
                    />
                  </FormGroup>
                
              </ModalBody>
              <ModalFooter>
                <Button color="success" className="btn-square">Save</Button>&nbsp;
                <Button color="secondary" className="btn-square" onClick={this.closeCurrencyModal}>Cancel</Button>
              </ModalFooter>
            </Form>
          </Modal>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(CreateProject)
