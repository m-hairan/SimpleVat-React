import {
  InitialLayout,
  AdminLayout,
  HealthLayout
} from 'layouts'

const mainRoutes = [
  { path: '/admin', name: 'AdminLayout', component: AdminLayout },
  { path: '/healthz', name: 'HealthLayout', component: HealthLayout },
  { path: '/', name: 'InitialLayout', component: InitialLayout }
]

export default mainRoutes
