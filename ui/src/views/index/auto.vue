<template>
  <div>
    自动化
    <textarea ref="editor"></textarea>
  </div>
</template>

<script>
import CodeMirror from '@assets/thirdpart/codemirror/importAll';

export default {
  name: 'auto',
  mounted() {
    const el = this.$refs.editor;
    const version = '# version: Python3\n\n';
    const codeAreaTip = '# please edit your code here:\n';
    const codeStart = '# code start\n\n';
    const codeEnd = '# code end\n\n';
    const codeTip = "'''\nThis function is the entry of this program and\nit must be return your answer of current question.\n'''\n";
    const code = 'def solution():\n\tpass';
    const initValue = version + codeAreaTip + codeStart + codeEnd + codeTip + code;
    const myCodeMirror = CodeMirror.fromTextArea(el, {
      mode: 'python',
      theme: 'leetcode',
      keyMap: 'sublime',
      lineNumbers: true,
      smartIndent: true,
      indentUnit: 4,
      indentWithTabs: true,
      lineWrapping: true,
      gutters: ['CodeMirror-linenumbers', 'CodeMirror-foldgutter', 'CodeMirror-lint-markers'],
      foldGutter: true,
      autofocus: true,
      matchBrackets: true,
      autoCloseBrackets: true,
      styleActiveLine: true,
    });
    myCodeMirror.setOption('value', initValue);
    myCodeMirror.on('keypress', () => {
      myCodeMirror.showHint();
    });
  },

  methods: {

  },
};
</script>

<style scoped>

</style>
