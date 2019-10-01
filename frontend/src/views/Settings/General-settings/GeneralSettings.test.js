import React from 'react';
import ReactDOM from 'react-dom';
import GeneralSettings from './GeneralSettings';
import { shallow } from 'enzyme'


it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<GeneralSettings />, div);
  ReactDOM.unmountComponentAtNode(div);
});

it('renders without crashing', () => {
  shallow(<GeneralSettings />);
});
