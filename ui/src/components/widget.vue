<template>
  <el-card :class="[
           isShake? 'shake':'',
           'widget',
           moveMe? 'move-this':'']"
           ref="card"
           @mousedown.native.self="handleMouseDown"
           @mouseup.native="handleMouseUp"
           @mouseleave.native="handleMouseLeave"
           @touchstart.native="handleMouseDown"
           @touchend.native="handleMouseUp"
           :style="{ 'width': moveMe? this.$store.state.moveElement.width+'px':'unset',
           'left': this.$store.state.mouse.moveX - this.offset.x + 'px',
           'top': this.$store.state.mouse.moveY - this.offset.y + 'px' }">
    <div slot="header" class="header" v-if="title">{{ title }}</div>
    <div style="display: flex; flex-direction: column; align-items: center">
      <el-popconfirm
        confirm-button-text='好的'
        cancel-button-text='不用了'
        icon="el-icon-info"
        icon-color="red"
        title="从概览中删除该组件(不会删除硬件实体)？"
        @confirm="handleDeleteWidget"
      >
        <el-button slot="reference"
                   type="danger"
                   class="close-btn"
                   icon="el-icon-close"
                   circle
                   style="display: none"></el-button>
      </el-popconfirm>

      <slot/>
    </div>
  </el-card>
</template>

<script>

export default {
  name: 'widget',
  props: {
    title: { type: String, required: false },
    devId: { type: String, required: true },
  },
  data() {
    return {
      time: 500,
      offset: {},
      timeout: null,
    };
  },

  methods: {
    handleDeleteWidget() {
      console.log(`delete ${this.devId}`);
      this.$store.commit('setShake', false);
    },

    handleMouseDown() {
      if (this.isShake) {
        // 拖动效果
        const posr = this.$refs.card.$el;
        this.$store.commit('setMoveWidth', posr.clientWidth);
        this.$store.commit('setMoveHeight', posr.clientHeight);
        this.offset.x = this.$store.state.mouse.moveX - posr.offsetLeft;
        this.offset.y = this.$store.state.mouse.moveY - posr.offsetTop;
        this.$store.commit('setMoveDevId', this.devId);
      } else {
        this.timeout = setTimeout(() => {
          this.$store.commit('setShake', true);
        }, this.time);
      }
    },

    handleMouseUp() {
      this.$store.commit('setMoveDevId', null);
      // console.log('up');
      clearTimeout(this.timeout);
    },

    handleMouseLeave() {
      // console.log('leave');
      clearTimeout(this.timeout);
    },
  },
  computed: {
    isShake() {
      return this.$store.state.isShake;
    },
    moveMe() {
      return this.$store.state.moveElement.devId === this.devId;
    },
  },

};
</script>

<style scoped lang="scss">
  .widget {
    user-select: none;
    margin-bottom: 10px;
    /*transition: 3s;*/
    min-height: 230px;
  }

  .move-this {
    position: absolute;
    z-index: 1;
  }

  .el-card {
    align-items: center;
    overflow: unset;
    transition: unset;
  }

  .el-card ::v-deep .el-card__header {
    padding: 5px 10px;
  }

  .header {
    font-size: 17px;
  }

  .shake {
    animation: shake 300ms infinite;

    .close-btn {
      display: inline-block !important;
      padding: 0;
      position: absolute;
      right: -5px;
      top: -5px;
    }
  }

  @keyframes shake
  {
    0% {
      transform: rotate(0deg);
    }
    25% {
      transform: rotate(0.5deg);
    }
    50% {
      transform: rotate(0deg);
    }
    75% {
      transform: rotate(-0.5deg);
    }
    100% {
      transform: rotate(0deg);
    }
  }
</style>
