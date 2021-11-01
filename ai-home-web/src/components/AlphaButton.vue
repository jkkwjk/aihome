<template>
  <span style="position: relative">
    <div class="blur-mask">

    </div>
    <el-button v-bind="$attrs" v-on="$listeners" class="blur-btn">
      <slot />
    </el-button>
  </span>

</template>

<script>
export default {
  name: 'AlphaButton',
};
</script>

<style scoped>
  /*todo*/
  .blur-mask {
    z-index: -1;
    position: absolute;
    width: 100%;
    height: 100%;

    filter: blur(20px);
  }

  .blur-btn {
    color: white;
    background-color: rgba(0,0,0,0);

  }
</style>
