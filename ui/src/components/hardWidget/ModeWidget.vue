<template>
  <el-col :sm="canShake? 6:12" :xs="12">
    <widget :name="name" :state-id="stateId" :can-shake="canShake">
      <i :class="options[state].icon"
         :style="{'font-size': '50px', 'color': options[state].color}"
         v-if="options[state] && options[state].icon">
      </i>
      <span style="font-size: 17px;"
            v-if="options[state] && options[state].text">
        {{ options[state].text }}
      </span>

      <el-radio-group v-model="state__inner" style="margin-top: 20px;" v-if="canControl" @click.native.stop>
        <el-radio-button :label="options[modeValue].modeText" v-for="modeValue in Object.keys(options)" :key="modeValue"></el-radio-button>
      </el-radio-group>
    </widget>
  </el-col>
</template>

<script>
import Widget from '../widget';

export default {
  name: 'ModeWidget',
  components: { Widget },
  props: {
    canShake: { type: Boolean, required: false, default: true },

    stateId: { type: String, required: true },
    canControl: { type: Boolean, required: true },
    name: { type: String, required: false },
    options: { type: Object, required: true },
    state: { type: String, required: true },
  },
  computed: {
    state__inner: {
      get() {
        return this.options[this.state].modeText;
      },
      set(v) {
        Object.keys(this.options).forEach((modeValue) => {
          if (this.options[modeValue].modeText === v) {
            this.$emit('update:state', modeValue);
          }
        });
      },
    },
  },
};
</script>

<style scoped>
  .el-radio-group {
    display: flex;
    width: 100%;
  }
  .el-radio-button {
    flex: 1;
    overflow: hidden;
  }

  .el-radio-button ::v-deep .el-radio-button__inner {
    width: 100%;
    padding: 10px 0;
    font-size: 10px;
  }
</style>
