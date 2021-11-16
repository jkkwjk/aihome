<template>
  <div class="overview">
    <el-row class="tool-bar"
    :style="{'margin-top': $store.state.isShake || $store.state.hardwareOverview.length === 0? '0':'-30px'}">
      <alpha-button icon="el-icon-plus" round size="mini" @click="handleOpenAddOverviewDialog"/>
      <alpha-button style="float: right"
                    round
                    size="mini"
                    v-if="$store.state.hardwareOverview.length !== 0"
                    @click="$store.commit('setShake', false)">完成</alpha-button>
    </el-row>

    <el-row :gutter="10" class="data" @mousemove.native="handleMouseMove" ref="posr">
      <all-widget :hardware="hardwareOverview"></all-widget>
    </el-row>

    <el-dialog title="添加到概览"
               :visible.sync="addOverviewDialogVisible">
      <el-row :gutter="10">
        <all-widget :can-shake="false" :hardware="hardwareWithOutOverview" @click="handleAddOverview"></all-widget>
      </el-row>
    </el-dialog>
  </div>

</template>

<script>
import AllWidget from '@components/hardWidget/AllWidget';
import overviewApi from '@api/OverviewApi';
import AlphaButton from '../../components/AlphaButton';

export default {
  name: 'overview',
  components: {
    AllWidget, AlphaButton,
  },
  async created() {
    this.$store.commit('setHardwareOverview', await overviewApi.getAll());
  },
  data() {
    return {
      hardwareWithOutOverview: [],
      temp: {
        type: 'TMP',
        stateId: 'tmp',
      },
      originIndex: -1,
      addOverviewDialogVisible: false,
    };
  },
  methods: {
    async handleAddOverview(stateId) {
      const detailView = await overviewApi.add({ stateId });
      this.$message.success('添加成功');
      this.addOverviewDialogVisible = false;
      this.$store.state.hardwareOverview.push(detailView);
    },
    async handleOpenAddOverviewDialog() {
      this.hardwareWithOutOverview = await overviewApi.getUnAddOverview();
      this.addOverviewDialogVisible = true;
    },
    handleMouseMove(e) {
      const posr = this.$refs.posr.$el;
      const x = e.clientX - posr.offsetLeft;
      const y = e.clientY - posr.offsetTop;
      let moveToIndex = parseInt(y / (this.moveElement.height + 10), 10) * 4 + parseInt(x / (this.moveElement.width + 10), 10);
      this.hardwareOverview.removeEqual((o) => o.type === 'TMP');

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
          this.hardwareOverview.removeEqual((o) => o.type === 'TMP');
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
