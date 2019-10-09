import React from 'react';
import ReactDOM from 'react-dom';
import CreateOrEditExpense from './CreateOrEditExpense';
import { shallow } from 'enzyme'


it('renders without crashing', () => {
    const div = document.createElement('div');
    ReactDOM.render(<CreateOrEditExpense />, div);
    ReactDOM.unmountComponentAtNode(div);
});

it('renders without crashing', () => {
    shallow(<CreateOrEditExpense />);
});
