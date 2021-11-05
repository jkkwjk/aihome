<template>
  <div class="auto">

    <code-view :code.sync="code"></code-view>
  </div>
</template>

<script>

import CodeView from '@components/auto/CodeView';

export default {
  name: 'auto',
  components: { CodeView },
  data() {
    return {
      code: 'version: Python',
    };
  },
  methods: {
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

<style scoped>

</style>
