import React from 'react';
import ReactDOM from 'react-dom';
import CreateOrEditVatCategory from './CreateOrEditVatCategory';
import { shallow } from 'enzyme'


it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<CreateOrEditVatCategory />, div);
    ReactDOM.unmountComponentAtNode(div);
});

it('renders without crashing', () => {
    shallow(<CreateOrEditVatCategory />);
});
