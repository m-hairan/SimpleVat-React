import React from 'react'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'
import { Card, CardHeader, CardBody, Button, Modal, ModalHeader, ModalBody, 
         ModalFooter, Row, Col, Input, FormGroup, Form, ButtonGroup } from 'reactstrap'
import { ToastContainer, toast } from 'react-toastify'
import { BootstrapTable, TableHeaderColumn, SearchField } from 'react-bootstrap-table'
import Loader from "components/loader"
import moment from 'moment'

import 'react-toastify/dist/ReactToastify.css'
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css'
import './style.scss'

import * as VatActions from './actions'


const mapStateToProps = (state) => {
  return ({
    vat_list: state.vat.vat_list
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    vatActions: bindActionCreators(VatActions, dispatch)
  })
}

class VatCategory extends React.Component {
  constructor(props) {
    super(props)

    this.state = {
      openDeleteModal: false,
      loading: true
    }

    this.selectRowProp = {
      mode: 'checkbox',
      bgColor: 'rgba(0,0,0, 0.05)',
      clickToSelect: true,
      onSelect: this.onRowSelect,
      onSelectAll: this.onSelectAll
    }

    this.deleteVat = this.deleteVat.bind(this)
    this.customSearchField = this.customSearchField.bind(this)
    this.success = this.success.bind(this)
    this.customTotal = this.customTotal.bind(this)
    this.vatPercentageFormat = this.vatPercentageFormat.bind(this)
    this.vatCreatedDateFormat = this.vatCreatedDateFormat.bind(this)
    this.versionNumFormat = this.versionNumFormat.bind(this)
    this.actions = this.actions.bind(this)

    this.closeModal = this.closeModal.bind(this)
  }

  // Table Custom Search Field
  customSearchField(props) {
    return (
      <SearchField
        defaultValue=''
        placeholder='Search ...'/>
    )
  }

  // Table Custom Pagination Label
  customTotal(from, to, size) {
    return (
      <span className="react-bootstrap-table-pagination-total">
        Showing {from} to {to} of {size} Results
      </span >
    )
  }

  // -------------------------
  // Data Table Custom Fields
  //--------------------------
  
  vatPercentageFormat(cell, row) {
    return(`${row.vat} %`)
  }

  vatCreatedDateFormat(cell, row) {
    return(`${moment(new Date(row.createdDate)).format('lll')}`)
  } 

  versionNumFormat(cell, row){
    return(<span className="badge badge-lg badge-info">{row.versionNumber}</span>)
  }

  actions(cell, row) {
    return (
      <div className="table-action text-right">
          <Button
            color="primary" 
            className="btn-pill vat-actions ml-1"
            title="Edit Vat Category" 
            onClick={() => this.props.history.push(`/admin/settings/vat-category/update?id=${row.id}`)}>
              <i className="far fa-edit"></i>
          </Button>
          <Button 
            color="primary" 
            className="btn-pill vat-actions ml-1" 
            title="Delete Vat Ctegory" 
            onClick={() => this.setState({ selectedData: row }, () => this.setState({ openDeleteModal: true }))}>
              <i className="fas fa-trash-alt"></i>
          </Button>
      </div>
    )
  }

  // Show Success Toast
  success() {
    return toast.success('Vat Category Deleted Successfully... ', {
        position: toast.POSITION.TOP_RIGHT
    })
  }

  
  componentDidMount() {
    this.getVatListData()
  }

  // Get All Vats
  getVatListData() {
    this.props.vatActions.getVatList().then(res => {
      if (res.status === 200) {
        this.setState({ loading: false })
      }
    })
  }

  // Delete Vat By ID
  deleteVat() {
    this.setState({ loading: true })
    this.setState({ openDeleteModal: false })
    this.props.vatActions.deleteVat(this.state.selectedData.id).then(res => {
      if (res.status === 200) {
        this.setState({ loading: false })
        this.getVatListData()
      }
    })
  }

  // Cloase Confirm Modal
  closeModal() {
    this.setState({ openDeleteModal: false })
  }

  render() {
    const { loading } = this.state
    const vatList = this.props.vat_list
    const containerStyle = {
        zIndex: 1999
    }

    return (
      <div className="vat-category-screen">
        <div className="animated">
          <ToastContainer position="top-right" autoClose={3000} style={containerStyle} />
          <Card>
            <CardHeader>
              <div className="h4 mb-0 d-flex align-items-center">
                <i className="nav-icon icon-briefcase" />
                <span className="ml-2">Vat Category</span>
              </div>
            </CardHeader>
            <CardBody>
              
            {
              loading ?
                <Loader></Loader>: 
                <Row>
                  <Col lg='12'>
                    <div className="mb-2">
                        <ButtonGroup className="toolbar" size="sm">
                          <Button
                            color="success"
                            className="btn-square"
                          >
                            <i className="fa glyphicon glyphicon-export fa-download mr-1" />
                            Export to CSV
                          </Button>
                          <Button
                            color="info"
                            className="btn-square"
                          >
                            <i className="fa glyphicon glyphicon-export fa-upload mr-1" />
                            Import from CSV
                          </Button>
                          <Button
                            color="primary"
                            className="btn-square"
                            onClick={() => this.props.history.push(`/admin/settings/vat-category/update`)}
                          >
                            <i className="fas fa-plus mr-1" />
                            New Category
                          </Button>
                          <Button
                            color="warning"
                            className="btn-square"
                          >
                            <i className="fa glyphicon glyphicon-trash fa-trash mr-1" />
                            Bulk Delete
                          </Button>
                        </ButtonGroup>
                        </div>
                        <div className="filter-panel my-3 p-3">
                          <Form inline>
                            <FormGroup className="pr-3">
                              <Input type="text" placeholder="Vat Name" />
                            </FormGroup>
                            <FormGroup className="pr-3">
                              <Input type="text" placeholder="Vat Percentage" />
                            </FormGroup>
                            
                            <Button
                              type="submit"
                              color="primary"
                              className="btn-square"
                            >
                              <i className="fas fa-search mr-1"></i>Filter
                            </Button>
                          </Form>
                        </div>
                        <BootstrapTable 
                          data={vatList}
                          hover
                          version="4"
                          pagination
                          search={true}
                          selectRow={ this.selectRowProp }
                        >
                          <TableHeaderColumn isKey dataField="name" dataSort={true}>
                            Vat Name
                          </TableHeaderColumn>
                          <TableHeaderColumn dataField="vat" dataSort={true} dataFormat={this.vatPercentageFormat}>
                            Vat Percentage
                          </TableHeaderColumn>
                          
                        </BootstrapTable>
                      </Col>
                    </Row>
            }
            </CardBody>
          </Card>
          <Modal isOpen={this.state.openDeleteModal}
              className={'modal-danger ' + this.props.className}>
              <ModalHeader toggle={this.toggleDanger}>Delete</ModalHeader>
              <ModalBody>
                  Are you sure want to delete this record?
            </ModalBody>
              <ModalFooter>
                  <Button color="danger" onClick={this.deleteVat}>Yes</Button>&nbsp;
                  <Button color="secondary" onClick={this.closeModal}>No</Button>
              </ModalFooter>
          </Modal>
        </div>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(VatCategory)
