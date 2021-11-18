<template>
  <transition name="alert">
    <div v-if="alertOn" class="alert" @click="close">
      <div class="start" :class="alertType"></div>
      <div class="icon">
        <img :src="require(`@/assets/image/icon/${alertType}.png`)" alt="">
      </div>
      <div class="message">
        {{ alertMessage }}
      </div>
      <button class="close" @click="close">
        <img src="@/assets/image/icon/close.png" alt="">
      </button>
    </div>
  </transition>
</template>

<script>
  import { useStore } from 'vuex'
  import { computed } from 'vue'


  export default {
    name: 'Alert',
    setup() {
      const store = useStore()
      const alertMessage = computed(() => store.state.alert.message)
      const alertType = computed(() => store.state.alert.type)   // success, danger
      const alertOn = computed(() => store.state.alert.isOn)

      const close = () => {
        store.commit('alert/CLOSE_ALERT')
      }

      return {
        alertMessage,
        alertType,
        alertOn,
        close
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";


  .alert {
    display: inline-block;
    z-index: 100;
    width: 25rem;
    height: 5rem;
    position: fixed;
    top: 6rem;
    right: 3rem;
    background-color: $white;
    border-radius: 3px;
    overflow: hidden;
    display: flex;
    align-items: center;
    box-shadow: 3px 3px 15px 2px rgba($color: #000000, $alpha: 0.1);
    white-space: nowrap;
    cursor: pointer;

    .start {
      height: 100%;
      width: 5px;
      position: absolute;
      left: 0;
    }

    .message {
      position: absolute;
      left: 5rem;
    }

    .icon {
      width: auto;
      height: 50%;
      position: absolute;
      left: 20px;

      img {
        width: 100%;
        height: 100%;
      }
    }

    .close {
      position: absolute;
      left: 22rem;
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

  .alert-enter-from,
  .alert-leave-to {
    width: 0;
  }

  .alert-enter-active,
  .alert-leave-active {
    transition: width 0.5s ease;
  }
</style>