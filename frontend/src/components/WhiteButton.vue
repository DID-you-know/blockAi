<template>
  <button class="fs-1">{{ value }}</button>
</template>

<script>
  export default {
    name: 'WhiteButton',
    props: {
      value: String
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";

  button {
    background-color: $white;
    color: $primary;
    padding: 1rem 1.3rem;
    border-radius: 1rem;

    &:hover {
      background-color: $white-hover;
    }
  }
</style>