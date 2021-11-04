<template>
  <div class="editable-span">
    <span @click="editing = true">{{ text === ''? emptyText : text }}</span>
    <el-input v-if="editing" v-model="inputText" @blur="editing = false" ref="input" size="mini">
      <i class="el-icon-check el-input__icon" slot="suffix" @mousedown.prevent="handleClick" />
    </el-input>
  </div>
</template>

<script>
export default {
  name: 'EditableSpan',

  created() {
    this.inputText = this.text;
  },

  props: {
    text: { required: true, type: String }, // 文本
    emptyText: { required: false, type: String, default: '无' }, // 空字符的显示
    allowContinueEditing: { required: false, type: Boolean, default: false }, // 是否允许停止输入后接着上次继续输入
  },

  data() {
    return {
      editing: false,
      inputText: '',
    };
  },

  watch: {
    editing(val) {
      if (val) {
        if (!this.allowContinueEditing) {
          this.inputText = this.text;
        }

        this.$nextTick(() => {
          this.$refs.input.focus();
        });
      }
    },

    text(val) {
      this.inputText = val;
    },
  },

  methods: {
    handleClick() {
      this.editing = false;
      this.$emit('edit', this.inputText);
    },
  },

};
</script>

<style scoped>
  span{
    line-height: 30px;
    padding: 0 15px;
  }

  .editable-span{
    position: relative;
  }

  .el-input{
    position: absolute;
    top: 0;
    left: 0;
  }
</style>
