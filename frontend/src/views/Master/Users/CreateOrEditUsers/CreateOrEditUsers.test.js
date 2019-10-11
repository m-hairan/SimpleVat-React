import React from 'react';
import ReactDOM from 'react-dom';
import CreateOrEditUsers from './CreateOrEditUsers';
import { shallow } from 'enzyme'


it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<CreateOrEditUsers />, div);
    ReactDOM.unmountComponentAtNode(div);
});

it('renders without crashing', () => {
    shallow(<CreateOrEditUsers />);
});
