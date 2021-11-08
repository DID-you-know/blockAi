<template>
  <div class="progress-bar">
    <div class="circle first"></div>
    <div class="circle second"></div>
    <div class="circle third"></div>
    <div class="circle fourth"></div>
    <div v-if="step === 1" class="pill first">얼굴 등록</div>
    <div v-if="step === 2" class="pill second">음성 등록</div>
    <div v-if="step === 3" class="pill third">블록체인 생성</div>
    <div v-if="step === 4" class="pill fourth">발급 완료</div>
    <div class="bar"></div>
  </div>
</template>

<script>
  export default {
    name: 'Progressbar',
    props: {
      step: Number
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";

  $D: 20px;
  $H: 3px;
  
  .progress-bar {
    width: 50%;
    position: relative;
    text-align: center;

    .bar {
      width: 100%;
      height: $H;
      background: $white;
      position: relative;
    }

    .circle {
      background: $white;
      width: $D;
      height: $D;
      border-radius: 50%;
    }

    .pill {
      background: $white;
      border-radius: 5rem;
      color: $primary;
      padding: 2px 2rem;
      z-index: 1;
    }

    .first {
      position: absolute;
      left: 0;
      transform: translate(-50%, -50%);
    }

    .second {
      position: absolute;
      left: 33%;
      transform: translate(-50%, -50%);
    }

    .third {
      position: absolute;
      right: 33%;
      transform: translate(50%, -50%);
    }

    .fourth {
      position: absolute;
      right: 0;
      transform: translate(50%, -50%);
    }
  }
</style>