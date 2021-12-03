import axios from '@/http/http';
import BaseApi from './BaseApi';

class ControlApi extends BaseApi {
  constructor() {
    super('control');
  }

  updateState(stateId, state) {
    return axios.put(`/${this.prefix}/${stateId}`, { state });
  }
}

export default new ControlApi();
