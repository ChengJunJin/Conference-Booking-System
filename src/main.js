import Vue from 'vue';
import App from './App.vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import Datetime from 'vue-datetime';
import 'vue-datetime/dist/vue-datetime.css';
import router from './router/router.js';
import moment from 'moment'

Vue.use(Datetime);
Vue.use(ElementUI);
Vue.use(moment);
Vue.prototype.$moment = moment;
Vue.config.productionTip = false;

new Vue({
  router,
  render: (h) => h(App),
}).$mount('#app');
