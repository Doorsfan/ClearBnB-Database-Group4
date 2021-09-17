import { createRouter, createWebHistory } from 'vue-router';

// import views
import StartPage from './views/StartPage.vue';

export default createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: StartPage,
    }
  ],
});
