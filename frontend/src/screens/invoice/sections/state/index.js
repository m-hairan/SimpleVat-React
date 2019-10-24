import React from 'react'

import './style.scss'

class State extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
    }

  }

  render() {

    return (
      <div className="state-section">

       <h3 className='invoice'>Invoices</h3>
            <div className='invoice-stats'>
              <div className='stat-container'>
                <div class="row">
                  <div class="col-sm">
                    <h4 className='stats-title'>Overdue</h4>
                    <h3 className='stats-value'> $53.36 <em>USD</em></h3>
                  </div>
                  <div class="col-sm">
                    <h4 className='stats-title'>Comming due within 30 days</h4>
                    <h3 className='stats-value'> $220.28 <em>USD</em></h3>
                  </div>
                  <div class="col-sm">
                    <h4 className='stats-title'>Average time get paid</h4>
                    <h3 className='stats-value'> 0 <em>day</em></h3>
                  </div>
                  <div class="col-sm">
                    <h4 className='stats-title'>Next payment</h4>
                    <h3 className='stats-value'> None  <i class="fa fa-question-circle-o" aria-hidden="true"></i></h3>
                  </div>
                </div>
              </div>
              <div className='update'>
                Last updated just a moment ago. <span className='refresh'>
                  Refresh
                </span>
              </div>
            </div>

      </div>
    )
  }
}

export default State


