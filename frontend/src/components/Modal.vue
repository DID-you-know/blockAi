<template>
  <div class="modal-backdrop">
    <transition name="modal-float">
      <div v-if="isModalVisible" class="modal">
        <slot></slot>
      </div>
    </transition>
  </div>
</template>

<script>
  export default {
    name: 'Modal',
    props: {
      isModalVisible: Boolean
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";


  .modal-backdrop {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background-color: rgba($color: #000000, $alpha: 0.5);
    display: flex;
    justify-content: center;
    align-items: center;

    .modal {
      border-radius: 10vh;
      padding-bottom: 10vh;
      width: 100vw;
      height: 100vh;
      position: relative;
      top: 10vh;
      left: 0;
    }
  }

  .modal-float-enter-from,
  .modal-float-leave-to {
    transform: translateY(100vh);
  }

  .modal-float-enter-active,
  .modal-float-leave-active {
    transition: all 1s ease;
  }
</style>