import Vue from 'vue';

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'element-ui/lib/theme-chalk/display.css';
import '@assets/iconfont-extra1.css';

import echarts from 'echarts';
import axios from '@/http/http';

import router from './router';
import store from './store';
import App from './App.vue';

import '@assets/JSProtype';

Vue.use(ElementUI);
Vue.prototype.$echarts = echarts;
Vue.prototype.$store = store;
Vue.prototype.$http = axios;

// 全局message配置（取配置项）
Vue.prototype.$message = function (msg) {
  ElementUI.Message(msg);
};
Vue.prototype.$message.success = function (message) {
  return ElementUI.Message.success({
    message,
    offset: 10,
    duration: 1000,
  });
};
Vue.prototype.$message.error = function (message) {
  return ElementUI.Message.error({
    message,
    offset: 10,
    duration: 2000,
  });
};

new Vue({
  store,
  router,
  render: (h) => h(App),
}).$mount('#app');
