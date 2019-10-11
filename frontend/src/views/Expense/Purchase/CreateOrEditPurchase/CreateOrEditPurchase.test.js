import React from 'react';
import ReactDOM from 'react-dom';
import CreateOrEditPurchase from './CreateOrEditPurchase';
import { shallow } from 'enzyme'


it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<CreateOrEditPurchase />, div);
    ReactDOM.unmountComponentAtNode(div);
});

it('renders without crashing', () => {
    shallow(<CreateOrEditPurchase />);
});
