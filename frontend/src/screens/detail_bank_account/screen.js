import React from 'react'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'

import {
  Row,
  Col,
  Card,
  CardHeader,
  CardBody,
  ListGroup,
  ListGroupItem,
  TabContent,
  TabPane
} from 'reactstrap'

import {
  BankStatementList,
  UpdateBankAccount,
  CreateBankStatement,
  ImportBankStatementsCSV
} from './sections'

import BankACcount from '../bank_account'


import './style.scss'

const mapStateToProps = (state) => {
  return ({
    bank_statement_list: state.bank.bank_statement_list
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    bankAccountActions: bindActionCreators(BankACcount.actions, dispatch)
  })
}

class DetailBankAccount extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      loading: false,
      activeTab: 0
    }

    this.toggleMenu = this.toggleMenu.bind(this)
  }

  toggleMenu(index) {
    this.setState({
      activeTab: index
    })
  }

  render() {

    return (
      <div className="detail-bank-account-screen">
        <div className="animated fadeIn">
          <Row>
            <Col lg={2}>
              <Card>
                <CardHeader>
                  <Row>
                    <Col lg={12}>
                      <div className="h4 menu-title mb-0 d-flex align-items-center">
                        <i className="fa fa-align-justify" />
                        <span className="ml-2">Menu</span>
                      </div>
                    </Col>
                  </Row>
                </CardHeader>
                <CardBody className="menu-container p-0">
                  <ListGroup id="list-tab" role="tablist">
                    <ListGroupItem
                      onClick={() => this.toggleMenu(0)}
                      action
                      active={this.state.activeTab === 0}
                    >
                      <i className="fas fa-university mr-1" />Bank Account
                    </ListGroupItem>
                    <ListGroupItem
                      onClick={() => this.toggleMenu(1)}
                      action
                      active={this.state.activeTab === 1}
                    >
                      <i className="icon-doc mr-1" />Bank Statements
                    </ListGroupItem>
                    <ListGroupItem
                      onClick={() => this.toggleMenu(2)}
                      action
                      active={this.state.activeTab === 2}
                    >
                      <i className="fas fa-plus mr-1" />New Statement
                    </ListGroupItem>
                    <ListGroupItem
                      onClick={() => this.toggleMenu(3)}
                      action
                      active={this.state.activeTab === 3}
                    >
                      <i className="fa glyphicon glyphicon-export fa-upload mr-1" />Import from CSV
                    </ListGroupItem>
                  </ListGroup>
                </CardBody>
              </Card>
            </Col>
            <Col lg={10}>
              <TabContent className="bank-detail-main-container" activeTab={this.state.activeTab}>
                <TabPane tabId={0} className="p-0">
                  <UpdateBankAccount {...this.props} />
                </TabPane>
                <TabPane tabId={1} className="p-0">
                  <BankStatementList
                    toggleMenu={this.toggleMenu}
                    {...this.props}
                  />
                </TabPane>
                <TabPane tabId={2} className="p-0">
                  <CreateBankStatement {...this.props} />
                </TabPane>
                <TabPane tabId={3} className="p-0">
                  <ImportBankStatementsCSV {...this.props} />
                </TabPane>
              </TabContent>
            </Col>
          </Row>
        </div>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(DetailBankAccount)
