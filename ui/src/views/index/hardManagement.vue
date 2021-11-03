<template>
  <div>
    <el-table
      :data="hardware"
      style="width: 100%;margin-bottom: 20px;"
      border>
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table
            :data="props.row.spilt"
            style="margin-bottom: 20px;"
            border>
            <el-table-column
              label="子状态">
              <template slot-scope="scope">
                <i :class="scope.row.icon"></i>
                <span style="margin-left: 10px">{{ scope.row.title }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="类型">
              <template slot-scope="scope">
                <span>{{ scope.row.type | friendlyType }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="state"
              label="当前值">
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column
        label="设备名称"
        width="180">
        <template slot-scope="scope">
          <i :class="scope.row.icon"></i>
          <span style="margin-left: 10px">{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="ip"
        label="设备IP"
        width="180">
      </el-table-column>
      <el-table-column
        prop="lastHeartTime"
        sortable
        label="最后一次心跳时间">
      </el-table-column>
      <el-table-column
        prop="discoverTime"
        sortable
        label="设备发现时间">
      </el-table-column>
    </el-table>

  </div>
</template>

<script>
export default {
  name: 'hardManagement',
  data() {
    return {
      hardware: [
        {
          devId: 'abc',
          icon: 'font-ext1 dengpao',
          name: '卧室灯泡',
          ip: '255.255.255.255',
          lastHeartTime: '2021-11-3 16:20:26',
          discoverTime: '2021-11-3 16:20:29',
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
    };
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

<style scoped>

</style>
