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
    hardwareOverview: [
      {
        type: 0,
        stateId: 'aaaaa',
        canControl: true,
        name: '1',
        textActive: '开开开',
        textUnActive: '关了',
        state: false,
        icon: 'el-icon-switch-button',
        iconActiveColor: '#000000',
        iconUnActiveColor: '#aabbcc',
      },
      {
        type: 1,
        stateId: 'bbbbbb',
        canControl: true,
        name: '2',
        options: {
          eco: {
            modeText: '环保',
            icon: 'el-icon-s-promotion',
            color: '#54b022',
            text: '节能模式',
          },
          auto: {
            modeText: '自动',
            icon: 'el-icon-help',
            color: '#539fb0',
            text: '自动模式',
          },
          power: {
            modeText: '全力',
            icon: 'el-icon-loading',
            color: '#b05d3b',
            text: '强力模式',
          },
        },
        state: 'eco',
      },
      {
        type: 0,
        stateId: 'aaaab',
        canControl: true,
        name: '3',
        textActive: '开开开',
        textUnActive: '关了',
        state: false,
        icon: 'el-icon-switch-button',
        iconActiveColor: '#000000',
        iconUnActiveColor: '#aabbcc',
      },
      {
        type: 2,
        stateId: 'cccc',
        canControl: true,
        name: '4',
        text: '亮度调节',
        state: 40,
        icon: 'el-icon-s-opportunity',
        iconColorForMax: '#8bb9eb',
        config: {
          min: 1,
          max: 100,
          step: 1,
        },
      },
      {
        type: 0,
        stateId: 'aaaac',
        canControl: true,
        name: '5',
        textActive: '开开开',
        textUnActive: '关了',
        state: false,
        icon: 'el-icon-switch-button',
        iconActiveColor: '#000000',
        iconUnActiveColor: '#aabbcc',
      },
    ],
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
  },

  actions: {
  },

  modules: {
  },
});
