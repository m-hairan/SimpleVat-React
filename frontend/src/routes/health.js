import {
  Healthz
} from 'screens'

const healthRoutes = [
  {
    path: '/healthz/index.html',
    name: 'Healthz',
    component: Healthz.screen
  },
  {
    redirect: true,
    path: '/healthz',
    pathTo: '/healthz/index.html',
    name: 'Health'
  }
]

export default healthRoutes