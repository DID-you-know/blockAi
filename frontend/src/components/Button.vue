<template>
  <div>
    <button class="fs-1">{{ value }}</button>
  </div>
</template>

<script>
  export default {
    name: 'Button',
    props: {
      value: String
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";

  button {
    background-color: $primary;
    color: $white;
    padding: 1rem 1.3rem;
    border-radius: 3rem;
    box-shadow: 2px 2px 6px 2px rgba(0, 0, 0, 0.2);

    &:hover {
      background-color: $primary-hover;
    }
  }
</style>
