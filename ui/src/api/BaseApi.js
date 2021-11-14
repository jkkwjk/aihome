import axios from '@/http/http';
/**
 * TOFIX: 未测试!!
 * Create Time: 2020年11月17日18:55:18
 */
export default class BaseApi {
  constructor(prefix) {
    this.prefix = prefix;
  }

  getById(id) {
    return axios.get(`/${this.prefix}/${id}`);
  }

  getAll() {
    return axios.get(`/${this.prefix}`);
  }

  add(data) {
    return axios.post(`/${this.prefix}`, data);
  }

  update(data) {
    return axios.put(`/${this.prefix}`, data);
  }

  remove(id) {
    return axios.delete(`/${this.prefix}/${id}`);
  }
}
