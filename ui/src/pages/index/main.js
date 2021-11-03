import Vue from 'vue';

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'element-ui/lib/theme-chalk/display.css';
import '@assets/iconfont-extra1.css';

import axios from '@/http/http';

import echarts from 'echarts';

import router from './router';
import store from './store';
import App from './App.vue';

import '@assets/JSProtype';

Vue.use(ElementUI);
Vue.prototype.$echarts = echarts;
Vue.prototype.$store = store;
Vue.prototype.$http = axios;

new Vue({
  store,
  router,
  render: (h) => h(App),
}).$mount('#app');
