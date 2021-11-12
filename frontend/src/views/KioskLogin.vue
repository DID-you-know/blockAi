<template>
  <div class="body">
    <div class="kiosk-login">
      <img class="company-logo" src="@/assets/image/logo.png" alt="">
      <KioskPhoneInput/>
    </div>
  </div>
</template>

<script>
  import KioskPhoneInput from '@/components/KioskPhoneInput'

  export default {
    name: 'KioskLogin',
    components: {
      KioskPhoneInput
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";

  .body {
    background: rgb(109,143,201);
    background: linear-gradient(180deg, rgba(109,143,201,1) 0%, rgba(113,121,200,1) 40%, rgba(114,117,200,1) 60%, rgba(176,162,208,1) 100%);
    text-align: center;

    .kiosk-login {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      height: 100vh;

      .company-logo {
        width: 40vh;
      }
    }
  }
</style>