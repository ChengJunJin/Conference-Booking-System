<template>
  <div id="app">
    <MeetingRoomReservation v-if="!isLoginPage && !isCancelPage"/>
    
    <Login v-if="isLoginPage"/>
    <Cancel v-if="isCancelPage"/>
    
    
  </div>
</template>

<script>
import MeetingRoomReservation from './components/MeetingRoomReservation.vue';
import Login from './components/Login.vue';
import Cancel from './components/Cancel.vue';

export default {
  components: {
    MeetingRoomReservation,
    Login,
    Cancel,
  },computed: {
    // 计算属性来判断是否是登录页面
    isLoginPage() {
      return this.$route.path.endsWith('/login');
    },
    isCancelPage() {
      return this.$route.path.endsWith('/cancel');
    }
  }
};
</script>
