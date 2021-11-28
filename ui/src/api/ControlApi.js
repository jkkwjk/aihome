import BaseApi from './BaseApi';
import axios from '@/http/http';

class ControlApi extends BaseApi {
  constructor() {
    super('control');
  }

  updateState(stateId, state) {
    return axios.put(`/${this.prefix}/${stateId}`, { state });
  }
}

export default new ControlApi();
