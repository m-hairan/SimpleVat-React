import React from 'react';
import ReactDOM from 'react-dom';
import CreateOrEditBankAccount from './CreateOrEditBankAccount';
import { shallow } from 'enzyme'


it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<CreateOrEditBankAccount />, div);
    ReactDOM.unmountComponentAtNode(div);
});

it('renders without crashing', () => {
    shallow(<CreateOrEditBankAccount />);
});
