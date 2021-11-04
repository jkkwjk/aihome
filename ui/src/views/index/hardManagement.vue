<template>
  <div>
    <el-table :data="searchHardware" style="margin-bottom: 20px;">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table :data="props.row.spilt"
            style="margin-bottom: 20px; margin-left: 50px;">
            <el-table-column label="子状态">
              <template slot-scope="scope">
                <div class="name">
                  <i :class="scope.row.icon"/>
                  <editable-span
                    :text="scope.row.title"
                    @edit="(text) => handleEditSpiltTitle(scope.row, text)"
                  />
                </div>
              </template>
            </el-table-column>
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
      <el-table-column prop="ip" label="设备IP" width="140" />
      <el-table-column prop="lastHeartTime" sortable width="180" label="最后一次心跳时间" />
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

export default {
  name: 'hardManagement',
  components: { EditableSpan },
  data() {
    return {
      hardware: [
        {
          devId: 'abc',
          icon: 'font-ext1 dengpao',
          name: '卧室灯泡',
          ip: '255.255.255.255',
          lastHeartTime: '2021-11-03 16:20:26',
          discoverTime: '2021-11-03 16:20:29',
          spilt: [
            {
              devId: 'abc-1',
              title: '开关',
              icon: 'el-icon-switch-button',
              type: 0,
              state: 'true',
              reportTime: '2021-11-4 11:38:57',
            },
            {
              devId: 'abc-2',
              title: '亮度',
              icon: '',
              type: 2,
              state: '30',
            },
          ],
        },
        {
          devId: 'abc',
          icon: 'font-ext1 dengpao',
          name: '卧室灯泡',
          ip: '255.255.255.255',
          lastHeartTime: '2021-11-03 16:20:26',
          discoverTime: '2021-11-03 16:20:29',
          spilt: [
            {
              devId: 'abc-1',
              title: '开关',
              icon: 'el-icon-switch-button',
              type: 0,
              state: 'true',
            },
            {
              devId: 'abc-2',
              title: '亮度',
              icon: '',
              type: 2,
              state: '30',
            },
          ],
        },
      ],
      newHardware: [],
      selectNewHardware: [],
      searchText: '',
      discoverDialogVisible: false,
    };
  },

  methods: {
    handleEditHardWare(row, text) {
      row.name = text;
    },
    handleDeleteHardWare(index) {
      console.log(index);
    },

    handleEditSpiltTitle(row, text) {
      row.title = text;
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
  computed: {
    searchHardware() {
      return this.hardware.filter((o) => o.name.indexOf(this.searchText) !== -1);
    },
  },
  filters: {
    friendlyType(type) {
      switch (type) {
        case 0:
          return '布尔型';
        case 1:
          return '枚举型';
        case 2:
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

    div {
      flex: 1;
    }
  }

  .el-table ::v-deep .el-table__body-wrapper .el-table__empty-block .el-table__empty-text {
    width: 100% !important;
  }
</style>
