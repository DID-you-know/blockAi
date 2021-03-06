<template>
  <div class="body certification">
    <template v-if="step === 1">
      <div class="container">
        <div class="message fs-3">
          {{ faceMessage }}
        </div>

        <div v-if="faceStep === 1 && !cameraOn" class="icon-box">
          <img class="icon" src="@/assets/image/icon/faceIdIcon.png" alt="faceIdIcon">
          <WhiteButton size="2" value="얼굴 촬영 시작" @click="startCamera"/>
        </div>
        <div v-show="faceStep === 1 && cameraOn" class="icon-box">
          <video ref="video" autoplay></video>
          <WhiteButton size="2" value="얼굴 촬영" @click="captureFace"/>
        </div>

        <div v-show="faceStep === 2" class="icon-box">
          <canvas ref="canvas" width="500" height="400"></canvas>
          <canvas ref="faceCanvas" hidden></canvas>
          <div class="button-box">
            <WhiteButton size="2" value="재촬영" @click="reCapture"/>
            <WhiteButton size="2" value="촬영 완료" @click="finishCapture"/>
          </div>
        </div>

        <div v-if="faceStep === 3" class="icon-box complete">
          <img class="icon" src="@/assets/image/icon/faceIdIcon.png" alt="faceIdIcon">
          <div class="complete">얼굴 촬영 완료</div>
        </div>
      </div>

        <div class="space"></div>

      <div class="container">
        <div class="message fs-3">
          {{ voiceMessage }}
        </div>
        <div v-if="voiceStep !== 4" class="icon-box">
          <img class="icon" src="@/assets/image/icon/voiceIcon.png" alt="voiceIcon">
          <WhiteButton size="2" v-if="voiceStep === 1" value="음성 녹음 시작" @click="recordVoice"/>
          <WhiteButton size="2" v-if="voiceStep === 2" value="정지" @click="stopRecord"/>
          <template v-if="voiceStep === 3">
            <audio controls preload="auto">
              <source :src="audioSource" type="audio/wav">
            </audio>
            <div class="button-box">
              <WhiteButton size="2" value="재녹음" @click="reRecord"/>
              <WhiteButton size="2" value="녹음 완료" @click="finishRecord"/>
            </div>
          </template>
        </div>
        <div v-if="voiceStep === 4" class="icon-box complete">
          <img class="icon" src="@/assets/image/icon/voiceIcon.png" alt="voiceIcon">
          <div class="complete">음성 녹음 완료</div>
        </div>
      </div>
    </template>
    <template v-if="step === 2">
      <div class="container">
        <div class="message fs-4">
          블록체인에 저장된 얼굴, 음성과 비교하고 있습니다.<br/><br/>잠시만 기다려주세요.
        </div>
        <div class="space"></div>
        <div class="loading-box">
          <div class="lds-default"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
        </div>
      </div>
    </template>
    <template v-if="step === 3">
      <div class="container">
        <div class="message fs-5">
          신분 인증이 <span class="success fw-bold">완료</span>되었습니다.<br/><br/>{{ timer }}초 뒤에 결제화면으로 이동합니다.
        </div>
        <WhiteButton class="mt-3" size="4" value="결제하기" @click="passCertification"/>
      </div>
    </template>
    <template v-if="step === 4">
      <div class="container">
        <div class="message fs-5">
          신분 인증에 <span class="fail fw-bold">실패</span>했습니다.<br/><br/>{{ timer }}초 뒤에 창이 닫힙니다.
        </div>
        <WhiteButton class="mt-3" size="4" value="즉시 닫기" @click="closeModal"/>
      </div>
    </template>
  </div>
</template>

<script>
  import WhiteButton from '@/components/WhiteButton'
  import { onUpdated, onMounted, ref, computed } from 'vue'
  import * as blazeface from '@tensorflow-models/blazeface'
  import '@tensorflow/tfjs-backend-webgl'
  import '@tensorflow/tfjs'
  import { useStore } from 'vuex'
  import { useRouter } from 'vue-router'


  export default {
    name: 'Certification',
    components: {
      WhiteButton
    },
    setup(props, { emit }) {
      const store = useStore()
      const router = useRouter()
      const isCertificated = computed(() => store.state.certification.isCertificated)
      const step = ref(1)

      // 얼굴 촬영
      const faceMessage = ref('얼굴 촬영 시작 버튼을 눌러 얼굴 촬영을 해주세요.')
      const faceStep = ref(1)
      const cameraOn = ref(false)
      const video = ref(null)
      const canvas = ref(null)
      const faceCanvas = ref(null)
      const model = ref(null)
      const predictions = ref(null)
      const faceBase64 = ref(null)
      const captureComplete = ref(false)

      const getModel = async () => {
        model.value = Object.freeze(await blazeface.load())
        const ctx = canvas.value.getContext('2d')
        ctx.drawImage(video.value, 0, 0, 500, 400)
        predictions.value = await model.value.estimateFaces(canvas.value, false)
      }

      const startCamera = async () => {
        faceMessage.value = '카메라 연결중입니다. 잠시만 기다려주세요.'
        const constraints = {
          audio: false,
          video: {
            width: { min: 500, ideal: 500},
            height: { min: 400, ideal: 400},
            aspectRatio: { ideal: 1 }
          }
        }
        try {
          const stream = await navigator.mediaDevices.getUserMedia(constraints)
          video.value.srcObject = stream
          await getModel()
          faceMessage.value = '얼굴이 인식될 수 있게 카메라를 응시한 상태로 얼굴 촬영 버튼을 눌러주세요.'
          cameraOn.value = true
        } catch(err) {
          store.dispatch('alert/popAlert', {
            type: 'danger',
            message: '카메라 연결에 실패했습니다.'
          })
        }
      }

      const captureFace = async () => {
        const ctx = canvas.value.getContext('2d')
        ctx.drawImage(video.value, 0, 0, 500, 400)

        predictions.value = await model.value.estimateFaces(canvas.value, false)

        const width = predictions.value[0].bottomRight[0] - predictions.value[0].topLeft[0]
        const height = predictions.value[0].bottomRight[1] - predictions.value[0].topLeft[1]

        faceCanvas.value.width = width
        faceCanvas.value.height = height

        const ctx2 = faceCanvas.value.getContext('2d')
        ctx2.drawImage(
          canvas.value, 
          predictions.value[0].topLeft[0], 
          predictions.value[0].topLeft[1], 
          width, 
          height,
          0,
          0,
          width, 
          height
        )
        faceBase64.value = faceCanvas.value.toDataURL('image/jpeg', 1.0)

        ctx.beginPath()
        ctx.lineWidth = '4'
        ctx.strokeStyle = 'rgb(0, 255, 0)'
        ctx.rect(
          predictions.value[0].topLeft[0],
          predictions.value[0].topLeft[1],
          width,
          height
        )
        ctx.stroke()

        faceStep.value = 2
        faceMessage.value = '얼굴이 잘 인식되었는지 확인한 후 촬영 완료 버튼 혹은 재촬영 버튼을 눌러주세요.'
      }

      const reCapture = () => {
        faceStep.value = 1
        faceMessage.value = '얼굴이 인식될 수 있게 카메라를 응시한 상태로 얼굴 촬영 버튼을 눌러주세요.'
      }

      const stopCamera = () => {
        const tracks = video.value.srcObject.getTracks()
        tracks.forEach(track => track.stop())
        cameraOn.value = false
      }

      const finishCapture = () => {
        stopCamera()
        faceMessage.value = '얼굴 촬영이 완료되었습니다.'
        faceStep.value += 1
        captureComplete.value = true
      }


      // 음성 녹음
      const voiceStep = ref(1)
      const voiceMessage = ref('음성 녹음 시작 버튼을 눌러 음성 녹음을 해주세요.')
      const mediaRecorder = ref(null)
      const audioStream = ref(null)
      const chunks = ref([])
      const audioBlob = ref(null)
      const audioSource = ref(null)
      const recordComplete = ref(false)
      const getMIC = async () => {
        const constraints = {
          audio: true,
          video: false
        }
        try {
          audioStream.value = await navigator.mediaDevices.getUserMedia(constraints)
        } catch(err) {
          store.dispatch('alert/popAlert', {
            type: 'danger',
            message: '마이크 연결에 실패했습니다.'
          })
        }
      }
      const recordVoice = async () => {
        mediaRecorder.value = new MediaRecorder(audioStream.value)
        mediaRecorder.value.start()
        if (mediaRecorder.value.state === 'recording') {
          voiceMessage.value = '"본인인증합니다."라고 말해주세요.'
          voiceStep.value += 1
          mediaRecorder.value.ondataavailable = (e) => {
            chunks.value.push(e.data)
          }
          mediaRecorder.value.onstop = () => {
            audioBlob.value = new Blob(chunks.value, { type: 'audio/wav' })
            chunks.value = []

            // base64 encode
            var reader = new FileReader();
            reader.readAsDataURL(audioBlob.value);
            reader.onloadend = function () {
              audioBlob.value = reader.result;
            }

            audioSource.value = window.URL.createObjectURL(audioBlob.value)
          }
        }
      }
      const stopRecord = () => {
        mediaRecorder.value.stop()
        voiceMessage.value = '음성이 잘 녹음되었는지 확인한 뒤 녹음 완료 혹은 재녹음 버튼을 눌러주세요.'
        voiceStep.value += 1
      }
      const reRecord = async () => {
        mediaRecorder.value.start()
        voiceStep.value = 2
        voiceMessage.value = '"본인인증합니다."라고 말해주세요.'
      }
      const stopMIC = () => {
        const tracks = audioStream.value.getTracks()
        tracks.forEach(track => track.stop())
      }
      const finishRecord = () => {
        stopMIC()
        voiceStep.value += 1
        voiceMessage.value = '음성 녹음이 완료되었습니다.'
        recordComplete.value = true
      }

      onMounted(() => {
        getMIC()
      })

      const timer = ref(5)
      const passCertification = () => {
        emit('pass')
      }
      const closeModal = () => {
        emit('close')
      }
      onUpdated(async () => {
        if (captureComplete.value && recordComplete.value) {
          captureComplete.value = false
          recordComplete.value = false
          step.value += 1
        } else if (step.value === 2) {
          const payload = {
            face: faceBase64.value.split(',')[1],
            voice: audioBlob.value.split(',')[1],
            certifiedBy: 'SSAFY25'
          }
          await store.dispatch('certification/certification', payload)
          if (isCertificated.value) {
            step.value += 1
            store.commit('certification/RESET')
          } else {
            step.value += 2
          }
        } else if (step.value === 3 || step.value === 4) {
          if (timer.value !== 0) {
            setTimeout(() => {
              timer.value -= 1
            }, 1000)
          } else {
            if (step.value === 3){
              setTimeout(() => {
                passCertification()
              }, 500)
            } else {
              setTimeout(() => {
                closeModal()
              }, 500)
            }
          }
        }
      })

      return {
        router,
        step,
        faceMessage,
        faceStep,
        cameraOn,
        video,
        startCamera,
        captureFace,
        canvas,
        faceCanvas,
        reCapture,
        finishCapture,
        voiceStep,
        voiceMessage,
        audioSource,
        recordVoice,
        stopRecord,
        reRecord,
        finishRecord,
        timer,
        passCertification,
        closeModal
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";
  @import "@/assets/style/common.scss";


  .certification {
    background: rgb(109,143,201);
    background: linear-gradient(180deg, rgba(109,143,201,1) 0%, rgba(113,121,200,1) 40%, rgba(114,117,200,1) 60%, rgba(176,162,208,1) 100%);
    position: relative;
    gap: 2rem;
    border-radius: inherit;
    padding-bottom: inherit;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: row;

    @include kiosk {
      flex-direction: column;
    }

    .container {
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      gap: 4rem;

      .mt-3 {
        margin-top: 3vh;
      }
    }

    .progressbar {
      margin-top: 8vh;
    }

    .message {
      color: white;
      text-align: center;

      .success {
        color: $success;
      }

      .fail {
        color: $danger;
      }
    }

    .icon-box {
      border: 2px solid;
      color: $white;
      border-radius: 2rem;
      width: 550px;
      height: 550px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      gap: 2rem;

      .icon {
        width: auto;
        height: 300px;
      }

      .complete {
        color: $white;
        background-color: $secondary;
        padding: 1vh 1.5vh;
        border-radius: 1.5vh;
        font-size: 1.5vh;
      }
    }

    .complete {
      background-color: $light-hover;
    }
    
    .button-box {
      display: flex;
      gap: 2rem;
    }

    .space {
      margin: 1vh;
    }

    .loading-box {
      height: 30%;

      .lds-default {
        display: inline-block;
        position: relative;
        width: 19.25vh;
        height: 19.25vh;
      }
      .lds-default div {
        position: absolute;
        width: 1vh;
        height: 1vh;
        background: $white;
        border-radius: 50%;
        animation: lds-default 1.2s linear infinite;
      }
      .lds-default div:nth-child(1) {
        animation-delay: 0s;
        top: 9.25vh;
        left: 16.5vh;
      }
      .lds-default div:nth-child(2) {
        animation-delay: -0.1s;
        top: 5.5vh;
        left: 15.5vh;
      }
      .lds-default div:nth-child(3) {
        animation-delay: -0.2s;
        top: 2.75vh;
        left: 13vh;
      }
      .lds-default div:nth-child(4) {
        animation-delay: -0.3s;
        top: 1.75vh;
        left: 9.25vh;
      }
      .lds-default div:nth-child(5) {
        animation-delay: -0.4s;
        top: 2.75vh;
        left: 5.5vh;
      }
      .lds-default div:nth-child(6) {
        animation-delay: -0.5s;
        top: 5.5vh;
        left: 2.75vh;
      }
      .lds-default div:nth-child(7) {
        animation-delay: -0.6s;
        top: 9.25vh;
        left: 1.75vh;
      }
      .lds-default div:nth-child(8) {
        animation-delay: -0.7s;
        top: 13vh;
        left: 2.75vh;
      }
      .lds-default div:nth-child(9) {
        animation-delay: -0.8s;
        top: 15.5vh;
        left: 5.5vh;
      }
      .lds-default div:nth-child(10) {
        animation-delay: -0.9s;
        top: 16.5vh;
        left: 9.25vh;
      }
      .lds-default div:nth-child(11) {
        animation-delay: -1s;
        top: 15.5vh;
        left: 13vh;
      }
      .lds-default div:nth-child(12) {
        animation-delay: -1.1s;
        top: 13vh;
        left: 15.5vh;
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