<template>
  <textarea ref="editor"></textarea>
</template>

<script>
import CodeMirror from '@assets/thirdpart/codemirror/importAll';

export default {
  name: 'CodeView',
  props: {
    code: { type: String, required: true },
  },
  mounted() {
    const el = this.$refs.editor;
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
    myCodeMirror.setOption('value', this.code);
    myCodeMirror.on('keypress', () => {
      myCodeMirror.showHint();
    });
    this.myCodeMirror = myCodeMirror;
    this.code__inner = this.myCodeMirror.getValue();

    this.$watch(
      () => this.myCodeMirror.getValue(),
      () => {
        this.code__inner = this.myCodeMirror.getValue();
      },
    );
  },

  data() {
    return {
      myCodeMirror: null,
    };
  },

  computed: {
    code__inner: {
      get() {
        return this.code;
      },
      set(v) {
        this.$emit('update:code', v);
      },
    },
  },
};
</script>

<style scoped>

</style>
