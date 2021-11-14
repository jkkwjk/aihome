<template>
  <div>
    <el-table :data="hardware.filter((o) => o.name.indexOf(searchText) !== -1)" style="margin-bottom: 20px;">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table :data="props.row.states"
            style="margin-bottom: 20px; margin-left: 50px; width: calc(100% - 40px)">
            <el-table-column label="子状态">
              <template slot-scope="scope">
                <div class="name">
                  <i :class="scope.row.icon"/>
                  <editable-span
                    :text="scope.row.name"
                    @edit="(text) => handleEditStateName(scope.row, text)"
                  />
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="stateId" label="状态ID" />
            <el-table-column label="类型">
              <template slot-scope="scope">
                <span>{{ scope.row.type | friendlyType }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="state" label="当前值" />
            <el-table-column prop="reportTime" label="最后一次上报时间" />
          </el-table>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        label="设备名称">
        <template slot-scope="scope">
          <div class="name">
            <i :class="scope.row.icon"></i>
            <editable-span
              :text="scope.row.name"
              @edit="(text) => handleEditHardWare(scope.row, text)"
            >
            </editable-span>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="devId" label="设备ID" width="100" />
      <el-table-column prop="ip" label="设备IP" width="140" />
      <el-table-column prop="heartTime" sortable width="180" label="最后一次心跳时间" />
      <el-table-column prop="discoverTime" sortable width="180" label="设备发现时间" />
      <el-table-column align="right">
        <template slot="header" slot-scope="/*eslint-disable vue/no-unused-vars*/scope">
          <el-input v-model="searchText" size="mini" placeholder="输入设备名称搜索"/>
        </template>
        <template slot-scope="scope">
          <el-popconfirm
            confirm-button-text='好的'
            cancel-button-text='不用了'
            icon="el-icon-info"
            icon-color="red"
            title="删除该硬件实体？(重新发现设备还可以加入)"
            @confirm="handleDeleteHardWare(scope.$index)"
          >
            <el-button size="mini" type="danger" slot="reference">删 除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 浮动组件 -->
    <el-dialog title="正在发现设备" :visible.sync="discoverDialogVisible">
      <el-table
        ref="discoverTable"
        :data="newHardware"
        @selection-change="(e) => {selectNewHardware = e}"
        empty-text="重置设备后连接至aihome开头的WiFi中，访问setup.com网页按步骤完成">
        <el-table-column type="selection" width="45"></el-table-column>
        <el-table-column property="name" label="设备名称"></el-table-column>
        <el-table-column property="ip" label="设备ip"></el-table-column>
      </el-table>

      <span slot="footer">
        <el-button type="primary" @click="handleAddHardware" :disabled="selectNewHardware.length === 0">加 入</el-button>
      </span>
    </el-dialog>
    <div style="position:absolute; bottom: 10px; right: 10px; z-index: 1000;">
      <el-button type="warning" icon="el-icon-plus" circle @click="beforeDiscover"/>
    </div>
  </div>
</template>

<script>

import EditableSpan from '@components/EditableSpan';
import hardwareApi from '../../api/HardwareApi';

export default {
  name: 'hardManagement',
  components: { EditableSpan },
  data() {
    return {
      hardware: [],
      newHardware: [],
      selectNewHardware: [],
      searchText: '',
      discoverDialogVisible: false,
    };
  },

  created() {
    this.getAllHardware();
  },
  methods: {
    async getAllHardware() {
      this.hardware = await hardwareApi.getAll();
    },
    async handleEditHardWare(row, text) {
      if (await hardwareApi.updateHardwareName(row.devId, text) === true) {
        row.name = text;
      }
    },
    handleDeleteHardWare(index) {
      console.log(index);
    },

    async handleEditStateName(row, text) {
      if (await hardwareApi.updateStateName(row.stateId, text) === true) {
        row.name = text;
      }
    },

    handleAddHardware() {
      console.log(this.selectNewHardware);
      this.discoverDialogVisible = false;
    },

    beforeDiscover() {
      this.newHardware = [{
        name: '新设备',
        ip: '255.255.255.255',
      },
      {
        name: '新设备2',
        ip: '255.255.255.255',
      }];
      const table = this.$refs.discoverTable;
      if (table !== undefined) {
        table.clearSelection();
      }
      this.discoverDialogVisible = true;
    },
  },
  filters: {
    friendlyType(type) {
      switch (type) {
        case 'ON_OFF':
          return '布尔型';
        case 'MODE':
          return '枚举型';
        case 'VALUE':
          return '数值型';
        default:
          return '未知';
      }
    },
  },
};
</script>

<style scoped lang="scss">
  .name {
    display: flex;
    align-items: center;
    white-space: nowrap;

    div {
      flex: 1;
    }
  }

  .el-table ::v-deep .el-table__body-wrapper .el-table__empty-block .el-table__empty-text {
    width: 100% !important;
  }
</style>
