import React from 'react'
import { connect } from 'react-redux'
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




            <div class="row filter">
              <div class="col-3">

                <button class="btn btn-secondary dropdown custom" type="button" id="dropdownMenu" data-toggle="dropdown1" aria-haspopup="true" aria-expanded="false">
                  <span>

                    All Customers
                     </span>
                  <i class="fa fa-angle-down arrow" ></i>
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenu">
                  <a class="dropdown-item" href="#">Item</a>
                  <a class="dropdown-item" href="#">Another item</a>
                  <a class="dropdown-item" href="#">One more item</a>
                </div>


              </div>
              <div class="col-2">
                <div class="dropdown1">
                  <button class="btn btn-secondary dropdown custom status" type="button" id="dropdownMenu1" data-toggle="dropdown1" aria-haspopup="true" aria-expanded="false">
                    All Status
  <i class="fa fa-angle-down arrow" ></i>
                  </button>
                  <div class="dropdown-menu" aria-labelledby="dropdownMenu">
                    <a class="dropdown-item" href="#">Item</a>
                    <a class="dropdown-item" href="#">Another item</a>
                    <a class="dropdown-item" href="#">One more item</a>
                  </div>
                </div>

              </div>
              <div class="col-sm">
                <div class="row">



                  <div class='col-6'>

                    <input type="email" class="form-control date" placeholder="From" />
                    <i class="fa fa-calendar" aria-hidden="true"></i>
                  </div>
                  <div class='col-6'>

                    <input type="email" class="form-control date" placeholder="From" />
                    <i class="fa fa-calendar" aria-hidden="true"></i>
                  </div>
                </div>
              </div>

              <div class="col-sm">
                <div class='row'>
                  <div class="col-10">

                    <input type="email" class="form-control search" placeholder="Enter Invoice #" />
                  </div>
                  <div class="col-2">
                    <button type="button" class="btn btn-secondary search">
                      <i class="fa fa-search" aria-hidden="true"></i>

                    </button>
                  </div>

                </div>

              </div>


            </div>
          </div>

          <div class="row title">
            <div class="col-1">
              Unpaid(3)
    </div>
            <div class="col-1">
              Draft(0)
    </div>
            <div class="col-1 all" >
              All Invoices
    </div>
          </div>

          <div class='row'>
            <div class='col-sm'>
              <table class="table">
                <thead>
                  <tr className='table-headings'>
                    <th scope="col">Status</th>
                    <th scope="col">Date</th>
                    <th scope="col">Number</th>
                    <th scope="col">Customer</th>
                    <th scope="col">Total</th>
                    <th scope="col">Amount Due</th>
                    <th scope="col">Actions</th>



                  </tr>
                </thead>
                <tbody className='table-body'>
                  <tr>
                    <th scope="row">
                      <button type="button" class="btn btn-secondary custom">Unsent</button>
                    </th>
                    <td>2018-10-03</td>
                    <td>136</td>
                    <td>Master of Buckland</td>
                    <td>$120.20</td>
                    <td>$128.20</td>
                    <td className='col-end'>
                      <span className='action'>
                        Send
                  </span>
                      <span>

                        <i class="fa fa-chevron-circle-down" aria-hidden="true"></i>
                      </span>
                    </td>


                  </tr>
                  <tr>
                    <th scope="row">
                      <button type="button" class="btn btn-success custom">Paid</button>
                    </th>
                    <td>2018-10-03</td>
                    <td>136</td>
                    <td>Master of Buckland</td>
                    <td>$120.20</td>
                    <td>$128.20</td>
                    <td className='col-end'>
                      <span className='action'>

                        View
                  </span>
                      <span>

                        <i class="fa fa-chevron-circle-down" aria-hidden="true"></i>
                      </span>
                    </td>


                  </tr>
                  <tr>
                    <th scope="row">
                      <button type="button" class="btn btn-primary custom">Sent</button>
                    </th>
                    <td>2018-10-03</td>
                    <td>136</td>
                    <td>Master of Buckland</td>
                    <td>$120.20</td>
                    <td>$128.20</td>
                    <td className='col-end'>
                      <span className='action'>

                        Record a Payment
                  </span>
                      <span>

                        <i class="fa fa-chevron-circle-down" aria-hidden="true"></i>
                      </span>
                    </td>


                  </tr>

                  <tr>
                    <th scope="row">
                      <button type="button" class="btn btn-danger custom">Overdue</button>
                    </th>
                    <td>2018-10-03</td>
                    <td>136</td>
                    <td>Master of Buckland</td>
                    <td>$120.20</td>
                    <td>$128.20</td>
                    <td className='col-end'>
                      <span className='action'>

                        Send a Reminder
                  </span>
                      <span>

                        <i class="fa fa-chevron-circle-down" aria-hidden="true"></i>
                      </span>
                    </td>


                  </tr>


                </tbody>
              </table>


            </div>
          </div>



          <div class="row">
            <div class="col-sm">
              <div></div>
              <div class='row'>
                <div class='col-12 limit'>
                  <p>Show:</p>
                  <span>
                    <button class="btn btn-secondary dropdown custom status" type="button" id="dropdownMenuPage2" data-toggle="dropdown1" aria-haspopup="true" aria-expanded="false">
                      25
  <i class="fa fa-angle-down arrow1" ></i>
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenu">
                      <a class="dropdown-item" href="#">Item</a>
                      <a class="dropdown-item" href="#">Another item</a>
                      <a class="dropdown-item" href="#">One more item</a>
                    </div>



                  </span>
                  <span>
                    <p>Per Page</p>
                  </span>
                </div>


              </div>
            </div>

            <div class="col-sm">
              <div className='custom-pagination'>

                <p>1-4 of 4</p> <button className='left'>
                  <i class="fa fa-angle-left" aria-hidden="true"></i>
                </button>  <button className='left'>
                  <i class="fa fa-angle-right" aria-hidden="true"></i>
                </button>
              </div>
            </div>

          </div>
        </div>

      </div>




    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Invoice)
