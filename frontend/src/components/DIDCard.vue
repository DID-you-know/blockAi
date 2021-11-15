<template>
  <div>
    <!-- 발행 후 -->
    <div v-if="isIssued" class="card-container" @click="turnCard">
      <div class="card front" :class="{ 'front-turn': isFront }">
        <div class="card-title fs-2 fw-light">
          신원증명서
        </div>
        <div class="card-body">
          <div class="card-content"><img class="stamp" src="@/assets/image/stamp.png" alt=""></div>
          <div class="card-content fw-light">
            <span class="label">이름</span>
            <span class="value">홍길동</span>
            <span class="label">발급일</span>
            <span class="value">2021.11.05</span>
          </div>
        </div>
      </div>

      <div class="card back" :class="{ 'back-turn': isFront }">
        <div class="card-title fs-2 fw-light">
          신원증명서
        </div>
        <div class="card-body">
          <div class="card-content">
            <div class="box">
              <img v-if="!isFaceOn" class="icon" src="@/assets/image/icon/faceIdIcon.png" alt="faceId">
              <img v-if="isFaceOn" class="image" :src="face.value" alt="face">
            </div>
            <span class="fs-1 fw-light tag" @click.stop="faceOn">
              얼굴 조회
            </span>
          </div>
          <div class="card-content">
            <div class="box">
              <img v-if="!isVoiceOn" class="icon" src="@/assets/image/icon/voiceIcon.png" alt="voice">
              <audio hidden>
                <source :src="voice.value" type="audio/wav">
              </audio>
            </div>
            <span class="fs-1 fw-light tag" @click.stop="voiceOn">
              음성 조회
            </span>
          </div>
        </div>
      </div>
    </div>

    <!-- 발행 전 -->
    <div v-if="!isIssued" class="card-container">
      <div class="card">
        <div class="card-title fs-2 fw-light blur">
          신원증명서
        </div>
        <div class="card-body blur">
          <div class="card-content"><img class="stamp" src="@/assets/image/stamp.png" alt=""></div>
          <div class="card-content fw-light">
            <span class="label">이름</span>
            <span class="value">홍길동</span>
            <span class="label">발급일</span>
            <span class="value">2021.11.05</span>
          </div>
        </div>
      </div>
      <WhiteButton class="issue-button" value="신원증명발급" @click="pushIssue"/>
    </div>
  </div>
</template>

<script>
  import { ref, computed } from 'vue'
  import { useStore } from 'vuex'
  import { useRouter } from 'vue-router'
  import WhiteButton from '@/components/WhiteButton'
  import users from '@/api/users'



  export default {
    name: 'DIDCard',
    components: {
      WhiteButton
    },
    setup() {
      const store = useStore()

      const isFront = ref(false)
      const turnCard = () => {
        isFront.value = !isFront.value
      }

      const isIssued = computed(() => store.getters.users.isIssued)

      const router = useRouter()
      const pushIssue = () => router.push({ name: 'issue' })

      const face = ref(null)
      const isFaceOn = ref(false)
      const faceOn = async () => {
        isFaceOn.value = !isFaceOn.value
        if (face.value === null) {
          console.log('axios')
          try {
            const response = await users.getFace(store.state.users.userId)
            console.log(response.data)
            face.value = response.data.face
          } catch (error) {
            console.log(error)
          }
        }
      }

      const voice = ref(null)
      const isVoiceOn = ref(false)
      const voiceOn = async () => {
        isVoiceOn.value = !isVoiceOn.value
        if (voice.value === null) {
          console.log('axios')
          try {
            const response = await users.getVoice(store.state.users.userId)
            console.log(response.data)
            voice.value = response.data.voice
          } catch (error) {
            console.log(error)
          }
        }
      }

      return {
        isFront,
        turnCard,
        isIssued,
        pushIssue,
        isFaceOn,
        faceOn,
        isVoiceOn,
        voiceOn
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import '@/assets/style/color.scss';


  $height: 20rem;

  .blur {
    filter: blur(5px);
  }

  .card-container {
    perspective: 1000px;
    display: inline-block;
    position: relative;

    .card {
      border-radius: 1rem;
      width: $height * 1.62;
      height: $height;
      display: flex;
      flex-direction: column;
      padding: 1rem 2rem;
      background: rgb(93,78,136);
      background: linear-gradient(0deg, rgba(93,78,136,1) 0%, rgba(114,117,200,1) 66%, rgba(109,143,201,1) 100%);
      backface-visibility: hidden;
      transition: 1.5s;

      .card-title {
        margin-left: 2rem;
        color: $white;
      }

      .card-body {
        display: flex;
        justify-content: space-around;
        height: 100%;
        gap: 2rem;

        .card-content {
          flex: 1;
          display: flex;
          flex-direction: column;
          justify-content: center;

          .label {
            margin: 1rem 0 5px 5px;
            color: rgb(189, 189, 189);
          }

          .value {
            font-size: 20px;
            color: $white;
            margin-bottom: 2rem;
          }

          .box {
            margin-top: 1rem;
            padding: 1.5rem 2.5rem;
            border: 1px solid;
            color: $white;
            border-radius: 1rem;
            text-align: center;

            .icon {
              width: 100px;
              height: 100px;
            }

            .image {
              width: 100px;
              height: 100px;
            }
          }

          .tag {
            color: $white;
            text-align: center;
            margin-top: 10px;
            padding: 10px;
            background-color: $primary;
            border-radius: 1rem;
            cursor: default;

            &:hover {
              background-color: $primary-hover;
            }
          }

          .stamp {
            opacity: 0.3;
            width: 100%;
          }
        }
      }
    }

    .front {
      position: absolute;
      transform: rotateY(0deg);
    }

    .front-turn {
      transform: rotateY(-180deg);
    }

    .back {
      transform: rotateY(180deg);
    }

    .back-turn {
      transform: rotateY(0deg);
    }

    .issue-button {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%,-50%);
    }
  }
</style>