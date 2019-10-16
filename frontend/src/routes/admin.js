import {
  Home
} from 'screens'

const adminRoutes = [
  {
    path: '/admin/home',
    name: 'Home',
    component: Home.screen
  },
  {
    redirect: true,
    path: '/admin',
    pathTo: '/admin/home',
    name: 'Admin'
  }
]

export default adminRoutes