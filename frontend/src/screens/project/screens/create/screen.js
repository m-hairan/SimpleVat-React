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
  { value: 1, label: 'English'},
  { value: 2, label: 'Arabic'}
]

class CreateProject extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      openContactModel: false,
      loading: false,

      selectedContact: null,
      selectedCurrency: null,
      selectedInvoiceLanguage: null,

      selectedContactCountry: null,
      selectedContactCurrency: null,
      selectedContactTitle: null,

      initProjectValue: {
          projectName: '', 
          invoiceLanguageCode: '',
          contact:'',
          contractPoNumber : '',
          vatRegistrationNumber : '',
          projectExpenseBudget : '',
          projectRevenueBudget: '', 
          currency: ''
      },


      initContactValue: {
        title: '',
        billingEmail: '',
        city: '',
        country : '',
        currency: '',
        firstName: '',
        lastName: '',
        middleName: '',
        email: '',
        stateRegion: '',
        invoicingAddressLine1: '',
        invoicingAddressLine2: '' 
      },
    }

    this.showContactModel = this.showContactModel.bind(this)
    this.closeContactModel = this.closeContactModel.bind(this)
    
    this.projectHandleSubmit = this.projectHandleSubmit.bind(this)
    this.contactHandleSubmit = this.contactHandleSubmit.bind(this)
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
    this.props.projectActions.getProjectTitleList()
  }

  // Show Success Toast
  success() {
    // toast.success('Vat Code Updated successfully... ', {
    //   position: toast.POSITION.TOP_RIGHT
    // })
  }

  // Create or Edit Vat
  projectHandleSubmit(data) {
    this.props.projectActions.createAndSaveProject(data).then(res => {
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


  // Create or Contact
  contactHandleSubmit(data) {
    this.props.projectActions.createProjectContact(data).then(res => {
      if (res.status === 200) {
        // this.success()
        this.closeContactModel()
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
                          initialValues={this.state.initProjectValue}
                          onSubmit={(values, {resetForm}) => {
                            this.projectHandleSubmit(values)
                            resetForm(this.state.initProjectValue)
                          }}
                          validationSchema={Yup.object().shape({
                            projectName: Yup.string()
                              .required("Project Name is Required"),
                            contact: Yup.string()
                              .required("Contact is Required"),
                            currency: Yup.string()
                              .required("Currency is Required"),
                            contractPoNumber: Yup.string()
                              .required("Contract PO Number is Required"),
                            vatRegistrationNumber: Yup.string()
                              .required("VAT Registration Number is Required"),
                            projectExpenseBudget: Yup.string()
                              .required("Expense Budget is Required"),
                            projectRevenueBudget: Yup.string()
                              .required("Revenue Budget is Required"),
                            invoiceLanguageCode: Yup.string()
                              .required("Invoice Language is Required")
                          })}>
                            {props => (
                              <Form onSubmit={props.handleSubmit}>
                                <Row>
                                  <Col lg={4}>
                                    <FormGroup className="mb-3">
                                      <Label htmlFor="projectName">Project Name</Label>
                                      <Input
                                        type="text"
                                        id="name"
                                        name="projectName"
                                        onChange={props.handleChange}
                                        placeholder="Enter Project Name"
                                        value={props.values.projectName}
                                        className={
                                          props.errors.projectName && props.touched.projectName
                                            ? "is-invalid"
                                            : ""
                                        }
                                      />
                                      {props.errors.projectName && props.touched.projectName && (
                                        <div className="invalid-feedback">{props.errors.projectName}</div>
                                      )}
                                    </FormGroup>
                                  </Col>
                                  <Col lg={4}>
                                    <FormGroup className="mb-3">
                                      <Label htmlFor="contact">Contact</Label>
                                      <Select
                                        options={INVOICE_LANGUAGE_OPTIONS}
                                        onChange={(option) => {
                                          this.setState({
                                            selectedContact: option.value
                                          })
                                          props.handleChange("contact")(option.value);
                                        }}
                                        id="contact"
                                        name="contact"
                                        placeholder="Select Contact"
                                        value={this.state.selectedContact}
                                        className={
                                          props.errors.contact && props.touched.contact
                                            ? "is-invalid"
                                            : ""
                                        }
                                      />
                                      {props.errors.contact && props.touched.contact && (
                                        <div className="invalid-feedback">{props.errors.contact}</div>
                                      )}
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
                                      <Label htmlFor="contractPoNumber">Contract PO Number</Label>
                                      <Input
                                        type="text"
                                        id="contractPoNumber"
                                        name="contractPoNumber"
                                        onChange={props.handleChange}
                                        placeholder="Enter Contract PO Number"
                                        value={props.values.contractPoNumber}
                                        className={
                                          props.errors.contractPoNumber && props.touched.contractPoNumber
                                            ? "is-invalid"
                                            : ""
                                        }
                                      />
                                      {props.errors.contractPoNumber && props.touched.contractPoNumber && (
                                        <div className="invalid-feedback">{props.errors.contractPoNumber}</div>
                                      )}
                                    </FormGroup>
                                  </Col>
                                  <Col lg={4}>
                                    <FormGroup className="mb-3">
                                      <Label htmlFor="vatRegistrationNumber">VAT Registration Number</Label>
                                      <Input
                                        type="text"
                                        id="vatRegistrationNumber"
                                        name="vatRegistrationNumber"
                                        onChange={props.handleChange}
                                        placeholder="Enter VAT Registration Number"
                                        value={props.values.vatRegistrationNumber}
                                        className={
                                          props.errors.vatRegistrationNumber && props.touched.vatRegistrationNumber
                                            ? "is-invalid"
                                            : ""
                                        }
                                      />
                                      {props.errors.vatRegistrationNumber && props.touched.vatRegistrationNumber && (
                                        <div className="invalid-feedback">{props.errors.vatRegistrationNumber}</div>
                                      )}
                                    </FormGroup>
                                  </Col>
                                  <Col lg={4}>
                                    <FormGroup className="mb-3">
                                      <Label htmlFor="currency">Currency</Label>
                                      <Select
                                        className="select-default-width"
                                        options={INVOICE_LANGUAGE_OPTIONS}
                                        onChange={(option) => {
                                          this.setState({
                                            selectedCurrency: option.value
                                          })
                                          props.handleChange("currency")(option.value);
                                        }}
                                        placeholder="Select currency"
                                        value={this.state.selectedCurrency}
                                        id="currency"
                                        name="currency"
                                        className={
                                          props.errors.currency && props.touched.currency
                                            ? "is-invalid"
                                            : ""
                                        }
                                      />
                                      {props.errors.currency && props.touched.currency && (
                                        <div className="invalid-feedback">{props.errors.currency}</div>
                                      )}
                                    </FormGroup>
                                  </Col>
                                </Row>
                                <Row>
                                  <Col lg={4}>
                                    <FormGroup className="">
                                      <Label htmlFor="projectExpenseBudget">Expense Budget</Label>
                                      <Input
                                        type="number"
                                        id="projectExpenseBudget"
                                        name="projectExpenseBudget"
                                        onChange={props.handleChange}
                                        placeholder="Enter Expense Budgets"
                                        value={props.values.projectExpenseBudget}
                                        className={
                                          props.errors.projectExpenseBudget && props.touched.projectExpenseBudget
                                            ? "is-invalid"
                                            : ""
                                        }
                                      />
                                      {props.errors.projectExpenseBudget && props.touched.projectExpenseBudget && (
                                        <div className="invalid-feedback">{props.errors.projectExpenseBudget}</div>
                                      )}
                                    </FormGroup>
                                  </Col>
                                  <Col lg={4}>
                                    <FormGroup className="">
                                      <Label htmlFor="projectRevenueBudget">Revenue Budget</Label>
                                      <Input
                                        type="number"
                                        id="projectRevenueBudget"
                                        name="projectRevenueBudget"
                                        onChange={props.handleChange}
                                        placeholder="Enter VAT Revenue Budget"
                                        value={props.values.projectRevenueBudget}
                                        className={
                                          props.errors.projectRevenueBudget && props.touched.projectRevenueBudget
                                            ? "is-invalid"
                                            : ""
                                        }
                                      />
                                      {props.errors.projectRevenueBudget && props.touched.projectRevenueBudget && (
                                        <div className="invalid-feedback">{props.errors.projectRevenueBudget}</div>
                                      )}
                                    </FormGroup>
                                  </Col>
                                  <Col lg={4}>
                                    <FormGroup className="">
                                      <Label htmlFor="invoiceLanguageCode">Invoice Language</Label>
                                      <Select
                                        className="select-default-width"
                                        options={INVOICE_LANGUAGE_OPTIONS}
                                        id="invoiceLanguageCode"
                                        onChange={(option) => {
                                          this.setState({
                                            selectedInvoiceLanguage: option.value
                                          })
                                          props.handleChange("invoiceLanguageCode")(option.value);
                                        }}
                                        placeholder="Select invoiceLanguageCode"
                                        value={this.state.selectedInvoiceLanguage}
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
                                      <Button name="button" color="primary" className="btn-square mr-3" 
                                        onClick={() => {
                                          this.setState({readMore: true})
                                          props.handleSubmit()
                                        }}>
                                        <i className="fa fa-refresh"></i> Create and More
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
            className="modal-success contact-modal"
          >
          <Formik
            initialValues={this.state.initContactValue}
            onSubmit={(values, {resetForm}) => {
              this.contactHandleSubmit(values)
              resetForm(this.state.initContactValue)
            }}
            validationSchema={Yup.object().shape({
              title: Yup.string()
                .required(),
              billingEmail: Yup.string()
                .email()
                .required(),
              city: Yup.string()
                .required(),
              country: Yup.string()
                .required(),
              currency: Yup.string()
                .required(),
              firstName: Yup.string()
                .required(),
              lastName: Yup.string()
                .required(),
              middleName: Yup.string()
                .required(),
              email: Yup.string()
                .email()
                .required(),
              stateRegion: Yup.string()
                .required(),
              invoicingAddressLine1: Yup.string()
                .required(),
              invoicingAddressLine2: Yup.string()
                .required()
            })}>
              {props => (
            <Form name="simpleForm" onSubmit={props.handleSubmit}>
              <ModalHeader toggle={this.toggleDanger}>New Contact</ModalHeader>
              <ModalBody>
                <Row>
                  <Col>
                    <FormGroup>
                      <Label htmlFor="categoryCode"><span className="text-danger">*</span>Title</Label>
                      <Select
                        className="select-default-width"
                        options={INVOICE_LANGUAGE_OPTIONS}
                        id="title"
                        onChange={(option) => {
                          this.setState({
                            selectedContactTitle: option.value
                          })
                          props.handleChange("title")(option.value);
                        }}
                        placeholder="Select title"
                        value={this.state.selectedContactTitle}
                        name="title"
                        className={
                          props.errors.title && props.touched.title
                            ? "is-invalid"
                            : ""
                        }
                      />
                      {props.errors.title && props.touched.title && (
                        <div className="invalid-feedback">{props.errors.title}</div>
                      )}
                    </FormGroup>                 
                  </Col>
                </Row>
                <Row>
                  <Col>
                    <FormGroup>
                      <Label htmlFor="categoryName"><span className="text-danger">*</span>First Name</Label>
                      <Input
                        type="text"
                        id="firstName"
                        name="firstName"
                        onChange={props.handleChange}
                        placeholder="Enter firstName "
                        value={props.values.firstName }
                        className={
                          props.errors.firstName  && props.touched.firstName 
                            ? "is-invalid"
                            : ""
                        }
                      />
                      {props.errors.firstName  && props.touched.firstName  && (
                        <div className="invalid-feedback">{props.errors.firstName }</div>
                      )}
                    </FormGroup>
                  </Col>
                  <Col>
                    <FormGroup>
                      <Label htmlFor="categoryName"><span className="text-danger">*</span>Middle Name</Label>
                      <Input
                        type="text"
                        id="middleName"
                        name="middleName"
                        onChange={props.handleChange}
                        placeholder="Enter middleName  "
                        value={props.values.middleName}
                        className={
                          props.errors.middleName && props.touched.middleName  
                            ? "is-invalid"
                            : ""
                        }
                      />
                      {props.errors.middleName && props.touched.middleName && (
                        <div className="invalid-feedback">{props.errors.middleName}</div>
                      )}
                    </FormGroup>
                  </Col>
                  <Col>
                    <FormGroup>
                      <Label htmlFor="categoryName"><span className="text-danger">*</span>Last Name</Label>
                      <Input
                        type="text"
                        id="lastName"
                        name="lastName"
                        onChange={props.handleChange}
                        placeholder="Enter lastName   "
                        value={props.values.lastName  }
                        className={
                          props.errors.lastName && props.touched.lastName   
                            ? "is-invalid"
                            : ""
                        }
                      />
                      {props.errors.lastName && props.touched.lastName && (
                        <div className="invalid-feedback">{props.errors.lastName}</div>
                      )}
                    </FormGroup>
                  </Col>
                </Row>

                <Row>
                  <Col>
                    <FormGroup>
                      <Label htmlFor="categoryName"><span className="text-danger">*</span>Email</Label>
                      <Input
                        type="email"
                        id="email"
                        name="email"
                        onChange={props.handleChange}
                        placeholder="Enter email"
                        value={props.values.email   }
                        className={
                          props.errors.email && props.touched.email    
                            ? "is-invalid"
                            : ""
                        }
                      />
                      {props.errors.email && props.touched.email && (
                        <div className="invalid-feedback">{props.errors.email}</div>
                      )}
                    </FormGroup>
                  </Col>
                  <Col>
                    <FormGroup>
                      <Label htmlFor="categoryName"><span className="text-danger">*</span>Address Line 1</Label>
                      <Input
                        type="text"
                        id="invoicingAddressLine1"
                        name="invoicingAddressLine1"
                        onChange={props.handleChange}
                        placeholder="Enter invoicingAddressLine1"
                        value={props.values.invoicingAddressLine1}
                        className={
                          props.errors.invoicingAddressLine1 && props.touched.invoicingAddressLine1     
                            ? "is-invalid"
                            : ""
                        }
                      />
                      {props.errors.invoicingAddressLine1 && props.touched.invoicingAddressLine1 && (
                        <div className="invalid-feedback">{props.errors.invoicingAddressLine1}</div>
                      )}
                    </FormGroup>
                  </Col>
                  <Col>
                    <FormGroup>
                      <Label htmlFor="categoryName"><span className="text-danger">*</span>Address Line 2</Label>
                      <Input
                        type="text"
                        id="invoicingAddressLine2"
                        name="invoicingAddressLine2"
                        onChange={props.handleChange}
                        placeholder="Enter invoicingAddressLine2"
                        value={props.values.invoicingAddressLine2}
                        className={
                          props.errors.invoicingAddressLine2 && props.touched.invoicingAddressLine2     
                            ? "is-invalid"
                            : ""
                        }
                      />
                      {props.errors.invoicingAddressLine2 && props.touched.invoicingAddressLine2 && (
                        <div className="invalid-feedback">{props.errors.invoicingAddressLine2}</div>
                      )}
                    </FormGroup>
                  </Col>
                </Row>

                <Row>
                  <Col>
                    <FormGroup>
                      <Label htmlFor="categoryName"><span className="text-danger">*</span>State Region</Label>
                      <Input
                        type="text"
                        id="stateRegion"
                        name="stateRegion"
                        onChange={props.handleChange}
                        placeholder="Enter stateRegion"
                        value={props.values.stateRegion}
                        className={
                          props.errors.stateRegion && props.touched.stateRegion     
                            ? "is-invalid"
                            : ""
                        }
                      />
                      {props.errors.stateRegion && props.touched.stateRegion && (
                        <div className="invalid-feedback">{props.errors.stateRegion}</div>
                      )}
                    </FormGroup>
                  </Col>
                  <Col>
                    <FormGroup>
                      <Label htmlFor="categoryName"><span className="text-danger">*</span>City</Label>
                      <Input
                        type="text"
                        id="city"
                        name="city"
                        onChange={props.handleChange}
                        placeholder="Enter stateRegion"
                        value={props.values.city}
                        className={
                          props.errors.city && props.touched.city     
                            ? "is-invalid"
                            : ""
                        }
                      />
                      {props.errors.city && props.touched.city && (
                        <div className="invalid-feedback">{props.errors.city}</div>
                      )}
                    </FormGroup>
                  </Col>
                  <Col>
                    <FormGroup>
                      <Label htmlFor="categoryName"><span className="text-danger">*</span>Country</Label>
                      <Select
                        className="select-default-width"
                        options={INVOICE_LANGUAGE_OPTIONS}
                        id="country"
                        onChange={(option) => {
                          this.setState({
                            selectedContactCountry: option.value
                          })
                          props.handleChange("country")(option.value);
                        }}
                        placeholder="Select country"
                        value={this.state.selectedContactCountry}
                        name="country"
                        className={
                          props.errors.country && props.touched.country
                            ? "is-invalid"
                            : ""
                        }
                      />
                      {props.errors.country && props.touched.country && (
                        <div className="invalid-feedback">{props.errors.country}</div>
                      )}
                    </FormGroup>
                  </Col>
                </Row>

                <Row>
                  <Col>
                    <FormGroup>
                      <Label htmlFor="categoryName"><span className="text-danger">*</span>Currency Code</Label>
                      <Select
                        className="select-default-width"
                        options={INVOICE_LANGUAGE_OPTIONS}
                        id="currency"
                        onChange={(option) => {
                          this.setState({
                            selectedContactCurrency: option.value
                          })
                          props.handleChange("currency")(option.value);
                        }}
                        placeholder="Select currency"
                        value={this.state.selectedContactCurrency}
                        name="currency"
                        className={
                          props.errors.currency && props.touched.currency
                            ? "is-invalid"
                            : ""
                        }
                      />
                      {props.errors.currency && props.touched.currency && (
                        <div className="invalid-feedback">{props.errors.currency}</div>
                      )}
                    </FormGroup>
                  </Col>
                  <Col>
                    <FormGroup>
                      <Label htmlFor="categoryName"><span className="text-danger">*</span>Billing Email</Label>
                      <Input
                        type="text"
                        id="billingEmail"
                        name="billingEmail"
                        onChange={props.handleChange}
                        placeholder="Enter billingEmail"
                        value={props.values.billingEmail}
                        className={
                          props.errors.billingEmail && props.touched.billingEmail    
                            ? "is-invalid"
                            : ""
                        }
                      />
                      {props.errors.billingEmail && props.touched.billingEmail && (
                        <div className="invalid-feedback">{props.errors.billingEmail}</div>
                      )}
                    </FormGroup>
                  </Col>
                </Row>

              </ModalBody>
              <ModalFooter>
                <Button color="success" type="submit" className="btn-square">Save</Button>&nbsp;
                <Button color="secondary" className="btn-square" onClick={this.closeContactModel}>Cancel</Button>
              </ModalFooter>
            </Form>
            )}
          </Formik>
          </Modal>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(CreateProject)
