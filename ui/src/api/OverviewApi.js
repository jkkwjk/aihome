import axios from '@/http/http';
import BaseApi from './BaseApi';

class OverviewApi extends BaseApi {
  constructor() {
    super('overview');
  }

  getUnAddOverview() {
    return axios.get(`/${this.prefix}/unadd`);
  }

  reorderOverview(stateId, toStateId) {
    if (toStateId != null) {
      return axios.put(`/${this.prefix}`, { stateId, toStateId });
    }
    return axios.put(`/${this.prefix}`, { stateId });
  }
}

export default new OverviewApi();
