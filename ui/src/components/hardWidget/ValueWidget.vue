<template>
  <el-col :sm="canShake? 6:12" :xs="12">
    <widget :name="name" :state-id="stateId" :can-shake="canShake">
      <i :class="icon" :style="{'font-size': '50px', 'color': iconColor}" v-if="icon"></i>
      <span style="font-size: 17px;" v-if="text">{{ text }}</span>
      <el-slider v-model="state__inner"
                 :min="config.min"
                 :max="config.max"
                 :step="config.step"
                 style="margin-top: 20px; width: 100%" v-if="canControl"
                 @click.native.stop
                 @mousedown.native.stop
                 @mouseleave.native.stop
                 @touchstart.native.stop></el-slider>
      <span v-else style="margin-top: 20px;">{{ state }}</span>
    </widget>
  </el-col>

</template>

<script>
import Widget from '../widget';
import { getLightColor } from '../../util/ColorUtil';

export default {
  name: 'ValueWidget',
  components: { Widget },
  props: {
    canShake: { type: Boolean, required: false, default: true },

    stateId: { type: String, required: true },
    canControl: { type: Boolean, required: true },
    name: { type: String, required: false },
    text: { type: String, required: false },
    state: { type: Number, required: true },
    icon: { type: String, required: false },
    config: { type: Object, required: true },
    iconColorForMax: { type: String, required: false, default: '#000000' },
  },
  computed: {
    iconColor() {
      const range = this.config.max - this.config.min;
      const now = Math.min(range, this.config.max - this.state - this.config.min);
      return getLightColor(this.iconColorForMax, now / range);
    },
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
