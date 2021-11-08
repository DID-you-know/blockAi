<template>
  <div class="body issue">
    <Progressbar :step="step" class="progressbar"/>
    <template v-if="step === 1">
      <div class="message fs-2">
        얼굴이 인식될 수 있게 얼굴을 화면에 맞춰주세요.<br>
        얼굴인식이 완료되면 다음단계로 넘어갑니다.
      </div>
      <div v-if="!cameraOn" class="icon-box">
        <img width="400" height="400" src="@/assets/image/icon/faceIdIcon.png" alt="faceIdIcon">
      </div>
      <video v-show="cameraOn" ref="video" autoplay></video>
    </template>
    <template v-if="step === 2">
      <div class="message fs-2">
        {{ voiceMessage }}
      </div>
      <div class="icon-box">
        <img width="400" height="400" src="@/assets/image/icon/voiceIcon.png" alt="voiceIcon">
        <WhiteButton v-if="audioStep === 1" value="녹음 시작" @click="recordVoice"/>
        <WhiteButton v-if="audioStep === 2" value="정지" @click="stopRecord"/>
        <template v-if="audioStep === 3">
          <audio controls preload="none">
            <source :src="audioSource" type="audio/mpeg">
          </audio>
          <div class="button-box">
            <WhiteButton value="재녹음" @click="reRecord"/>
            <WhiteButton value="녹음 완료" @click="finishRecord"/>
          </div>
        </template>
      </div>
    </template>
    <template v-if="step === 3">
      <div class="message fs-2">
        블록체인에 저장중입니다. 잠시만 기다려주세요.
      </div>
      <div class="loading-box">
        <div class="lds-default"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
      </div>
    </template>
    <template v-if="step === 4">
      <div class="message fs-2">
        신분증명발급이 완료되었습니다.
      </div>
      <WhiteButton value="신분증명 조회하기" @click="router.push({ name: 'home' })"/>
    </template>
  </div>
</template>

<script>
  import Progressbar from '@/components/Progressbar'
  import WhiteButton from '@/components/WhiteButton'
  import { ref, onMounted } from 'vue'
  import { useRouter } from 'vue-router'


  export default {
    name: 'Issue',
    components: {
      Progressbar,
      WhiteButton
    },
    setup() {
      const router = useRouter()
      const step = ref(2)
      const nextStep = () => {
        step.value += 1
      }
      
      // 얼굴 등록
      const cameraOn = ref(false)
      const video = ref(null)
      const getCamera = async () => {
        const constraints = {
          audio: false,
          video: {
            width: { min: 640, ideal: 640},
            height: { min: 480, ideal: 480},
            aspectRatio: { ideal: 1.7777777778 }
          }
        }
        try {
          const stream = await navigator.mediaDevices.getUserMedia(constraints)
          video.value.srcObject = stream
          cameraOn.value = true
        } catch(err) {
          console.log(err)
        }
      }
      const stopCamera = () => {
        const tracks = video.value.srcObject.getTracks()
        tracks.forEach(track => track.stop())
      }
      const finishCaptureFace = () => {
        stopCamera()
        nextStep()
      }

      // 음성 등록
      const audioStep = ref(1)
      const voiceMessage = ref('그림 아래에 있는 녹음 시작 버튼을 눌러 음성을 녹음해주세요.')
      const mediaRecorder = ref(null)
      const audioStream = ref(null)
      const chunks = ref([])
      const audioBlob = ref(null)
      const recordVoice = async () => {
        const constraints = {
          audio: true,
          video: false
        }
        try {
          audioStream.value = await navigator.mediaDevices.getUserMedia(constraints)
          mediaRecorder.value = new MediaRecorder(audioStream.value)
          mediaRecorder.value.start()
          if (mediaRecorder.value.state === 'recording') {
            voiceMessage.value = '~~~라고 따라 말해주세요.'
            audioStep.value += 1
            mediaRecorder.value.ondataavailable = (e) => {
              chunks.value.push(e.data)
            }
            mediaRecorder.value.onstop = () => {
              audioBlob.value = new Blob(chunks.value, { type: 'audio/mpeg' })
              chunks.value = []
              audioSource.value = window.URL.createObjectURL(audioBlob.value)
            }
          }
        } catch(err) {
          console.log(err)
        }
      }
      const audioSource = ref(null)
      const stopRecord = () => {
        mediaRecorder.value.stop()
        voiceMessage.value = '음성이 잘 녹음되었는지 확인한 뒤 녹음 완료 혹은 재녹음 버튼을 눌러주세요.'
        audioStep.value += 1
      }
      const reRecord = async () => {
        mediaRecorder.value.start()
        audioStep.value = 2
      }
      const stopMIC = () => {
        const tracks = audioStream.value.getTracks()
        tracks.forEach(track => track.stop())
      }
      const finishRecord = () => {
        stopMIC()
        step.value += 1
        audioStep.value = 1
      }

      onMounted(() => {
        if (step.value === 1) {
          getCamera()
        }
      })

      return {
        router,
        step,
        finishCaptureFace,
        getCamera,
        video,
        cameraOn,
        voiceMessage,
        recordVoice,
        audioStep,
        stopRecord,
        audioSource,
        audioBlob,
        reRecord,
        finishRecord
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";

  .issue {
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

      .button-box {
        display: flex;
        gap: 2rem;
      }
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