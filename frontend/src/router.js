import { createRouter, createWebHistory } from 'vue-router';

// import views
import StartPage from './views/StartPage.vue';
import LoginPage from './views/LoginPage.vue';
import ProfilePage from './views/ProfilePage.vue';
import LeaseAHousePage from './views/LeaseAHousePage.vue';

export default createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: StartPage,
    },
    {
      path: '/login',
      component: LoginPage,
    },
    {
      path: '/profile/:id?',
      component: ProfilePage,
    },
    {
      path: '/leaseAHouse',
      component: LeaseAHousePage
    }
  ],
});
