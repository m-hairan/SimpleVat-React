export default {
  items: [
    {
      name: 'Home',
      url: '/admin/home',
      icon: 'icon-speedometer'
    },
    {
      name: 'Invoice',
      url: '/admin/invoice',
      icon: 'far fa-address-book'
    },
    {
      name: 'Expense',
      url: '/admin/expense',
      icon: 'fas fa-receipt',
      children: [
        {
          name: 'Expence',
          url: '/admin/expense/expense',
          icon: 'fab fa-stack-exchange',
        },
        {
          name: 'Purchase',
          url: '/admin/expense/purchase',
          icon: 'fas fa-money-check',
        },
      ],
    },
    {
      name: 'Bank Account',
      url: '/admin/bank-account',
      icon: 'fas fa-university'
    },
    {
      name: 'Taxes',
      url: '/admin/taxes',
      icon: 'fas fa-chart-line'
    },
    {
      name: 'Report',
      url: '/admin/report',
      icon: "fas fa-chart-bar",
      children: [
        {
          name: 'Transaction Report',
          url: '/admin/report/transaction-report',
          icon: 'fas fa-exchange-alt',
        },
        {
          name: 'Invoice Report',
          url: '/admin/report/invoice-report',
          icon: 'fas fa-list-ul',
        },
        {
          name: 'Expense Report',
          url: '/admin/report/expense-report',
          icon: 'fas fa-tasks',
        },
        {
          name: 'Tax Report',
          url: '/admin/report/tax-report',
          icon: 'fas fa-files-o',
        },
        {
          name: 'Profit and Loss',
          url: '/admin/report/profit-and-loss',
          icon: 'fas fa-signal',
        },
        {
          name: 'Balance Sheet',
          url: '/admin/report/balance-sheet',
          icon: 'fas fa-balance-scale',
        },
        {
          name: 'Cash Flow / Position',
          url: '/admin/report/cash-flow-position',
          icon: 'fas fa-money',
        },
      ],
    },
    {
      name: 'Imports',
      url: '/admin/imports',
      icon: 'fas fa-file-import'
    },
    {
      name: 'Master',
      url: '/admin/master',
      icon: 'icon-settings',
      children: [
        {
          name: 'Product',
          url: '/admin/master/product',
          icon: 'fas fa-object-group',
        },
        {
          name: 'Project',
          url: '/admin/master/project',
          icon: 'fas fa-project-diagram',
        },
        {
          name: 'Contact',
          url: '/admin/master/contact',
          icon: 'fas fa-id-card-alt',
        },
        {
          name: 'User',
          url: '/admin/master/user',
          icon: 'icon-graph',
        },
      ],
    },
    {
      name: 'Settings',
      url: '/admin/settings',
      icon: 'icon-settings',
      children: [
        {
          name: 'General Settings',
          url: '/admin/settings/general',
          icon: 'icon-wrench',
        },
        {
          name: 'Vat Category',
          url: '/admin/settings/vat-category',
          icon: 'icon-briefcase',
        },
        {
          name: 'Transaction Category',
          url: '/admin/settings/transaction-category',
          icon: 'icon-graph',
        },
      ],
    },
    {
      name: 'Employee',
      url: '/admin/employee',
      icon: 'fas fa-user-tie'
    }
  ]
}
