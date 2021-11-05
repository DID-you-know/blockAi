<template>
  <div>
    <button class="fs-1">{{ value }}</button>
  </div>
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
    border-radius: 3rem;

    &:hover {
      background-color: $white;
    }
  }
</style>