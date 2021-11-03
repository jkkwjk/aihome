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
    hardwareOverview: [
      {
        type: 0,
        devId: 'aaaaa',
        canControl: true,
        title: '1',
        textActive: '开开开',
        textUnActive: '关了',
        state: false,
        icon: 'el-icon-switch-button',
        iconActiveColor: '#000000',
        iconUnActiveColor: '#aabbcc',
      },
      {
        type: 1,
        devId: 'bbbbbb',
        canControl: true,
        title: '2',
        texts: {
          eco: {
            text: '节能模式',
          },
          auto: {
            text: '自动模式',
          },
          power: {
            text: '强力模式',
          },
        },
        icons: {
          eco: {
            icon: 'el-icon-s-promotion',
            activeColor: '#54b022',
          },
          auto: {
            icon: 'el-icon-help',
            activeColor: '#539fb0',
          },
          power: {
            icon: 'el-icon-loading',
            activeColor: '#b05d3b',
          },
        },
        stateOptions: [
          {
            text: '环保',
            value: 'eco',
          },
          {
            text: '自动',
            value: 'auto',
          },
          {
            text: '全力',
            value: 'power',
          },
        ],
        state: 'eco',
      },
      {
        type: 0,
        devId: 'aaaab',
        canControl: true,
        title: '3',
        textActive: '开开开',
        textUnActive: '关了',
        state: false,
        icon: 'el-icon-switch-button',
        iconActiveColor: '#000000',
        iconUnActiveColor: '#aabbcc',
      },
      {
        type: 2,
        devId: 'cccc',
        canControl: true,
        title: '4',
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
        devId: 'aaaac',
        canControl: true,
        title: '5',
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
