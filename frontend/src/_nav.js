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
      name: 'BankAccount',
      url: '/bankAccount',
      icon: 'icon-file',
      // badge: {
      //   variant: 'info',
      //   text: 'NEW',
      // },
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
  ]
};
