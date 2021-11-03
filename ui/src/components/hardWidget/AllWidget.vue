<template>
  <div>
    <template v-for="h in hardware">
      <on-off-widget v-if="h.type === 0" :key="h.devId"
                     @click.native="handleClick(h.devId)"
                     :dev-id="h.devId" :can-shake="canShake"
                     :state.sync="h.state" :title="h.title" :icon="h.icon" :can-control="h.canControl"
                     :icon-active-color="h.iconActiveColor" :icon-un-active-color="h.iconUnActiveColor"
                     :text-active="h.textActive" :text-un-active="h.textUnActive"/>

      <mode-widget v-if="h.type === 1" :key="h.devId"
                   @click.native="handleClick(h.devId)"
                   :dev-id="h.devId" :can-shake="canShake"
                   :state.sync="h.state" :title="h.title" :can-control="h.canControl"
                   :state-options="h.stateOptions" :texts="h.texts" :icons="h.icons"/>
      <value-widget v-if="h.type === 2" :key="h.devId"
                    @click.native="handleClick(h.devId)"
                    :dev-id="h.devId" :can-shake="canShake"
                    :state.sync="h.state" :title="h.title" :icon="h.icon" :can-control="h.canControl"
                    :text="h.text" :config="h.config" :icon-color-for-max="h.iconColorForMax"/>

      <el-col :sm="6" :xs="12" v-if="h.type === 999" :key="h.devId"
              :style="'height: ' + (242) + 'px'">
      </el-col>
    </template>

  </div>
</template>

<script>
import OnOffWidget from './OnOffWidget';
import ModeWidget from './ModeWidget';
import ValueWidget from './ValueWidget';

export default {
  name: 'AllWidget',
  components: {
    OnOffWidget, ModeWidget, ValueWidget,
  },

  props: {
    hardware: { type: Array, required: true },
    canShake: { type: Boolean, required: false, default: true },
  },

  methods: {
    handleClick(devId) {
      if (!this.canShake) {
        console.log(devId);
      }
    },
  },

};
</script>

<style scoped>

</style>
