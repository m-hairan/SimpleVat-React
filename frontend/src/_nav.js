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
      icon: 'icon-file',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
    },
    {
      name: 'Expense',
      url: '/Expense',
      icon: 'icon-settings',
      children: [
        {
          name: 'Expence',
          url: '/Expense/Expense',
          icon: 'icon-wrench',
        },
        {
          name: 'Purchase',
          url: '/Expense/Purchase',
          icon: 'icon-briefcase',
        },
      ],
    },
    {
      name: 'BankAccount',
      url: '/bankAccount',
      icon: 'icon-file',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
    },
    {
      name: 'Taxes',
      url: '/Taxes',
      icon: 'icon-file',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
    },
    {
      name: 'Report',
      url: '/Report',
      icon: 'icon-settings',
      children: [
        {
          name: 'Transaction Report',
          url: '/Report/TransactionReport',
          icon: 'icon-wrench',
        },
        {
          name: 'Invoice Report',
          url: '/Report/InvoiceReport',
          icon: 'icon-briefcase',
        },
        {
          name: 'Expense Report',
          url: '/Report/ExpenseReport',
          icon: 'icon-graph',
        },
      ],
    },
    {
      name: 'Imports',
      url: '/Imports',
      icon: 'icon-file',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
    },
    {
      name: 'Master',
      url: '/settings',
      icon: 'icon-settings',
      children: [
        {
          name: 'Product',
          url: '/settings/general',
          icon: 'icon-wrench',
        },
        {
          name: 'Project',
          url: '/settings/vat-category',
          icon: 'icon-briefcase',
        },
        {
          name: 'Contact',
          url: '/settings/transaction-category',
          icon: 'icon-graph',
        },
        {
          name: 'Users',
          url: '/settings/transaction-category',
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
      icon: 'icon-file',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
    },
  ]
};
