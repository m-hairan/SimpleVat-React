import React from 'react';
import ReactDOM from 'react-dom';
import TransactionReport from './TransactionReport';
import { shallow } from 'enzyme'


it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<TransactionReport />, div);
  ReactDOM.unmountComponentAtNode(div);
});

it('renders without crashing', () => {
  shallow(<TransactionReport />);
});
