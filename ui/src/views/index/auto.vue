<template>
  <div class="auto">
    <el-card style="position:relative;">
      <span style="font-size: 20px; margin-bottom: 5px;">定时任务 :</span>
      <div class="item">
        <el-scrollbar>
          <el-table :data="cronAuto.filter((o) => o.name.indexOf(searchAutoText) !== -1)">
            <el-table-column label="名称" min-width="300">
              <template slot-scope="scope">
                <div class="name">
                  <editable-span
                    :text="scope.row.name"
                    @edit="(text) => handleEditName(scope.row, text)"
                  >
                  </editable-span>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="id" label="自动化ID" width="100" />
            <el-table-column label="cron表达式" min-width="250">
              <template slot-scope="scope">
                <div class="name">
                  <editable-span
                    :text="scope.row.cron"
                    emptyText="请输入表达式"
                    @edit="(text) => handleEditCron(scope.row, text)"
                  >
                  </editable-span>
                </div>
              </template>
            </el-table-column>
            <el-table-column align="right" fixed="right">
              <template slot="header" slot-scope="/*eslint-disable vue/no-unused-vars*/scope">
                <el-input v-model="searchAutoText" size="mini" placeholder="输入自动化名称搜索"/>
              </template>
              <template slot-scope="scope">
                <div style="white-space: nowrap;">
                  <el-switch :value="scope.row.enable" @input="value => handleEnableChange(scope.row, value)"/>
                  <i class="el-icon-arrow-left show-code-btn" @click="handleShowCode(scope.row)"></i>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </el-scrollbar>
      </div>
      <div style="position:absolute; top: 10px; right: 10px; z-index: 1000;">
        <el-button type="warning" icon="el-icon-plus" circle @click="handleAddAuto(0)"/>
      </div>
    </el-card>

    <el-card style="margin-top: 20px; position:relative;">
      <span style="font-size: 20px; margin-bottom: 5px;">事件触发任务 :</span>
      <div class="item">
        <el-scrollbar>
          <el-table :data="eventAuto.filter((o) => o.name.indexOf(searchEventText) !== -1)">
            <el-table-column label="名称" min-width="300">
              <template slot-scope="scope">
                <div class="name">
                  <editable-span
                    :text="scope.row.name"
                    @edit="(text) => handleEditName(scope.row, text)"
                  >
                  </editable-span>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="id" label="自动化ID" width="100" />
            <el-table-column label="绑定的事件" min-width="250">
              <template slot-scope="scope">
                  <div slot>
                    <el-select v-model="scope.row.events" placeholder="请选择"
                               multiple collapse-tags @change="(events)=>handleEventChange(scope.row, events)">
                      <el-option
                        v-for="event in allEvent"
                        :key="event"
                        :label="event"
                        :value="event">
                      </el-option>
                    </el-select>
                  </div>
              </template>
            </el-table-column>
            <el-table-column align="right" fixed="right">
              <template slot="header" slot-scope="/*eslint-disable vue/no-unused-vars*/scope">
                <el-input v-model="searchEventText" size="mini" placeholder="输入自动化名称搜索"/>
              </template>
              <template slot-scope="scope">
                <div style="white-space: nowrap;">
                  <el-switch :value="scope.row.enable" @input="value => handleEnableChange(scope.row, value)"/>
                  <i class="el-icon-arrow-left show-code-btn" @click="handleShowCode(scope.row)"></i>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </el-scrollbar>
      </div>
      <div style="position:absolute; top: 10px; right: 10px; z-index: 1000;">
        <el-button type="warning" icon="el-icon-plus" circle @click="handleAddAuto(1)"/>
      </div>
    </el-card>

    <el-drawer
      title="执行Code"
      destroy-on-close
      :visible.sync="codeEditor"
      direction="rtl"
      size="60%"
      :before-close="handleCodeClose">
      <code-view :code.sync="code"></code-view>
      <div style="float: right; margin-top: 20px; margin-right: 40px;">
        <el-button type="success" @click="runOnce">试 一 试</el-button>
        <el-button type="primary" @click="saveCode">保 存</el-button>
        <el-popconfirm
          confirm-button-text='好的'
          cancel-button-text='不用了'
          icon="el-icon-info"
          icon-color="red"
          title="删除该项自动化？"
          @confirm="deleteAuto"
        >
          <el-button type="danger" slot="reference" style="margin-left: 10px;">删 除</el-button>
        </el-popconfirm>
      </div>

    </el-drawer>
  </div>
</template>

<script>

import CodeView from '@components/auto/CodeView';
import EditableSpan from '@components/EditableSpan';
import autoApi from '@/api/AutoApi';

export default {
  name: 'auto',
  components: { CodeView, EditableSpan },
  data() {
    const version = '# version: Python3\n\n';
    const codeTip = "'''\n下面的函数将会按照定时或者状态驱动的方式执行\n";
    const asyncTip = "各个脚本之间将会以阻塞队列方式同步执行, 所以——某些执行时间长的任务请新建线程~\n'''\n\n\n";
    const code = 'def doAutoTask(states, globals):\n\t# write your code here.\n\t';
    const control = 'control = {}\n\treturn control\n\t';
    const initValue = version + codeTip + asyncTip + code + control;

    return {
      code: '',
      editCodeId: null,
      searchAutoText: '',
      searchEventText: '',
      codeEditor: false,
      allEvent: [
        'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
      ],
      defaultPython: initValue,
      cronAuto: [],
      eventAuto: [
        {
          name: '卧室等暗了则把厨房的灯打开厨房',
          id: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enable: true,
        },
        {
          name: '123',
          id: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enable: true,
        },
        {
          name: '123',
          id: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enable: true,
        },
        {
          name: '123',
          id: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enable: true,
        },
        {
          name: '123',
          id: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enabled: true,
        },
        {
          name: '123',
          id: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enabled: true,
        },
        {
          name: '123',
          id: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enabled: true,
        },
        {
          name: '123',
          id: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enabled: true,
        },
        {
          name: '1234',
          id: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enabled: true,
        },
      ],
    };
  },
  created() {
    this.getAllCronAuto();
  },
  methods: {
    async getAllCronAuto() {
      this.cronAuto = await autoApi.getAllAuto('timer');
    },
    async handleEditName(row, name) {
      if (await autoApi.modifyName(row.id, name) === true) {
        this.$message.success('重命名成功');
        row.name = name;
      }
    },
    async handleEnableChange(row, enable) {
      if (await autoApi.modifyEnable(row.id, enable) === true) {
        this.$message.success(`自动化已${row.enable ? '失效' : '生效'}`);
        row.enable = enable;
      }
    },
    async handleEditCron(row, cron) {
      if (await autoApi.modifyCron(row.id, cron) === true) {
        this.$message.success('cron 修改成功');
        row.cron = cron;
      }
    },
    handleEventChange(row, event) {
      console.log(row, event);
    },
    async handleAddAuto(type) {
      const result = await autoApi.add({ type });
      this.$message.success('添加成功');
      if (type === 0) {
        this.cronAuto.unshift(result);
      } else if (type === 1) {
        this.eventAuto.unshift({
          name: '默认名称',
          id: '1231231',
          events: [],
          enabled: true,
        });
      }
    },
    async handleShowCode(row) {
      const code = await autoApi.getCode(row.id);

      if (code === null) {
        this.code = this.defaultPython;
      } else {
        this.code = code;
      }

      this.editCodeId = row.id;
      this.codeEditor = true;
    },
    handleCodeClose(done) {
      this.$confirm('确认关闭？')
        .then(() => {
          done();
        })
        .catch(() => {});
    },
    async runOnce() {
      const data = await autoApi.tryOnce(this.code);
      this.$notify({
        title: '返回信息',
        message: data,
        type: 'success',
      });
    },
    async saveCode() {
      if (await autoApi.modifyCode(this.editCodeId, this.code) === true) {
        this.$message.success('code 保存成功');
      }
    },
    async deleteAuto() {
      if (await autoApi.remove(this.editCodeId) === true) {
        this.$message.success('删除成功');
        this.codeEditor = false;

        await this.getAllCronAuto();
      }
    },
  },
};
</script>

<style scoped lang="scss">
  .auto {
    height: calc(100vh - 50px);
    .item {
      box-sizing: border-box;
      height: 100%;
    }
  }

  .name {
    display: flex;
    align-items: center;
    white-space: nowrap;

    div {
      flex: 1;
    }
  }

  .show-code-btn {
    font-size: 16px;
    margin-left: 20px;
    margin-right: 10px;
  }

  .button-new-tag {
    margin-left: 5px;
    height: 24px;
    line-height: 24px;
    padding-top: 0;
    padding-bottom: 0;
  }

  .el-table--fit {
    &::v-deep .el-table__body-wrapper::-webkit-scrollbar{
      display: none;
    }

    &::v-deep .el-table__fixed-right {
      height: 100% !important;
    }
  }

  .el-card {
    height: 50%;

    &::v-deep .el-card__body{
      box-sizing: border-box;
      height: 100%;
    }
  }

  .el-scrollbar {
    height: 100%;
    overflow: unset;
    &::v-deep .el-scrollbar__wrap {
      overflow: unset;
      overflow-x: unset !important;
      overflow-y: scroll !important;
      padding-right: 20px;

      &::-webkit-scrollbar {
        display: none;
      }
    }

    &::v-deep .el-scrollbar__bar {
      z-index: 100;
    }
    &::v-deep .is-vertical {
      margin-right: -17px;
    }
  }
</style>
