export default {
  items: [
    {
      name: 'Dashboard',
      url: '/admin/dashboard',
      icon: 'icon-speedometer'
    },
    {
      name: 'Revenue',
      url: '/admin/revenue',
      icon: 'far fa-address-book',
      children: [
        {
          name: 'Customer Invoices',
          url: '/admin/revenue/customer-invoice',
          icon: 'far fa-address-card',
        },
        {
          name: 'Receipts',
          url: '/admin/revenue/receipt',
          icon: 'fa fa-file-o',
        }
      ]
    },
    {
      name: 'Expense',
      url: '/admin/expense',
      icon: 'fas fa-receipt',
      children: [
        {
          name: 'Supplier Invoices',
          url: '/admin/expense/supplier-invoice',
          icon: 'far fa-address-card',
        },
        {
          name: 'Expense',
          url: '/admin/expense/expense',
          icon: 'fab fa-stack-exchange',
        },
        {
          name: 'Payments',
          url: '/admin/expense/payment',
          icon: 'fas fa-money-check',
        }
      ]
    },
    {
      name: 'Bank',
      url: '/admin/bank',
      icon: 'fas fa-folder',
      children: [
        {
          name: 'Bank Accounts',
          url: '/admin/bank/bank-account',
          icon: 'fas fa-university',
        },
        {
          name: 'Bank Statements',
          url: '/admin/bank/bank-statement',
          icon: 'icon-doc',
        }
      ]
    },
    {
      name: 'Taxes',
      url: '/admin/taxes',
      icon: 'fas fa-chart-line',
      children: [
        {
          name: 'VAT Transactions',
          url: '/admin/taxes/vat-transactions',
          icon: 'fas fa-exchange-alt',
        },
        {
          name: 'Reports Filing',
          url: '/admin/taxes/reports-filing',
          icon: 'fas fa-file-text',
        }
      ]
    },
    {
      name: 'Report',
      url: '/admin/report',
      icon: "fas fa-chart-bar",
      children: [
        {
          name: 'Transactions',
          url: '/admin/report/transactions',
          icon: 'fas fa-exchange-alt',
        },
        {
          name: 'Financial',
          url: '/admin/report/financial',
          icon: 'fas fa-usd',
        }
      ]
    },
    {
      name: 'Master',
      url: '/admin/master',
      icon: 'fas fa-database',
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
          icon: 'fas fa-users',
        }
      ]
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
        {
          name: 'Users & Roles',
          url: '/admin/settings/user-role',
          icon: 'fas fa-users',
        },
        {
          name: 'Organization Profile',
          url: '/admin/settings/organization-profile',
          icon: 'fas fa-sitemap',
        },
        {
          name: 'Currencies',
          url: '/admin/settings/currency',
          icon: 'fas fa-money',
        },
        {
          name: 'Notifications',
          url: '/admin/settings/notification',
          icon: 'fas fa-bell',
        },
        {
          name: 'Data Backup',
          url: '/admin/settings/data-backup',
          icon: 'fas fa-hdd-o',
        },
        {
          name: 'Help',
          url: '/admin/settings/help',
          icon: 'fas fa-info-circle',
        }
      ]
    },
    {
      name: 'Employee',
      url: '/admin/employee',
      icon: 'fas fa-user-tie'
    }
  ]
}
