import React from 'react';

const Dashboard = React.lazy(() => import('./views/Dashboard'));
const GeneralSettings = React.lazy(() => import('./views/Settings/General-settings'));
const VatCategory = React.lazy(() => import('./views/Settings/Vat-Category'));
const Invoice = React.lazy(() => import('./views/Invoice'));
const CreateOrEditVatCategory = React.lazy(() => import('./views/Settings/Vat-Category/CreateOrEditVatCategory/CreateOrEditVatCategory'));

// https://github.com/ReactTraining/react-router/tree/master/packages/react-router-config
const routes = [
  { path: '/', exact: true, name: 'Home' },
  { path: '/home', name: 'Home', component: Dashboard },
  { path: '/invoice', name: 'Invoice', component: Invoice },
  { path: '/settings/general', name: 'General Settings', component: GeneralSettings },
  { path: '/settings/vat-category', name: 'Vat Category', component: VatCategory },
  { path: '/create-vat-category', name: 'Create Vat Category', component: CreateOrEditVatCategory },
];

export default routes;
