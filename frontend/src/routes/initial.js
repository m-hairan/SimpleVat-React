import {
  LogIn,
  Register
} from 'screens'

const initialRoutes = [
  {
    path: '/login',
    name: 'LogIn',
    component: LogIn.screen
  },
  {
    path: '/register',
    name: 'Register',
    component: Register.screen
  },
  {
    redirect: true,
    path: '/',
    pathTo: '/login',
    name: 'Initial'
  }
]

export default initialRoutes