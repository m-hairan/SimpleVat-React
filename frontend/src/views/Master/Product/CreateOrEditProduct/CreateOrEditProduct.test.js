import React from 'react';
import ReactDOM from 'react-dom';
import CreateOrEditProduct from './CreateOrEditProduct';
import { shallow } from 'enzyme'


it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<CreateOrEditProduct />, div);
    ReactDOM.unmountComponentAtNode(div);
});

it('renders without crashing', () => {
    shallow(<CreateOrEditProduct />);
});
