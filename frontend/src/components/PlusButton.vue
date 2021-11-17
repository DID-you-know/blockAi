<template>
  <div class="button" @mouseover="onMouseOver=true" @mouseleave="onMouseOver=false">
    <img v-if="!onMouseOver" src="@/assets/image/icon/plus-icon.png" alt="">
    <span v-else class="text nanumGothic">재발급</span>
  </div>
</template>

<script>
  import { ref } from 'vue'


  export default {
    name: 'PlusButton',
    setup() {
      const onMouseOver = ref(false)

      return {
        onMouseOver,
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";

  .button {
    background-color: $secondary-hover;
    width: 3rem;
    height: 3rem;
    border-radius: 1.5rem;
    cursor: pointer;
    box-shadow: 2px 2px 4px 2px rgba($color: #000000, $alpha: 0.2);
    display: flex;
    justify-content: center;
    align-items: center;
    transition: width 0.6s ease;
    overflow: hidden;
    white-space: nowrap;

    &:hover {
      width: 6.5rem;
    }

    img {
      width: 1.5rem;
      height: 1.5rem;
    }

    .text {
      color: $white;
      font-size: 1.2rem;
    }
  }
</style>7