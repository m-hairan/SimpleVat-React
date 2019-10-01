import React from 'react';
import ReactDOM from 'react-dom';
import VatCategory from './VatCategory';
import { shallow } from 'enzyme'


it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<VatCategory />, div);
  ReactDOM.unmountComponentAtNode(div);
});

it('renders without crashing', () => {
  shallow(<VatCategory />);
});
