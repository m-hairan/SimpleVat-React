import React from 'react'

import './style.scss'

class Table extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
    }

  }

  render() {

    return (
      <div className="table-section">
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
    )
  }
}

export default Table


