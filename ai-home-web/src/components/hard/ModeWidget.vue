<template>
  <el-col :sm="6" :xs="12">
    <widget :title="title" :dev-id="devId">
      <i :class="icons[state].icon"
         :style="{'font-size': '50px', 'color': state? icons[state].activeColor: icons[state].unActiveColor}"
         v-if="icons && icons[state]">
      </i>
      <span style="font-size: 17px;"
            v-if="texts && texts[state]">
        {{ texts[state].text }}
      </span>

      <el-radio-group v-model="state__inner" style="margin-top: 20px;" v-if="canControl">
        <el-radio-button :label="text" v-for="{text, value} in stateOptions" :key="value"></el-radio-button>
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
    devId: { type: String, required: true },
    canControl: { type: Boolean, required: true },
    title: { type: String, required: false },
    texts: { type: Object, required: false },
    icons: { type: Object, required: false },
    stateOptions: { type: Array, required: true },
    state: { type: String, required: true },
  },
  computed: {
    state__inner: {
      get() {
        const option = this.stateOptions.find((o) => o.value === this.state);
        return option.text;
      },
      set(v) {
        const option = this.stateOptions.find((o) => o.text === v);
        this.$emit('update:state', option.value);
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
