import React, { Component } from 'react';
import { Card, CardHeader, CardBody, Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import { NavLink } from 'react-router-dom';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css';
import sendRequest from '../../xhrRequest';
import paginationFactory from 'react-bootstrap-table2-paginator';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

class BankAccount extends Component {
  constructor(props) {
    super(props);

    this.state = {
      bankAccountList: [],
      loading: true
    }

    // this.table = data.rows;
    this.options = {
      paginationSize: 5,
      sortIndicator: true,
      hideSizePerPage: true,
      hidePageListOnlyOnePage: true,
      clearSearch: true,
      alwaysShowAllBtns: false,
      withFirstAndLast: false,
      showTotal: true,
      paginationTotalRenderer: this.customTotal,
      sizePerPageList: [{
        text: '5', value: 5
      }, {
        text: '10', value: 10
      }, {
        text: 'All', value: this.state.bankAccountList ? this.state.bankAccountList.length : 0
      }]
    }

  }

  componentDidMount() {
    this.getBankListData();
  }

  getBankListData = () => {
    const res = sendRequest(`rest/bank/getbanklist`, "get", "");
    res.then((res) => {
      if (res.status === 200) {
        this.setState({ loading: false });
        return res.json();
      }
    }).then(data => {
      this.setState({ bankAccountList: data });
    })
  }

  success = () => {
    return toast.success('Bank Account Deleted Successfully... ', {
      position: toast.POSITION.TOP_RIGHT
    });
  }

  deleteBank = (data) => {
    this.setState({ loading: true })
    this.setState({ openDeleteModal: false });
    const res = sendRequest(`rest/bank/deletebank?id=${this.state.selectedData.bankAccountId}`, "delete", "");
    res.then(res => {
      if (res.status === 200) {
        this.setState({ loading: false });
        this.success();
        this.getBankListData();
      }
    })
  }

  bankAccounttActions = (cell, row) => {
    return (
      <div className="d-flex">
        <Button block color="primary" className="btn-pill vat-actions" title="Edit Vat Category" onClick={() => this.props.history.push(`/create-bank-account?id=${row.id}`)}><i className="far fa-edit"></i></Button>
        <Button block color="primary" className="btn-pill vat-actions" title="Transaction" onClick={() => this.props.history.push(`/create-bank-account?id=${row.id}`)}><i className="fas fa-university"></i></Button>
        <Button block color="primary" className="btn-pill vat-actions" title="Delete Vat Ctegory" onClick={() => this.setState({ selectedData: row }, () => this.setState({ openDeleteModal: true }))}><i className="fas fa-trash-alt"></i></Button>
      </div>
    );
  };

  setStatus = (cell, row) => row.bankAccountStatus.bankAccountStatusName;

  render() {
    const { bankAccountList, loading } = this.state;
    const containerStyle = {
      zIndex: 1999
    };
    return (
      <div className="animated">
        <ToastContainer position="top-right" autoClose={5000} style={containerStyle} />
        <Card>
          <CardHeader>
            <i className="icon-menu"></i>Bank Account
                    </CardHeader>
          <CardBody>
            <Button className="mb-3" onClick={() => this.props.history.push(`/create-bank-account`)}>New</Button>
            <BootstrapTable data={bankAccountList} version="4" striped hover pagination={paginationFactory(this.options)} totalSize={bankAccountList ? bankAccountList.length : 0} >
              <TableHeaderColumn isKey dataField="bankAccountName">Account Name</TableHeaderColumn>
              <TableHeaderColumn dataField="accountNumber" >Account Number</TableHeaderColumn>
              <TableHeaderColumn dataField="swiftCode" >Swift Code</TableHeaderColumn>
              <TableHeaderColumn dataFormat={this.setStatus} >Status</TableHeaderColumn>
              <TableHeaderColumn dataField="openingBalance" >Current Balance</TableHeaderColumn>
              <TableHeaderColumn dataFormat={this.bankAccounttActions}>Action</TableHeaderColumn>
            </BootstrapTable>
          </CardBody>
        </Card>
        <Modal isOpen={this.state.openDeleteModal}
          className={'modal-danger ' + this.props.className}>
          <ModalHeader toggle={this.toggleDanger}>Delete</ModalHeader>
          <ModalBody>
            Are you sure want to delete this record?
                  </ModalBody>
          <ModalFooter>
            <Button color="danger" onClick={this.deleteBank}>Yes</Button>{' '}
            <Button color="secondary" onClick={() => this.setState({ openDeleteModal: false })}>No</Button>
          </ModalFooter>
        </Modal>
        {
          loading ?
            <div className="sk-double-bounce loader">
              <div className="sk-child sk-double-bounce1"></div>
              <div className="sk-child sk-double-bounce2"></div>
            </div>
            : ""
        }
      </div>
    );
  }
}

export default BankAccount;
