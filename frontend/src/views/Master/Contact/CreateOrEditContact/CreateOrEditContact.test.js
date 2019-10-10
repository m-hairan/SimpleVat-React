import React from 'react';
import ReactDOM from 'react-dom';
import CreateOrEditContact from './CreateOrEditContact';
import { shallow } from 'enzyme'


it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<CreateOrEditContact />, div);
    ReactDOM.unmountComponentAtNode(div);
});

it('renders without crashing', () => {
    shallow(<CreateOrEditContact />);
});
