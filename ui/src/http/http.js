import axios from 'axios';
import Qs from 'qs';
import { Message } from 'element-ui';

const instance = axios.create({
  baseURL: `${process.env.VUE_APP_API}`,
  withCredentials: true,
  timeout: 2000,
  headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
});

instance.interceptors.request.use((config) => {
  if (config.headers['Content-Type'] === 'application/x-www-form-urlencoded') {
    config.data = Qs.stringify(config.data);
  }
  return config;
}, (error) => Promise.reject(error));

/**
 * 统一错误处理
 */
instance.interceptors.response.use((res) => {
  const { data } = res;

  if (data.code === 505) {
    Message.error(data.msg == null ? '服务器未知错误' : data.msg);
  }

  return data;
}, (error) => {
  if (error.message.includes('timeout')) {
    Message.error('服务器响应超时');
  }

  return Promise.reject(error);
});

export default instance;
