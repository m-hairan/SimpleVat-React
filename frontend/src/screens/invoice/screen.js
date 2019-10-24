import React from 'react'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import State from './sections/state'
import Filter from './sections/filter'
import Table from './sections/table'

import './style.scss'

const mapStateToProps = (state) => {
  return ({
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
  })
}

class Invoice extends React.Component {

  constructor(props) {
    super(props)
    this.state = {

    }

  }

  render() {

    return (
      <div className="invoice-screen">

        <div class="card">
          <div class="card-body">
            <State {...this.props} />
            <Filter {...this.props} />
            <Table {...this.props}/>

          </div>
        </div>

      </div>




    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Invoice)
