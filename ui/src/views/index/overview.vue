<template>
  <div class="overview" v-loading.fullscreen.lock="loading">
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
      <all-widget :hardware="hardwareOverview" @stateChange="handleStateChange"></all-widget>
    </el-row>

    <el-dialog title="添加到概览"
               :visible.sync="addOverviewDialogVisible">
      <el-row :gutter="10">
        <all-widget :can-shake="false" :hardware="hardwareWithOutOverview" @click="handleAddOverview" @stateChange="$message.error('请先添加到概览再操作')"></all-widget>
      </el-row>
    </el-dialog>
  </div>

</template>

<script>
import AllWidget from '@components/hardWidget/AllWidget';
import overviewApi from '@api/OverviewApi';
import controlApi from '@api/ControlApi';
import AlphaButton from '../../components/AlphaButton';

export default {
  name: 'overview',
  components: {
    AllWidget, AlphaButton,
  },
  created() {
    const overviewSocket = new WebSocket(`ws://${process.env.VUE_APP_WEBSOCKET}/ws/overview`);
    overviewSocket.onmessage = (data) => {
      const socketData = JSON.parse(data.data);
      this.$store.commit('setHardwareOverview', socketData.data);
      this.loading = false;
      if (this.loadingTimeout != null) {
        clearTimeout(this.loadingTimeout);
      }
    };
    this.overviewSocket = overviewSocket;
    // this.$store.commit('setHardwareOverview', await overviewApi.getAll());
  },
  data() {
    return {
      hardwareWithOutOverview: [],
      temp: {
        type: 'TMP',
        stateId: 'tmp',
      },
      originIndex: -1,
      moveToIndex: -1,
      addOverviewDialogVisible: false,
      loading: false,
      loadingTimeout: null,
      overviewSocket: null,
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
    handleStateChange(stateId, state) {
      console.log(stateId, state);
      this.loading = true;
      this.loadingTimeout = setTimeout(() => {
        this.loading = false;
        this.$message.error('设备无响应');
      }, 5000);
      controlApi.updateState(stateId, state);
    },
    handleMouseMove(e) {
      const posr = this.$refs.posr.$el;
      const x = e.clientX - posr.offsetLeft;
      const y = e.clientY - posr.offsetTop;
      this.moveToIndex = parseInt(y / (this.moveElement.height + 20), 10) * 4 + parseInt(x / (this.moveElement.width + 20), 10);
      this.hardwareOverview.removeEqual((o) => o.type === 'TMP');

      if (this.moveToIndex >= this.hardwareOverview.length) {
        this.moveToIndex = this.hardwareOverview.length - 1;
      }
      if (this.moveElement.stateId != null) {
        if (this.moveToIndex > this.originIndex) {
          this.hardwareOverview.splice(this.moveToIndex + 1, 0, this.temp);
        } else {
          this.hardwareOverview.splice(this.moveToIndex, 0, this.temp);
        }
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
      async handler() {
        // 防止闪烁
        if (this.moveElement.stateId != null) {
          this.originIndex = this.hardwareOverview.findIndex((o) => o.stateId === this.moveElement.stateId);
          this.hardwareOverview.splice(this.originIndex, 0, this.temp);
        } else {
          this.hardwareOverview.removeEqual((o) => o.type === 'TMP');
          if (this.originIndex !== this.moveToIndex) {
            if (this.hardwareOverview[this.originIndex].stateId && this.hardwareOverview[this.moveToIndex].stateId) {
              if (this.originIndex < this.moveToIndex) {
                this.moveToIndex += 1;
              }
              this.loading = true;
              this.loadingTimeout = setTimeout(() => {
                this.loading = false;
                this.$message.error('移动失败');
              }, 5000);

              const toStateId = this.moveToIndex < this.hardwareOverview.length ? this.hardwareOverview[this.moveToIndex].stateId : null;
              if (await overviewApi.reorderOverview(this.hardwareOverview[this.originIndex].stateId, toStateId) === true) {
                this.overviewSocket.send('refresh');
              }
              // console.log(this.hardwareOverview[this.originIndex].stateId, this.hardwareOverview[this.moveToIndex].stateId);
            } else {
              this.$message.error('移动失败');
            }
          }
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
