import React, { Component } from 'react'
import {
  DropdownItem,
  DropdownMenu,
  DropdownToggle,
  Nav,
  UncontrolledDropdown
} from 'reactstrap'
import PropTypes from 'prop-types'

import {
  AppAsideToggler,
  AppNavbarBrand,
  AppSidebarToggler
} from '@coreui/react'

import logo from 'assets/images/brand/logo.svg'
import sygnet from 'assets/images/brand/sygnet.svg'
import avatar from 'assets/images/avatars/6.jpg'

const propTypes = {
  children: PropTypes.node
}

const defaultProps = {}

class Header extends Component {

  constructor(props) {
    super(props)
    this.state = {

    }

    this.signOut = this.signOut.bind(this)
  }

  signOut (e) {
    e.preventDefault()
    this.props.history.push('/login')
  }

  render() {
    const { children, ...attributes } = this.props

    return (
      <React.Fragment>
        <AppSidebarToggler className="d-lg-none" display="md" mobile />
        <AppNavbarBrand
          full={{ src: logo, width: 89, height: 25, alt: 'CoreUI Logo' }}
          minimized={{ src: sygnet, width: 30, height: 30, alt: 'CoreUI Logo' }}
        />
        <AppSidebarToggler className="d-md-down-none" display="lg" />
        <Nav className="ml-auto" navbar>
          <UncontrolledDropdown nav direction="down">
            <DropdownToggle nav>
              <img src={avatar} className="img-avatar" alt="admin@bootstrapmaster.com" />
            </DropdownToggle>
            <DropdownMenu right style={{ height: 'auto', right: 0 }}>
              <DropdownItem>
                <DropdownItem onClick={this.signOut}><i className="fa fa-lock"></i> Logout</DropdownItem>
              </DropdownItem>
            </DropdownMenu>
          </UncontrolledDropdown>
        </Nav>
        <AppAsideToggler className="d-md-down-none" />
      </React.Fragment>
    );
  }
}

Header.propTypes = propTypes
Header.defaultProps = defaultProps

export default Header
