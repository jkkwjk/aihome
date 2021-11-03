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
      devId: null,
      width: '',
      height: '',
    },
  },

  mutations: {
    setShake(state, value) {
      state.isShake = value;
    },
    setXY(state, value) {
      state.mouse.moveX = value.x;
      state.mouse.moveY = value.y;
    },
    setMoveDevId(state, moveDevId) {
      state.moveElement.devId = moveDevId;
    },
    setMoveWidth(state, moveWidth) {
      state.moveElement.width = moveWidth;
    },
    setMoveHeight(state, moveHeight) {
      state.moveElement.height = moveHeight;
    },
  },

  actions: {
  },

  modules: {
  },
});