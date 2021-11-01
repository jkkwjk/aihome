<template>
  <el-menu
    router
    :mode="mode"
    default-active="0"
    @select="handleSelect"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b">
    <el-menu-item index="0" route="overview">
      <i class="el-icon-menu"></i>
      <span slot="title">概览</span>
    </el-menu-item>
    <el-menu-item index="1" route="auto">
      <i class="el-icon-document"></i>
      <span slot="title">自动化</span>
    </el-menu-item>
    <el-menu-item index="2" route="hard-management">
      <i class="el-icon-setting"></i>
      <span slot="title">硬件管理</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: 'mAside',
  props: {
    mode: { required: true, type: String },
  },
  methods: {
    handleSelect() {
      // switch (index) {
      //   case '0':
      //     this.$router.push('/overview');
      //     break;
      //   case '1':
      //     this.$router.push('/auto');
      //     break;
      //   case '2':
      //     this.$router.push('/hard-management');
      //     break;
      //   default:
      //     break;
      // }
    },

  },

};
</script>

<style scoped>
  .el-menu{
    height: 100%;
    z-index: 1;
  }
</style>
