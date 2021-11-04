<template>
  <div class="form-item">
    <label class="label fw-light">{{ label }}</label>
    <input :type="type">
  </div>
</template>

<script>
  export default {
    name: 'Input',
    props: {
      label: String,
      type: String
    }
  }
</script>

<style lang="scss" scoped>
  .form-item {
    position: relative;

    .label {
      position: absolute;
      top: -27px;
      left: 5px;
    }
  }
</style>