<template>
  <div class="body issue">
    <Progressbar :step="step" :content="['얼굴 등록', '음성 등록', '블록체인 생성', '등록 완료']" class="progressbar"/>
    <template v-if="step === 1">
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
    <template v-if="step === 2">
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
    <template v-if="step === 3">
      <div class="message fs-2">
        블록체인에 저장중입니다. 잠시만 기다려주세요.
      </div>
      <div class="loading-box">
        <Spinner/>
      </div>
    </template>
    <template v-if="step === 4">
      <div class="message fs-2">
        신분증명발급이 완료되었습니다.<br/>{{ timer }}초 뒤에 신분증명조회 페이지로 이동합니다.
      </div>
      <WhiteButton value="신분증명 조회하기" @click="router.push({ name: 'status' })"/>
    </template>
    <template v-if="step === 5">
      <div class="message fs-2">
        신분증명발급에 실패했습니다. 다시 시도해 주세요.<br/>{{ timer }}초 뒤에 신분증명조회 페이지로 돌아갑니다.
      </div>
      <WhiteButton value="신분증명 조회하기" @click="router.push({ name: 'status' })"/>
    </template>
    <canvas ref="faceCanvas" hidden></canvas>
  </div>
</template>

<script>
  import Progressbar from '@/components/Progressbar'
  import WhiteButton from '@/components/WhiteButton'
  import Spinner from '@/components/Spinner'
  import { ref, onMounted, onUpdated, computed } from 'vue'
  import { useRouter } from 'vue-router'
  import * as blazeface from '@tensorflow-models/blazeface'
  import '@tensorflow/tfjs-backend-webgl'
  import '@tensorflow/tfjs'
  import AWS from 'aws-sdk'
  import { useStore } from 'vuex'
  import { v4 as uuidv4 } from 'uuid'


  export default {
    name: 'Issue',
    components: {
      Progressbar,
      WhiteButton,
      Spinner
    },
    setup() {
      const store = useStore()
      const userId = computed(() => store.state.users.userId)
      const isIssued = computed(() => store.state.users.isIssued)

      const router = useRouter()
      const step = ref(1)
      const nextStep = () => {
        step.value += 1
      }
      
      // 얼굴 등록
      const faceStep = ref(1)
      const faceMessage = ref('카메라 연결 중입니다. 잠시만 기다려주세요.')
      const model = ref(null)
      const predictions = ref(null)
      const canvas = ref(null)
      const faceCanvas = ref(null)
      const faceBase64 = ref(null)
      const getModel = async () => {
        model.value = Object.freeze(await blazeface.load())
        const ctx = canvas.value.getContext('2d')
        ctx.drawImage(video.value, 0, 0, 640, 480)
        predictions.value = await model.value.estimateFaces(canvas.value, false)
      }
      const captureFace = async () => {
        const ctx = canvas.value.getContext('2d')
        ctx.drawImage(video.value, 0, 0, 640, 480)

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
      const cameraOn = ref(false)
      const video = ref(null)
      const stopCamera = () => {
        const tracks = video.value.srcObject.getTracks()
        tracks.forEach(track => track.stop())
        cameraOn.value = false
      }
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
          faceMessage.value = '얼굴이 인식될 수 있게 카메라를 응시한 상태로 얼굴 촬영 버튼을 눌러주세요.'
        } catch(err) {
          store.dispatch('alert/popAlert', {
            type: 'danger',
            message: '카메라 연결에 실패했습니다.'
          })
        }
      }
      const reCapture = () => {
        faceStep.value = 1
        faceMessage.value = '얼굴이 인식될 수 있게 카메라를 응시한 상태로 얼굴 촬영 버튼을 눌러주세요.'
      }
      const finishCapture = () => {
        stopCamera()
        nextStep()
        reCapture()
      }

      onMounted(async () => {
        if (step.value === 1 && cameraOn.value === false) {
          await getModel()
          await getCamera()
        }
      })

      // 음성 등록
      const voiceStep = ref(1)
      const voiceMessage = ref('그림 아래에 있는 녹음 시작 버튼을 눌러 음성을 녹음해주세요.')
      const mediaRecorder = ref(null)
      const audioStream = ref(null)
      const chunks = ref([])
      const audioBlob = ref(null)
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
            audioSource.value = window.URL.createObjectURL(audioBlob.value)
          }
        }
      }
      const audioSource = ref(null)
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
        step.value += 1
        voiceStep.value = 1
      }

      // 생체데이터 저장
      const facePath = ref(null)
      const voicePath = ref(null)
      // s3 저장 및 axios 요청
      const s3Upload = async () => {
        // S3 설정
        AWS.config.update({
          accessKeyId: process.env.VUE_APP_AWS_ACCESS_KEY,
          secretKey: process.env.VUE_APP_AWS_SECRET_ACCESS_KEY,
          region: process.env.VUE_APP_BUCKETREGION,
          credentials: new AWS.CognitoIdentityCredentials({
            IdentityPoolId: process.env.VUE_APP_IDENTITYPOOLID
          })
        })

        // canvas to blob
        const base64 = faceCanvas.value.toDataURL('image/jpeg', 1.0)
        const base64Response = await fetch(base64)
        const faceBlob = await base64Response.blob()

        // 이미지 업로드
        const faceUpload = new AWS.S3.ManagedUpload({
          params: {
            Bucket: process.env.VUE_APP_ALBUMBUCKETNAME,
            Key: uuidv4() + '-' + userId.value + '.jpg',
            Body: faceBlob
          }
        })
        try {
          const facePromise = await faceUpload.promise()
          facePath.value = facePromise.Location
        } catch (error) {
          store.dispatch('alert/popAlert', {
            type: 'danger',
            message: '얼굴등록에 실패했습니다.'
          })
          step.value = 5
        }

        // 음성 업로드
        const voiceUpload = new AWS.S3.ManagedUpload({
          params: {
            Bucket: process.env.VUE_APP_ALBUMBUCKETNAME,
            Key: uuidv4() + '-' + userId.value + '.wav',
            Body: audioBlob.value
          }
        })
        try {
          const voicePromise = await voiceUpload.promise()
          voicePath.value = voicePromise.Location
        } catch (error) {
          store.dispatch('alert/popAlert', {
            type: 'danger',
            message: '음성등록에 실패했습니다.'
          })
          step.value = 5
        }

        // axios
        await store.dispatch('users/didIssue', { userId: userId.value, didData: { facePath: facePath.value, voiceId: voicePath.value } })
        if (isIssued.value) {
          step.value += 1
        } else {
          step.value += 2
        }
      }

      const timer = ref(5)
      onUpdated(async () => {
        if (step.value === 2 && audioStream.value === null) {
          getMIC()
        } else if (step.value === 3) {
          await s3Upload()
        } else if (step.value === 4 || step.value === 5) {
          if (timer.value !== 0) {
            setTimeout(() => {
              timer.value -= 1
            }, 1000)
          } else {
            setTimeout(() => {
              router.push({ name: 'status' })
            }, 500)
          }
        }
      })

      return {
        router,
        step,
        faceCanvas,
        faceStep,
        faceMessage,
        getCamera,
        reCapture,
        finishCapture,
        video,
        model,
        canvas,
        cameraOn,
        captureFace,
        voiceMessage,
        recordVoice,
        voiceStep,
        stopRecord,
        audioSource,
        audioBlob,
        reRecord,
        finishRecord,
        timer
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
    overflow: auto;

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
    }
  }
</style>