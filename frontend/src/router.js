import { createRouter, createWebHistory } from 'vue-router';

// import views
import StartPage from './views/StartPage.vue';
import ProfilePage from './views/ProfilePage.vue';

export default createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: StartPage,
    },
    {
      path: '/profile/:id?',
      component: ProfilePage,
    },
  ],
});
