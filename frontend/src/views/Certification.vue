<template>
  <div class="body certification">
    <Progressbar :step="step" :content="['얼굴 촬영', '음성 녹음', '유사도 판별', '인증 완료']" class="progressbar"/>
    <template v-if="step === 2">
      <div class="message fs-2">
        {{ faceMessage }}
      </div>

      <div v-if="faceStep === 1 && !cameraOn" class="icon-box">
        <img width="400" height="400" src="@/assets/image/icon/faceIdIcon.png" alt="faceIdIcon">
      </div>
      <video v-show="faceStep === 1 && cameraOn" ref="video" autoplay></video>
      <WhiteButton v-if="faceStep === 1 && cameraOn" value="얼굴 촬영" @click="captureFace"/>

      <canvas v-show="faceStep === 2" ref="canvas" width="640" height="480"></canvas>
      <div v-if="faceStep === 2" class="button-box">
        <WhiteButton value="재촬영" @click="reCapture"/>
        <WhiteButton value="촬영 완료" @click="finishCapture"/>
      </div>
    </template>
    <template v-if="step === 3">
      <div class="message fs-2">
        {{ voiceMessage }}
      </div>
      <div class="icon-box">
        <img width="400" height="400" src="@/assets/image/icon/voiceIcon.png" alt="voiceIcon">
        <WhiteButton v-if="voiceStep === 1" value="녹음 시작" @click="recordVoice"/>
        <WhiteButton v-if="voiceStep === 2" value="정지" @click="stopRecord"/>
        <template v-if="voiceStep === 3">
          <audio controls preload="auto">
            <source :src="audioSource" type="audio/wav">
          </audio>
          <div class="button-box">
            <WhiteButton value="재녹음" @click="reRecord"/>
            <WhiteButton value="녹음 완료" @click="finishRecord"/>
          </div>
        </template>
      </div>
    </template>
    <template v-if="step === 4">
      <div class="message fs-2">
        
      </div>
      <div class="loading-box">
        <div class="lds-default"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
      </div>
    </template>
    <template v-if="step === 5">
      <div class="message fs-2">
        신분증명발급이 완료되었습니다.
      </div>
      <WhiteButton value="신분증명 조회하기" @click="router.push({ name: 'home' })"/>
    </template>
    <canvas ref="faceCanvas" hidden></canvas>
  </div>
</template>

<script>
  import Progressbar from '@/components/Progressbar'
  import WhiteButton from '@/components/WhiteButton'
  import { ref } from 'vue'


  export default {
    name: 'Certification',
    components: {
      Progressbar,
      WhiteButton
    },
    setup() {
      const step = ref(1)

      return {
        step
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";


  .certification {
    background: rgb(109,143,201);
    background: linear-gradient(180deg, rgba(109,143,201,1) 0%, rgba(113,121,200,1) 40%, rgba(114,117,200,1) 60%, rgba(176,162,208,1) 100%);
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    gap: 4rem;

    .progressbar {
      margin-top: 8vh;
    }

    .message {
      color: white;
      text-align: center;
    }

    .icon-box {
      border: 2px solid;
      color: $white;
      border-radius: 2rem;
      padding: 50px 150px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      gap: 1rem;

    }
    
    .button-box {
      display: flex;
      gap: 2rem;
    }

    .loading-box {
      height: 100%;

      .lds-default {
        display: inline-block;
        position: relative;
        width: 80px;
        height: 80px;
      }
      .lds-default div {
        position: absolute;
        width: 6px;
        height: 6px;
        background: $white;
        border-radius: 50%;
        animation: lds-default 1.2s linear infinite;
      }
      .lds-default div:nth-child(1) {
        animation-delay: 0s;
        top: 37px;
        left: 66px;
      }
      .lds-default div:nth-child(2) {
        animation-delay: -0.1s;
        top: 22px;
        left: 62px;
      }
      .lds-default div:nth-child(3) {
        animation-delay: -0.2s;
        top: 11px;
        left: 52px;
      }
      .lds-default div:nth-child(4) {
        animation-delay: -0.3s;
        top: 7px;
        left: 37px;
      }
      .lds-default div:nth-child(5) {
        animation-delay: -0.4s;
        top: 11px;
        left: 22px;
      }
      .lds-default div:nth-child(6) {
        animation-delay: -0.5s;
        top: 22px;
        left: 11px;
      }
      .lds-default div:nth-child(7) {
        animation-delay: -0.6s;
        top: 37px;
        left: 7px;
      }
      .lds-default div:nth-child(8) {
        animation-delay: -0.7s;
        top: 52px;
        left: 11px;
      }
      .lds-default div:nth-child(9) {
        animation-delay: -0.8s;
        top: 62px;
        left: 22px;
      }
      .lds-default div:nth-child(10) {
        animation-delay: -0.9s;
        top: 66px;
        left: 37px;
      }
      .lds-default div:nth-child(11) {
        animation-delay: -1s;
        top: 62px;
        left: 52px;
      }
      .lds-default div:nth-child(12) {
        animation-delay: -1.1s;
        top: 52px;
        left: 62px;
      }
      @keyframes lds-default {
        0%, 20%, 80%, 100% {
          transform: scale(1);
        }
        50% {
          transform: scale(1.5);
        }
      }
    }
  }
</style>