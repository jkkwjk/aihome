<template>
  <el-col :sm="canShake? 6:12" :xs="12">
    <widget :name="name" :state-id="stateId" :can-shake="canShake">
      <div style="margin-top: 5px;"></div>
      <i :class="icon" :style="{'font-size': '50px', 'color': state? iconActiveColor: iconUnActiveColor}" v-if="icon"></i>
      <span style="font-size: 17px;" v-if="textActive && textUnActive">{{ state? textActive: textUnActive }}</span>
      <el-switch v-model="state__inner" style="margin-top: 20px;" :width="60" v-if="canControl" @click.native.stop></el-switch>
    </widget>
  </el-col>

</template>

<script>
import Widget from '../widget';

export default {
  name: 'OnOffWidget',
  components: { Widget },
  props: {
    canShake: { type: Boolean, required: false, default: true },

    stateId: { type: String, required: true },
    canControl: { type: Boolean, required: true },
    name: { type: String, required: false },
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
