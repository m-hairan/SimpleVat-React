import React from 'react'

import './style.scss'

class Filter extends React.Component {
  
  constructor(props) {
    super(props)
    this.state = {
    }

  }

  render() {

    return (
      <div className="filter-section">

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
    )
  }
}

export default Filter


