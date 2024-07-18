// router.js
import Vue from 'vue';
import VueRouter from 'vue-router';

// 导入组件
import Login from '../components/Login.vue';
import Cancel from '../components/Cancel.vue';
import Meeting from '../components/MeetingRoomReservation.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/login',
    component: Login
  },
  {
    path: '/cancel',
    component: Cancel
  },
  {
    path: '/meeting',
    component: Meeting
  }
];

const router = new VueRouter({
  routes
});

export default router;
