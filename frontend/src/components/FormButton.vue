<template>
  <button class="fs-1" :disabled="disabled">{{ value }}</button>
</template>

<script>
  export default {
    name: 'FormButton',
    props: {
      value: String,
      disabled: {
        type: Boolean,
        default: false
      }
    },
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";

  button {
    background-color: $primary;
    color: $white;
    width: 100%;
    padding: 12px 15px;
    border-radius: 10px;
    font-size: 20px;

    &:hover:enabled {
      background-color: $primary-hover;
    }

    &:disabled {
      background-color: $secondary;
      cursor: default;
    }
  }
</style>