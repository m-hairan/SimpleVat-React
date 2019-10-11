import React from 'react';
import ReactDOM from 'react-dom';
import CreateOrEditProject from './CreateOrEditProject';
import { shallow } from 'enzyme'


it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<CreateOrEditProject />, div);
    ReactDOM.unmountComponentAtNode(div);
});

it('renders without crashing', () => {
    shallow(<CreateOrEditProject />);
});
