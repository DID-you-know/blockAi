<template>
  <div>
    <!-- 발행 후 -->
    <div v-if="isIssued" class="card-container" @click="turnCard">
      <div class="card front" :class="{ 'front-turn': isFront }">
        <div class="card-title fs-2 fw-light">
          신원증명서
        </div>
        <div class="card-body">
          <div class="card-content">이미지</div>
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
              <img class="icon" src="@/assets/image/icon/faceIdIcon.png" alt="">
            </div>
            <span class="fs-1 fw-light tag">
              얼굴 조회
            </span>
          </div>
          <div class="card-content">
            <div class="box">
              <img class="icon" src="@/assets/image/icon/voiceIcon.png" alt="">
            </div>
            <span class="fs-1 fw-light tag">
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
          <div class="card-content">이미지</div>
          <div class="card-content fw-light">
            <span class="label">이름</span>
            <span class="value">홍길동</span>
            <span class="label">발급일</span>
            <span class="value">2021.11.05</span>
          </div>
        </div>
      </div>
      <WhiteButton value="신원증명발급"/>
    </div>
  </div>
</template>

<script>
  import { ref, computed } from 'vue'
  import { useStore } from 'vuex'
  import WhiteButton from '@/components/WhiteButton'


  export default {
    name: 'DIDCard',
    components: {
      WhiteButton
    },
    setup() {
      const isFront = ref(false)
      const turnCard = () => {
        isFront.value = !isFront.value
      }

      const store = useStore()
      const isIssued = computed(() => store.state.user.isIssued)

      return {
        isFront,
        turnCard,
        isIssued
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

        .card-content {
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
            padding: 1.5rem 2.5rem;
            border: 1px solid;
            color: $white;
            border-radius: 1rem;

            .icon {
              width: 100px;
              height: 100px;
            }
          }

          .tag {
            color: $white;
            text-align: center;
            margin-top: 1rem;
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
  }
</style>