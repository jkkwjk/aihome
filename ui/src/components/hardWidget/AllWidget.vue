<template>
  <div>
    <template v-for="h in hardware">
      <on-off-widget v-if="h.type === 0" :key="h.stateId"
                     @click.native="handleClick(h.stateId)"
                     :state-id="h.stateId" :can-shake="canShake"
                     :state.sync="h.state" :name="h.name" :icon="h.icon" :can-control="h.canControl"
                     :icon-active-color="h.iconActiveColor" :icon-un-active-color="h.iconUnActiveColor"
                     :text-active="h.textActive" :text-un-active="h.textUnActive"/>

      <mode-widget v-if="h.type === 1" :key="h.stateId"
                   @click.native="handleClick(h.stateId)"
                   :state-id="h.stateId" :can-shake="canShake"
                   :state.sync="h.state" :name="h.name"
                   :can-control="h.canControl" :options="h.options"/>
      <value-widget v-if="h.type === 2" :key="h.stateId"
                    @click.native="handleClick(h.stateId)"
                    :state-id="h.stateId" :can-shake="canShake"
                    :state.sync="h.state" :name="h.name" :icon="h.icon" :can-control="h.canControl"
                    :text="h.text" :config="h.config" :icon-color-for-max="h.iconColorForMax"/>

      <el-col :sm="6" :xs="12" v-if="h.type === 999" :key="h.stateId"
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
    handleClick(stateId) {
      if (!this.canShake) {
        console.log(stateId);
      }
    },
  },

};
</script>

<style scoped>

</style>
