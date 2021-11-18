<template>
  <div class="alert">
    <div class="start" :class="type"></div>
    <div class="message">
      <slot></slot>
    </div>
    <div class="close">
      <img src="@/assets/image/icon/close.png" alt="">
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Alert',
    props: {
      type: String
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";


  .alert {
    display: inline-block;
    z-index: 100;
    width: 50vw;
    height: 5rem;
    position: fixed;
    top: 3rem;
    left: 50%;
    transform: translateX(-50%);
    background-color: $white;
    border-radius: 3px;
    overflow: hidden;
    display: flex;
    align-items: center;
    box-shadow: 3px 3px 15px 2px rgba($color: #000000, $alpha: 0.1);

    .start {
      height: 100%;
      width: 5px;
      position: absolute;
      left: 0;
    }

    .message {
      position: absolute;
      left: 50%;
      transform: translateX(-50%);
    }

    .close {
      position: absolute;
      right: 20px;
      width: 20px;
      height: 20px;

      img {
        width: 100%;
        height: 100%;
        opacity: 0.3;
      }
    }
  }

  .success {
    background-color: $success;
  }

  .danger {
    background-color: $danger;
  }
</style>