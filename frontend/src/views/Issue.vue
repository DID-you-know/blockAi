<template>
  <div class="body issue">
    <Progressbar :step="step" class="progressbar"/>
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
        <div class="lds-default"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
      </div>
    </template>
    <template v-if="step === 4">
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
  import { ref, onMounted, onUpdated } from 'vue'
  import { useRouter } from 'vue-router'
  import * as blazeface from '@tensorflow-models/blazeface'
  import '@tensorflow/tfjs-backend-webgl'
  import '@tensorflow/tfjs'
  import AWS from 'aws-sdk'


  export default {
    name: 'Issue',
    components: {
      Progressbar,
      WhiteButton
    },
    setup() {
      const router = useRouter()
      const step = ref(1)
      const nextStep = () => {
        step.value += 1
      }
      
      // 얼굴 등록
      const faceStep = ref(1)
      const faceMessage = ref('얼굴이 인식될 수 있게 카메라를 응시한 상태로 얼굴 촬영 버튼을 눌러주세요.')
      const model = ref(null)
      const predictions = ref(null)
      const canvas = ref(null)
      const faceCanvas = ref(null)
      const getModel = async () => {
        model.value = Object.freeze(await blazeface.load())
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
        } catch(err) {
          console.log(err)
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
          console.log(err)
        }
      }
      const recordVoice = async () => {
        mediaRecorder.value = new MediaRecorder(audioStream.value)
        mediaRecorder.value.start()
        if (mediaRecorder.value.state === 'recording') {
          voiceMessage.value = '~~~라고 따라 말해주세요.'
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
      const audioSource = ref(null)
      const stopRecord = () => {
        mediaRecorder.value.stop()
        voiceMessage.value = '음성이 잘 녹음되었는지 확인한 뒤 녹음 완료 혹은 재녹음 버튼을 눌러주세요.'
        voiceStep.value += 1
      }
      const reRecord = async () => {
        mediaRecorder.value.start()
        voiceStep.value = 2
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
      const faceImageUpload = async () => {
        // S3 설정
        AWS.config.update({
          accessKeyId: process.env.VUE_APP_AWS_ACCESS_KEY,
          secretKey: process.env.VUE_APP_AWS_SECRET_ACCESS_KEY,
          region: process.env.VUE_APP_BUCKETREGION,
          credentials: new AWS.CognitoIdentityCredentials({
            IdentityPoolId: process.env.VUE_APP_IDENTITYPOOLID
          })
        })
        const s3 = new AWS.S3({
          apiVersion: "2006-03-01",
          params: { Bucket: process.env.VUE_APP_ALBUMBUCKETNAME }
        })

        // canvas to blob
        const base64 = faceCanvas.value.toDataURL('image/jpeg', 1.0)
        const base64Response = await fetch(base64)
        const blob = await base64Response.blob()

        console.log(s3)
        // 이미지 업로드
        const upload = new AWS.S3.ManagedUpload({
          params: {
            Bucket: process.env.VUE_APP_ALBUMBUCKETNAME,
            Key: '얼굴사진테스트.jpg',
            Body: blob
          }
        })
        const promise = upload.promise()
        promise.then((data) => {
          console.log('success', data)
        }, (err) => {
          console.log('error', err)
        })
      }


      onMounted(() => {
        if (step.value === 1 && cameraOn.value === false) {
          getModel()
          getCamera()
        }
      })

      onUpdated(() => {
        if (step.value === 2 && audioStream.value === null) {
          getMIC()
        }
        if (step.value === 3) {
          faceImageUpload()
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