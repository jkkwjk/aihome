import axios from '@/http/http';
import BaseApi from './BaseApi';

class AutoApi extends BaseApi {
  constructor() {
    super('auto');
  }

  tryOnce(code) {
    return axios.post(`/${this.prefix}/tryOnce`, { code });
  }

  getAllAuto(type) {
    return axios.get(`/${this.prefix}/${type}`);
  }

  getCode(id) {
    return axios.get(`/${this.prefix}/code/${id}`);
  }

  modifyEnable(id, enable) {
    return axios.put(`/${this.prefix}/enable/${id}`, { enable });
  }

  modifyName(id, name) {
    return axios.put(`/${this.prefix}/name/${id}`, { name });
  }

  modifyCron(id, cron) {
    return axios.put(`/${this.prefix}/cron/${id}`, { cron });
  }

  modifyCode(id, code) {
    return axios.put(`/${this.prefix}/code/${id}`, { code });
  }
}

export default new AutoApi();
