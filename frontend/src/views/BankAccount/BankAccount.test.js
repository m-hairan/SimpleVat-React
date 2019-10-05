import React from 'react';
import ReactDOM from 'react-dom';
import BankAccount from './BankAccount';
import { shallow } from 'enzyme'


it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<BankAccount />, div);
  ReactDOM.unmountComponentAtNode(div);
});

it('renders without crashing', () => {
  shallow(<BankAccount />);
});
