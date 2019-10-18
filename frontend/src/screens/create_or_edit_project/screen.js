import React from 'react'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'

import './style.scss'

const mapStateToProps = (state) => {
  return ({
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
  })
}

class CreateOrEditProject extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
      
    }

  }

  render() {

    return (
      <div className="create-or-edit-project-screen">
        <h1>CreateOrEditProject Screen</h1>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(CreateOrEditProject)
