import React from 'react';
import ReactDOM from 'react-dom';
import InvoiceReport from './InvoiceReport';
import { shallow } from 'enzyme'


it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<InvoiceReport />, div);
  ReactDOM.unmountComponentAtNode(div);
});

it('renders without crashing', () => {
  shallow(<InvoiceReport />);
});
