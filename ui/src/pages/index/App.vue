<template>
  <el-row class="app">
    <el-col :xs="0" :sm="4">
      <m-aside mode="vertical"></m-aside>
    </el-col>

    <el-col :xs="24" :sm="0">
      <m-aside mode="horizontal"></m-aside>
    </el-col>

    <el-col class="main" :sm="20" :xs="24">
      <router-view />
    </el-col>
  </el-row>

</template>

<script>
import mAside from '../../components/mAside';

export default {
  name: 'App',
  components: { mAside },
  data() {
    return {

    };
  },
};
</script>

<style scoped lang="scss">
  .main {
    background-color: rgb(50, 53, 56);
    height: 100%;
    padding: 20px;
  }
  @media (max-width: 767px){
    .app{
      display: flex;
      flex: 1;
      flex-direction: column;
      height: 100%;
    }
  }
  @media (min-width: 767px){
    .app{
      display: flex;
      flex: 1;
      height: 100%;
    }
  }
</style>
