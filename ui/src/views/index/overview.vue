<template>
  <div class="overview">
    <el-row class="tool-bar"
    :style="{'margin-top': $store.state.isShake? '0':'-30px'}">
      <alpha-button icon="el-icon-plus" round size="mini"/>
      <alpha-button style="float: right"
                    round
                    size="mini"
                    @click="$store.commit('setShake', false)">完成</alpha-button>
    </el-row>

    <el-row :gutter="10" class="data" @mousemove.native="handleMouseMove" ref="posr">
      <template v-for="h in hardware">

        <on-off-widget v-if="h.type === 0" :key="h.devId" :dev-id="h.devId"
          :state.sync="h.state" :title="h.title" :icon="h.icon" :can-control="h.canControl"
          :icon-active-color="h.iconActiveColor" :icon-un-active-color="h.iconUnActiveColor"
          :text-active="h.textActive" :text-un-active="h.textUnActive"/>
        <mode-widget v-if="h.type === 1" :key="h.devId" :dev-id="h.devId"
                       :state.sync="h.state" :title="h.title" :can-control="h.canControl"
                       :state-options="h.stateOptions" :texts="h.texts" :icons="h.icons"/>
        <value-widget v-if="h.type === 2" :key="h.devId" :dev-id="h.devId"
                       :state.sync="h.state" :title="h.title" :icon="h.icon" :can-control="h.canControl"
                       :text="h.text" :config="h.config" :icon-color-for-max="h.iconColorForMax"/>

        <el-col :sm="6" :xs="12" v-if="h.type === 999" :key="h.devId"
          style="border: 1px black solid">

        </el-col>
      </template>

    </el-row>
  </div>

</template>

<script>
import AlphaButton from '../../components/AlphaButton';
import OnOffWidget from '../../components/hard/OnOffWidget';
import ModeWidget from '../../components/hard/ModeWidget';
import ValueWidget from '../../components/hard/ValueWidget';

export default {
  name: 'overview',
  components: {
    ValueWidget, ModeWidget, OnOffWidget, AlphaButton,
  },
  data() {
    return {
      delivery: true,
      hardware: [
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
          canControl: false,
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
      temp: {
        type: 999,
        devId: 'adsdxc',
      },
    };
  },
  methods: {
    handleMouseMove(e) {
      const posr = this.$refs.posr.$el;
      const x = e.clientX - posr.offsetLeft;
      const y = e.clientY - posr.offsetTop;

      if (this.$store.state.moveElement.devId != null) {
        const devIndex = this.hardware.findIndex((o) => o.devId === this.$store.state.moveElement.devId);
        this.hardware.push(JSON.parse(JSON.stringify(this.hardware[devIndex])));
        this.hardware.removeEqual((o) => o.devId === this.$store.state.moveElement.devId);

        const moveToIndex = parseInt(y / (this.$store.state.moveElement.height + 10), 10) * 4 + parseInt(x / (this.$store.state.moveElement.width + 10), 10);
        console.log(moveToIndex);
        this.hardware.removeEqual((o) => o.type === 999);
        this.hardware.splice(moveToIndex, 0, this.temp);
      } else {
        this.hardware.removeEqual((o) => o.type === 999);
      }
      this.$store.commit('setXY', { x, y });
    },
  },
  computed: {
    moveDevId() {
      return this.$store.state.moveDevId;
    },
  },
};
</script>

<style scoped lang="scss">
  .overview {
    width: 100%;
    height: 100%;
    overflow: hidden;
  }

  .tool-bar {
    height: 30px;
    margin-bottom: 5px;
    padding-right: 20px;
    transition: .5s;
    z-index: 0;
  }

  .data {
    height: 100%;
    width: 100%;
  }
</style>
