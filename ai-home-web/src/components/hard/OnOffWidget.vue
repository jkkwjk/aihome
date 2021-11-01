<template>
  <el-col :sm="6" :xs="12">
    <widget :title="title" :dev-id="devId">
      <div style="margin-top: 5px;"></div>
      <i :class="icon" :style="{'font-size': '50px', 'color': state? iconActiveColor: iconUnActiveColor}" v-if="icon"></i>
      <span style="font-size: 17px;" v-if="textActive && textUnActive">{{ state? textActive: textUnActive }}</span>
      <el-switch v-model="state__inner" style="margin-top: 20px;" :width="60" v-if="canControl"></el-switch>
    </widget>
  </el-col>

</template>

<script>
import Widget from '../widget';

export default {
  name: 'OnOffWidget',
  components: { Widget },
  props: {
    devId: { type: String, required: true },
    canControl: { type: Boolean, required: true },
    title: { type: String, required: false },
    textActive: { type: String, required: false },
    textUnActive: { type: String, required: false },
    state: { type: Boolean, required: true },
    icon: { type: String, required: false },
    iconActiveColor: { type: String, required: false, default: '#000000' },
    iconUnActiveColor: { type: String, required: false, default: '#000000' },
  },
  computed: {
    state__inner: {
      get() {
        return this.state;
      },
      set(v) {
        this.$emit('update:state', v);
      },
    },
  },
};
</script>

<style scoped>
</style>
