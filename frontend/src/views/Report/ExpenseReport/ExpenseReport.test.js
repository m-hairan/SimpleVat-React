import React from 'react';
import ReactDOM from 'react-dom';
import ExpenseReport from './ExpenseReport';
import { shallow } from 'enzyme'


it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<ExpenseReport />, div);
  ReactDOM.unmountComponentAtNode(div);
});

it('renders without crashing', () => {
  shallow(<ExpenseReport />);
});
