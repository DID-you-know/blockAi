<template>
  <div class="form-item">
    <label class="label fw-light">{{ label }}</label>
    <input :type="type" :onpaste="() => paste"/>
    <span class="error fw-light">{{ error }}</span>
  </div>
</template>

<script>
  export default {
    name: 'Input',
    props: {
      label: String,
      type: {
        type: String,
        default: 'text'
      },
      error: String,
      paste: {
        type: Boolean,
        default: true
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";

  .form-item {
    position: relative;

    .label {
      position: absolute;
      top: -27px;
      left: 5px;
    }

    .error {
      color: $danger;
      padding: 5px;
    }
  }
</style>