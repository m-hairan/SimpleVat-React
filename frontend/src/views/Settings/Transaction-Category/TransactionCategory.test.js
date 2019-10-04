import React from "react";
import ReactDOM from "react-dom";
import TransactionCategory from "./TransactionCategory";
import { shallow } from "enzyme";

it("renders without crashing", () => {
  const div = document.createElement("div");
  ReactDOM.render(<TransactionCategory />, div);
  ReactDOM.unmountComponentAtNode(div);
});

it("renders without crashing", () => {
  shallow(<TransactionCategory />);
});
