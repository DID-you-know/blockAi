<template>
  <div>
    <!-- 발행 후 -->
    <div v-if="issuedDate" class="card-container" @click="turnCard">
      <div class="card front" :class="{ 'front-turn': isFront }">
        <div class="card-title">
          신원증명서
        </div>
        <div class="card-body">
          <div class="card-content"><img class="stamp" src="@/assets/image/stamp.png" alt=""></div>
          <div class="card-content">
            <span class="label">이름</span>
            <span class="value">{{ name }}</span>
            <span class="label">발급일</span>
            <span class="value">{{ issuedAt }}</span>
          </div>
        </div>
      </div>

      <div class="card back" :class="{ 'back-turn': isFront }">
        <div class="card-title">
          신원증명서
        </div>
        <div class="card-body">
          <div class="card-content">
            <div class="box">
              <img v-if="!isFaceOn" class="icon" src="@/assets/image/icon/faceIdIcon.png" alt="faceId">
              <Spinner v-if="isFaceOn && loading"/>
              <img v-if="isFaceOn && !loading" class="image" :src="face" alt="face">
            </div>
            <WhiteButton class="tag" @click.stop="faceOn" value="얼굴 조회"/>
          </div>
          <div class="card-content">
            <div class="box">
              <img class="icon" src="@/assets/image/icon/voiceIcon.png" alt="voice">
              <audio autoplay hidden ref="audio">
                <source :src="voice" type="audio/wav">
              </audio>
            </div>
            <WhiteButton class="tag" @click.stop="voiceOn" value="음성 조회"/>
          </div>
        </div>
      </div>
    </div>

    <!-- 발행 전 -->
    <div v-else class="card-container">
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
  import Spinner from '@/components/Spinner'
  import users from '@/api/users'


  export default {
    name: 'DIDCard',
    components: {
      WhiteButton,
      Spinner
    },
    props: {
      name: String,
      issuedAt: String
    },
    setup() {
      const store = useStore()
      const issuedDate = computed(() => store.state.users.issuedDate)

      const isFront = ref(false)
      const turnCard = () => {
        isFront.value = !isFront.value
      }

      const router = useRouter()
      const pushIssue = () => router.push({ name: 'issue' })

      const face = ref(null)
      const isFaceOn = ref(false)
      const loading = ref(false)
      const faceOn = async () => {
        loading.value = true
        isFaceOn.value = !isFaceOn.value
        if (face.value === null) {
          try {
            const response = await users.getFace(store.state.users.userId)
            face.value = response.data.encodeFaceFile
          } catch (error) {
            store.dispatch('alert/popAlert', {
              type: 'danger',
              message: '얼굴조회에 실패했습니다.'
            })
          }
        }
        loading.value = false
      }

      const voice = ref(null)
      const isVoiceOn = ref(false)
      const audio = ref(null)
      const voiceOn = async () => {
        isVoiceOn.value = !isVoiceOn.value
        if (voice.value === null) {
          try {
            const response = await users.getVoice(store.state.users.userId)
            voice.value = response.data.encodeVoiceFile
          } catch (error) {
            store.dispatch('alert/popAlert', {
              type: 'danger',
              message: '음성조회에 실패했습니다.'
            })
          }
        }
        audio.value.load()
      }

      return {
        isFront,
        turnCard,
        issuedDate,
        pushIssue,
        isFaceOn,
        loading,
        faceOn,
        isVoiceOn,
        voiceOn,
        face,
        voice,
        audio
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import '@/assets/style/color.scss';


  $height: 60vh;

  .blur {
    filter: blur(10px);
  }

  .card-container {
    perspective: 150vh;
    display: inline-block;
    position: relative;
    cursor: pointer;

    .card {
      border-radius: 3vh;
      width: $height * 1.62;
      height: $height;
      display: flex;
      flex-direction: column;
      padding: 3vh 6vh;
      background: rgb(93,78,136);
      background: linear-gradient(0deg, rgba(93,78,136,1) 0%, rgba(114,117,200,1) 66%, rgba(109,143,201,1) 100%);
      backface-visibility: hidden;
      transition: 1.5s;
      box-shadow: 1vh 1vh 2vh 0 rgba($color: #000000, $alpha: 0.2);

      .card-title {
        margin-left: 6vh;
        color: $white;
        font-size: 4.5vh
      }

      .card-body {
        display: flex;
        justify-content: space-around;
        height: 100%;
        gap: 6vh;

        .card-content {
          flex: 1;
          display: flex;
          flex-direction: column;
          justify-content: center;

          .label {
            margin: 3vh 0 1vh 1vh;
            color: rgb(189, 189, 189);
            font-size: 2vh;
          }

          .value {
            font-size: 3.5vh;
            color: $white;
            margin-bottom: 6vh;
            letter-spacing: 0.1vh;
          }

          .box {
            margin-top: 3vh;
            padding: 4.5vh 7.5vh;
            border: 1px solid;
            color: $white;
            border-radius: 3vh;
            height: 29vh;
            display: flex;
            justify-content: center;
            align-items: center;

            .icon {
              width: 20vh;
              height: 20vh;
            }

            .image {
              max-width: 20vh;
              height: auto;
            }
          }

          .tag {
            text-align: center;
            margin-top: 2vh;
            padding: 2vh;
            border-radius: 3vh;
            font-size: 2vh;
          }

          .stamp {
            opacity: 0.6;
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