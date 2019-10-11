export default {
  items: [
    {
      name: 'Home',
      url: '/home',
      icon: 'icon-speedometer',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
    },
    {
      name: 'Invoice',
      url: '/invoice',
      icon: 'far fa-address-book',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
    },
    {
      name: 'Expense',
      url: '/Expense',
      icon: 'fas fa-receipt',
      children: [
        {
          name: 'Expence',
          url: '/Expense/Expense',
          icon: 'fab fa-stack-exchange',
        },
        {
          name: 'Purchase',
          url: '/Expense/Purchase',
          icon: 'fas fa-money-check',
        },
      ],
    },
    {
      name: 'BankAccount',
      url: '/bankAccount',
      icon: 'fas fa-university',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
    },
    {
      name: 'Taxes',
      url: '/Taxes',
      icon: 'fas fa-chart-line',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
    },
    {
      name: 'Report',
      url: '/Report',
      icon: "fas fa-chart-bar",
      children: [
        {
          name: 'Transaction Report',
          url: '/Report/TransactionReport',
          icon: 'fas fa-exchange-alt',
        },
        {
          name: 'Invoice Report',
          url: '/Report/InvoiceReport',
          icon: 'fas fa-list-ul',
        },
        {
          name: 'Expense Report',
          url: '/Report/ExpenseReport',
          icon: 'fas fa-tasks',
        },
      ],
    },
    {
      name: 'Imports',
      url: '/Imports',
      icon: 'fas fa-file-import',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
    },
    {
      name: 'Master',
      url: '/Master',
      icon: 'icon-settings',
      children: [
        {
          name: 'Product',
          url: '/Master/Product',
          icon: 'fas fa-object-group',
        },
        {
          name: 'Project',
          url: '/Master/Project',
          icon: 'fas fa-project-diagram',
        },
        {
          name: 'Contact',
          url: '/Master/Contact',
          icon: 'fas fa-id-card-alt',
        },
        {
          name: 'Users',
          url: '/Master/Users',
          icon: 'icon-graph',
        },
      ],
    },
    {
      name: 'Settings',
      url: '/settings',
      icon: 'icon-settings',
      children: [
        {
          name: 'General Settings',
          url: '/settings/general',
          icon: 'icon-wrench',
        },
        {
          name: 'Vat Category',
          url: '/settings/vat-category',
          icon: 'icon-briefcase',
        },
        {
          name: 'Transaction Category',
          url: '/settings/transaction-category',
          icon: 'icon-graph',
        },
      ],
    },
    {
      name: 'Employee',
      url: '/Employee',
      icon: 'fas fa-user-tie',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
    },
  ]
};
