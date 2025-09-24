import { createWebHistory, createRouter } from 'vue-router'
import App from '../App.vue'
import AppLayout from '../components/AppLayout.vue'
import Login from '../pages/login.vue'
import Register from '../pages/register.vue'
import Home from '../pages/home.vue'
import CustomerList from '../pages/Customer/CustomerList.vue'
import CustomerDetail from '../pages/Customer/CustomerDetail.vue'
import Car from '../pages/Car/registerCar.vue'
import CarList from '@/pages/Car/CarList.vue'
const routes = [
  {
    path: '/',
    component: AppLayout,
    children: [
      {path: '/home', component: Home},
      {path: '/customers', component: CustomerList},
      {path: '/customers/add', component: CustomerDetail},
      {path: '/customers/edit/:id', component: CustomerDetail, props: true},
      {path: '/cars', component: CarList},
      {path: '/cars/add', component: Car},
    ],
  },
   {path: '/login', component: Login},
  {path: '/register', component: Register},
];


const router = createRouter({
  history: createWebHistory(),
  routes,
})

// });
router.beforeEach((to, from, next) => {
  let employee = null;
  try {
    employee = JSON.parse(sessionStorage.getItem('employee'));
  } catch (e) {
    employee = null;
  }

  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);

  if (requiresAuth && (!employee || typeof employee !== 'object')) {
    next('/SignIn');
  } else {
    next();
  }
});



export default router; 