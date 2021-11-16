import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);
export default new Vuex.Store({
  state: {
    isShake: false,
    mouse: {
      moveX: null,
      moveY: null,
    },
    moveElement: {
      stateId: null,
      width: '',
      height: '',
    },
    hardwareOverview: [],
  },

  mutations: {
    setShake(state, value) {
      state.isShake = value;
    },
    setXY(state, value) {
      state.mouse.moveX = value.x;
      state.mouse.moveY = value.y;
    },
    setMoveStateId(state, moveStateId) {
      state.moveElement.stateId = moveStateId;
    },
    setMoveWidth(state, moveWidth) {
      state.moveElement.width = moveWidth;
    },
    setMoveHeight(state, moveHeight) {
      state.moveElement.height = moveHeight;
    },
    setHardwareOverview(state, hardwareOverview) {
      state.hardwareOverview = hardwareOverview;
    },
  },

  actions: {
  },

  modules: {
  },
});
