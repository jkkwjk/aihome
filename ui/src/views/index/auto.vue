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
            <el-table-column prop="autoId" label="自动化ID" width="100" />
            <el-table-column label="cron表达式" min-width="250">
              <template slot-scope="scope">
                <div class="name">
                  <editable-span
                    :text="scope.row.cron"
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
                  <el-switch v-model="scope.row.enabled"/>
                  <i class="el-icon-arrow-left show-code-btn" @click="handleShowCode(scope.$index)"></i>
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
            <el-table-column prop="autoId" label="自动化ID" width="100" />
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
                  <el-switch v-model="scope.row.enabled"/>
                  <i class="el-icon-arrow-left show-code-btn" @click="handleShowCode(scope.$index)"></i>
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

export default {
  name: 'auto',
  components: { CodeView, EditableSpan },
  data() {
    const version = '# version: Python3\n\n';
    const codeAreaTip = '# 注意：写在外部的变量各脚本间上下文将会共享\n\n';
    const codeTip = "'''\n下面的函数将会按照定时或者状态驱动的方式执行\n'''\n";
    const code = 'def doAutoTask(states):\n\t# write your code here.\n\tpass';
    const initValue = version + codeAreaTip + codeTip + code;

    return {
      code: 'version: Python',
      searchAutoText: '',
      searchEventText: '',
      codeEditor: false,
      allEvent: [
        'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
      ],
      defaultPython: initValue,
      cronAuto: [
        {
          name: '卧室等暗了则把厨房的灯打开厨房',
          autoId: '1231231',
          cron: '10/0 10/0 10/0 10/0 10/0 10/0 ?',
          enabled: true,
        },
        {
          name: '123',
          autoId: '1231231',
          cron: '10/0 10/0 10/0 10/0 10/0 10/0 ?',
          enabled: true,
        },
        {
          name: '123',
          autoId: '1231231',
          cron: '10/0 10/0 10/0 10/0 10/0 10/0 ?',
          enabled: true,
        },
        {
          name: '123',
          autoId: '1231231',
          cron: '10/0 10/0 10/0 10/0 10/0 10/0 ?',
          enabled: true,
        },
        {
          name: '123',
          autoId: '1231231',
          cron: '10/0 10/0 10/0 10/0 10/0 10/0 ?',
          enabled: true,
        },
        {
          name: '123',
          autoId: '1231231',
          cron: '10/0 10/0 10/0 10/0 10/0 10/0 ?',
          enabled: true,
        },
        {
          name: '123',
          autoId: '1231231',
          cron: '10/0 10/0 10/0 10/0 10/0 10/0 ?',
          enabled: true,
        },
        {
          name: '123',
          autoId: '1231231',
          cron: '10/0 10/0 10/0 10/0 10/0 10/0 ?',
          enabled: true,
        },
        {
          name: '1234',
          autoId: '1231231',
          cron: '10/0 10/0 10/0 10/0 10/0 10/0 ?',
          enabled: true,
        },
      ],
      eventAuto: [
        {
          name: '卧室等暗了则把厨房的灯打开厨房',
          autoId: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enabled: true,
        },
        {
          name: '123',
          autoId: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enabled: true,
        },
        {
          name: '123',
          autoId: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enabled: true,
        },
        {
          name: '123',
          autoId: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enabled: true,
        },
        {
          name: '123',
          autoId: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enabled: true,
        },
        {
          name: '123',
          autoId: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enabled: true,
        },
        {
          name: '123',
          autoId: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enabled: true,
        },
        {
          name: '123',
          autoId: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enabled: true,
        },
        {
          name: '1234',
          autoId: '1231231',
          events: [
            'event.device.abc-1', 'event.device.abc-2', 'event.device.abc-3', 'event.device.abc-4', 'event.device.abc-5',
          ],
          enabled: true,
        },
      ],
    };
  },
  methods: {
    handleEditName(row, name) {
      row.name = name;
    },
    handleEditCron(row, cron) {
      row.cron = cron;
    },
    handleEventChange(row, event) {
      console.log(row, event);
    },
    handleAddAuto(type) {
      if (type === 0) {
        this.cronAuto.unshift({
          name: '默认名称',
          autoId: '1231231',
          cron: '请设置',
          enabled: true,
        });
      } else {
        this.eventAuto.unshift({
          name: '默认名称',
          autoId: '1231231',
          events: [],
          enabled: true,
        });
      }
    },
    handleShowCode(e) {
      console.log(e);
      this.code = e.toString();
      this.codeEditor = true;
    },
    handleCodeClose(done) {
      this.$confirm('确认关闭？')
        .then(() => {
          done();
        })
        .catch(() => {});
    },
    runOnce() {

    },
    saveCode() {

    },
    deleteAuto() {

    },
  },
  watch: {
    code() {
      console.log(this.code);
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
