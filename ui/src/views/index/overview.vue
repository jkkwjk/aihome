<template>
  <div class="overview">
    <el-row class="tool-bar"
    :style="{'margin-top': $store.state.isShake? '0':'-30px'}">
      <alpha-button icon="el-icon-plus" round size="mini" @click="addOverviewDialogVisible=true"/>
      <alpha-button style="float: right"
                    round
                    size="mini"
                    @click="$store.commit('setShake', false)">完成</alpha-button>
    </el-row>

    <el-row :gutter="10" class="data" @mousemove.native="handleMouseMove" ref="posr">
      <all-widget :hardware="hardwareOverview"></all-widget>
    </el-row>

    <el-dialog title="添加到概览"
               :visible.sync="addOverviewDialogVisible">
      <el-row :gutter="10">
        <all-widget :can-shake="false" :hardware="hardwareWithOutOverview"></all-widget>
      </el-row>
    </el-dialog>
  </div>

</template>

<script>
import AllWidget from '@components/hardWidget/AllWidget';
import AlphaButton from '../../components/AlphaButton';

export default {
  name: 'overview',
  components: {
    AllWidget, AlphaButton,
  },
  data() {
    return {
      hardwareWithOutOverview: [
        {
          type: 0,
          stateId: 'aaaaa',
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
          stateId: 'bbbbbb',
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
          stateId: 'aaaab',
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
          stateId: 'cccc',
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
      ],
      temp: {
        type: 999,
        stateId: 'tmp',
      },
      originIndex: -1,
      addOverviewDialogVisible: false,
    };
  },
  methods: {
    handleMouseMove(e) {
      const posr = this.$refs.posr.$el;
      const x = e.clientX - posr.offsetLeft;
      const y = e.clientY - posr.offsetTop;
      let moveToIndex = parseInt(y / (this.moveElement.height + 10), 10) * 4 + parseInt(x / (this.moveElement.width + 10), 10);
      this.hardwareOverview.removeEqual((o) => o.type === 999);

      if (moveToIndex >= this.hardwareOverview.length) {
        moveToIndex = this.hardwareOverview.length - 1;
      }
      if (this.moveElement.stateId != null) {
        if (moveToIndex > this.originIndex) {
          this.hardwareOverview.splice(moveToIndex + 1, 0, this.temp);
        } else {
          this.hardwareOverview.splice(moveToIndex, 0, this.temp);
        }
      } else if (this.originIndex !== -1) {
        if (this.originIndex !== moveToIndex) {
          console.log(`移动；${this.originIndex} -> ${moveToIndex}`);
          console.log(this.hardwareOverview[this.originIndex].stateId, this.hardwareOverview[moveToIndex].stateId);
        }
        this.originIndex = -1;
      }
      this.$store.commit('setXY', { x, y });
    },
  },
  computed: {
    moveElement() {
      return this.$store.state.moveElement;
    },
    hardwareOverview() {
      return this.$store.state.hardwareOverview;
    },
  },
  watch: {
    'moveElement.stateId': {
      handler() {
        // 防止闪烁
        if (this.moveElement.stateId != null) {
          this.originIndex = this.hardwareOverview.findIndex((o) => o.stateId === this.moveElement.stateId);
          this.hardwareOverview.splice(this.originIndex, 0, this.temp);
        } else {
          this.hardwareOverview.removeEqual((o) => o.type === 999);
        }
      },
    },
  },
};
</script>

<style scoped lang="scss">
  .overview {
    width: 100%;
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
