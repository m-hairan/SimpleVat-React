import React from 'react';
import ReactDOM from 'react-dom';
import CreateOrEditInvoice from './CreateOrEditInvoice';
import { shallow } from 'enzyme'


it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<CreateOrEditInvoice />, div);
    ReactDOM.unmountComponentAtNode(div);
});

it('renders without crashing', () => {
    shallow(<CreateOrEditInvoice />);
});
