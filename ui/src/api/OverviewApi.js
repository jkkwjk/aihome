import BaseApi from './BaseApi';
import axios from '@/http/http';

class OverviewApi extends BaseApi {
  constructor() {
    super('overview');
  }

  getUnAddOverview() {
    return axios.get(`/${this.prefix}/unadd`);
  }
}

export default new OverviewApi();
