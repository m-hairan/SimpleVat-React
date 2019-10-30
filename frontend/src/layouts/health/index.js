import React from 'react'
import { Route, Switch, Redirect } from 'react-router-dom'
import {connect} from 'react-redux'
import { bindActionCreators } from 'redux'

import { healthRoutes } from 'routes'
import {
  AuthActions,
  CommonActions
} from 'services/global'


const mapStateToProps = (state) => {
  return ({
  })
}
const mapDispatchToProps = (dispatch) => {
  return ({
    authActions: bindActionCreators(AuthActions, dispatch),
    commonActions: bindActionCreators(CommonActions, dispatch)
  })
}

class HealthLayout extends React.Component {

  constructor(props) {
    super(props)
    this.state = {
    }
  }

  render() {

    return (
      <div className="health-container">
        <Switch>
          {
            healthRoutes.map((prop, key) => {
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

export default connect(mapStateToProps, mapDispatchToProps)(HealthLayout)
