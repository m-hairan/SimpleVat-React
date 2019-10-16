import React from 'react'
import { Route, Switch, Redirect } from 'react-router-dom'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'

import { adminRoutes } from 'routes'
import {
  UserActions,
  CommonActions
} from 'services/global'


const mapStateToProps = (state) => {
  return ({
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    userActions: bindActionCreators(UserActions, dispatch),
    commonActions: bindActionCreators(CommonActions, dispatch)
  })
}

class AdminLayout extends React.Component {

  constructor(props) {
    super(props)
    this.state = {
    }
  }

  render() {

    return (
      <div className="initial-container">
        <Switch>
          {
            adminRoutes.map((prop, key) => {
              if (prop.redirect)
                return <Redirect from={prop.path} to={prop.pathTo} key={key} />
              return (
                <Route
                  path={prop.path}
                  component={prop.component}
                  key={key}
                />
              )
            })
          }
        </Switch>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(AdminLayout)
