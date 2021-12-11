import BaseApi from './BaseApi';

class EventApi extends BaseApi {
  constructor() {
    super('event');
  }
}

export default new EventApi();
