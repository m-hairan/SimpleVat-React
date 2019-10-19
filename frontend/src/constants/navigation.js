export default {
  items: [
    {
      name: 'Home',
      url: '/admin/home',
      icon: 'icon-speedometer',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
    },
    {
      name: 'Invoice',
      url: '/admin/invoice',
      icon: 'far fa-address-book',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
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
      name: 'BankAccount',
      url: '/admin/bank-account',
      icon: 'fas fa-university',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
    },
    {
      name: 'Taxes',
      url: '/admin/taxes',
      icon: 'fas fa-chart-line',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
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
      ],
    },
    {
      name: 'Imports',
      url: '/admin/imports',
      icon: 'fas fa-file-import',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
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
          name: 'Users',
          url: '/admin/master/users',
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
      icon: 'fas fa-user-tie',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
    },
  ]
}