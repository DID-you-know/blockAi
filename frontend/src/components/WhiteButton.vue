<template>
  <button :class="'fs-' + size +' size-' + size">{{ value }}</button>
</template>

<script>
  export default {
    name: 'WhiteButton',
    props: {
      value: String,
      size: {
        type: String,
        default: '1'
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";

  button {
    background-color: $white;
    color: $primary;

    &:hover {
      background-color: $white-hover;
    }
  }

  .size-1 {
    border-radius: 1rem;
    padding: 1rem 1.3rem;
  }

  .size-2 {
    border-radius: 1rem;
    padding: 1rem 1.5rem;
  }

  .size-3 {
    border-radius: 1.3rem;
    padding: 1.3rem 2rem;
  }

  .size-4 {
    border-radius: 1.5rem;
    padding: 1.5rem 3rem;
  }
</style>