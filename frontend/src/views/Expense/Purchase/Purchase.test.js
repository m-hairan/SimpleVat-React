import React from 'react';
import ReactDOM from 'react-dom';
import Purchase from './Purchase';
import { shallow } from 'enzyme'


it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<Purchase />, div);
  ReactDOM.unmountComponentAtNode(div);
});

it('renders without crashing', () => {
  shallow(<Purchase />);
});
