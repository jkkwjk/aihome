<template>
  <div class="auto">
    <el-card>
      <span style="font-size: 20px; margin-bottom: 5px;">定时任务 :</span>
      <div class="item">
        <el-scrollbar>
          <el-table :data="cronAuto.filter((o) => o.name.indexOf(searchAutoText) !== -1)">
            <el-table-column label="名称" >
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
            <el-table-column prop="cron" label="cron表达式">
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
            <el-table-column align="right">
              <template slot="header" slot-scope="/*eslint-disable vue/no-unused-vars*/scope">
                <el-input v-model="searchAutoText" size="mini" placeholder="输入自动化名称搜索"/>
              </template>
              <template slot-scope="scope">
                <el-switch v-model="scope.row.enabled" />
                <i class="el-icon-arrow-left show-code-btn" @click="handleShowCode(scope.$index)"></i>
              </template>
            </el-table-column>
          </el-table>
        </el-scrollbar>
      </div>
    </el-card>

    <el-card style="margin-top: 20px;">
      <span style="font-size: 20px; margin-bottom: 5px;">事件触发任务 :</span>
      <div class="item">
        <el-scrollbar>
          <el-table :data="cronAuto.filter((o) => o.name.indexOf(searchEventText) !== -1)" >
            <el-table-column label="名称" >
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
            <el-table-column prop="cron" label="cron表达式">
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
            <el-table-column align="right">
              <template slot="header" slot-scope="/*eslint-disable vue/no-unused-vars*/scope">
                <el-input v-model="searchEventText" size="mini" placeholder="输入自动化名称搜索"/>
              </template>
              <template slot-scope="scope">
                <el-switch v-model="scope.row.enabled" />
              </template>
            </el-table-column>
          </el-table>
        </el-scrollbar>
      </div>
    </el-card>

    <el-drawer
      title="执行Code"
      destroy-on-close
      :visible.sync="codeEditor"
      direction="rtl"
      size="60%"
      :before-close="handleClose">
      <code-view :code.sync="code"></code-view>
      <div style="float: right; margin-top: 20px; margin-right: 40px;">
        <el-button type="success" @click="runOnce">试 一 试</el-button>
        <el-button type="primary" @click="saveCode">保 存</el-button>
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
    return {
      code: 'version: Python',
      searchAutoText: '',
      searchEventText: '',
      codeEditor: false,
      cronAuto: [
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
    };
  },
  methods: {
    handleEditName(row, name) {
      row.name = name;
    },
    handleEditCron(row, cron) {
      row.cron = cron;
    },
    handleShowCode(e) {
      console.log(e);
      this.code = e.toString();
      this.codeEditor = true;
    },
    handleClose(done) {
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
    a() {
      const version = '# version: Python3\n\n';
      const codeAreaTip = '# 注意：写在外部的变量各脚本间上下文将会共享\n\n';
      const codeTip = "'''\n下面的函数将会按照定时或者状态驱动的方式执行\n'''\n";
      const code = 'def doAutoTask(states):\n\t# write your code here.\n\tpass';
      const initValue = version + codeAreaTip + codeTip + code;
      console.log(initValue);
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

  .el-card {
    height: 50%;

    &::v-deep .el-card__body{
      box-sizing: border-box;
      height: 100%;
    }
  }

  .el-scrollbar {
    height: 100%;

    &::v-deep .el-scrollbar__wrap {
      overflow: unset;
      overflow-x: unset !important;
      overflow-y: scroll !important;
    }
  }
</style>
