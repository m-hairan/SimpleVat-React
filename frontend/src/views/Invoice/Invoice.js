import React, { Component } from 'react';
import { Card, CardHeader, CardBody } from 'reactstrap';
import { NavLink } from 'react-router-dom';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';
import data from './_data';
import 'react-bootstrap-table/dist//react-bootstrap-table-all.min.css';
import sendRequest from 'request';

class Invoice extends Component {
  constructor(props) {
    super(props);

    this.state = {
      invoiceList: []
    }

    // this.table = data.rows;
    this.options = {
      sortIndicator: true,
      hideSizePerPage: true,
      paginationSize: 3,
      hidePageListOnlyOnePage: true,
      clearSearch: true,
      alwaysShowAllBtns: false,
      withFirstAndLast: false
    }

  }

  componentDidMount() {

  }

  // just an example
  nameFormat(cell, row) {
    const id = `/users/${row.id}`
    return (
      <NavLink strict to={id}> {cell} </NavLink>
    );
  };

  render() {
    return (
      <div className="animated">
        <Card>
          <CardHeader>
            <i className="icon-menu"></i>Invoices
          </CardHeader>
          <CardBody>
            <BootstrapTable data={this.table} version="4" striped hover pagination search options={this.options}>
              <TableHeaderColumn dataField="name" dataSort dataFormat={this.nameFormat} >Name</TableHeaderColumn>
              <TableHeaderColumn isKey dataField="email">Email</TableHeaderColumn>
              <TableHeaderColumn dataField="age" dataSort>Age</TableHeaderColumn>
              <TableHeaderColumn dataField="city" dataSort>City</TableHeaderColumn>
            </BootstrapTable>
          </CardBody>
        </Card>
      </div>
    );
  }
}

export default Invoice;
