import BaseApi from './BaseApi';
import axios from '@/http/http';

class HardwareApi extends BaseApi {
  constructor() {
    super('hardware');
  }

  updateHardwareName(devId, name) {
    return axios.put(`/${this.prefix}/dev/${devId}`, { name });
  }

  updateStateName(stateId, name) {
    return axios.put(`/${this.prefix}/state/${stateId}`, { name });
  }
}

export default new HardwareApi();
