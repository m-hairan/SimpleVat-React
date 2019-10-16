import React, { Component } from 'react'
import PropTypes from 'prop-types'

const propTypes = {
  children: PropTypes.node
}

const defaultProps = {}

class Footer extends Component {
  
  render() {

    const { children, ...attributes } = this.props

    return (
      <React.Fragment>
        <span><a href="https://coreui.io">CoreUI</a> &copy; 2019 creativeLabs.</span>
        <span className="ml-auto">Powered by <a href="https://coreui.io/react">CoreUI for React</a></span>
      </React.Fragment>
    );
  }
}

Footer.propTypes = propTypes
Footer.defaultProps = defaultProps

export default Footer
