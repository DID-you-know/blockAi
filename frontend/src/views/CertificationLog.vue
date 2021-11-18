<template>
  <div class="body certification-log">
    <Navbar/>
    <div class="image">
      <img src="@/assets/image/certification-log/certification-log.svg" alt="">
    </div>
    <div class="container">
      <h1 class="fs-3 fw-bold">인증 내역</h1>
      <div class="log-card-list">
        <template v-if="logList.length > 0">
          <div class="log-field">
            <span class="field-1">발급처</span>
            <span class="field-2">발급날짜</span>
          </div>
          <LogCard v-for="log in logList" :key="log.id" :company="log.certifiedBy" :datetime="log.certifiedAt"/>
        </template>
        <template v-else>
          <div class="log-empty">
            인증 내역이 없습니다.
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
  import Navbar from '@/components/Navbar'
  import LogCard from '@/components/LogCard'
  import users from '@/api/users'
  import { ref, computed, onMounted } from 'vue'
  import { useStore } from 'vuex'


  export default {
    name: 'CertificationLog',
    components: {
      Navbar,
      LogCard
    },
    setup() {
      const store = useStore()
      const logList = ref([])
      const userId = computed(() => store.state.users.userId)

      const getLog = async () => {
        try {
          const response = await users.getLog(userId.value)
          console.log(response.data)
          logList.value = response.data.reverse()
        } catch (error) {
          console.log(error.data)
        }
      }

      onMounted(async () => {
        await getLog()
      })

      return {
        logList
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";


  .certification-log {
    padding: 2rem 3rem 0;
    display: flex;
    gap: 1rem;
    justify-content: center;
    align-items: center;
    background-color: $light;

    .image {
      flex: 2;

      img {
        max-width: 100%;
      }
    }

    .container {
      flex: 3;
      display: flex;
      flex-direction: column;
      justify-content: flex-start;
      align-items: center;
      gap: 4rem;
      border-radius: 2rem;
      box-shadow: 2px 2px 15px 2px rgba($color: #000000, $alpha: 0.2);
      padding: 2rem 4rem;
      height: 80vh;
      background-color: $white;

      .log-card-list {
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        gap: 1rem;

        .log-field {
          position: relative;
          width: 100%;

          .field-1 {
            position: absolute;
            top: -1rem;
            left: 25%;
          }
          .field-2 {
            position: absolute;
            top: -1rem;
            left: 75%;
            transform: translateX(-50%)
          }
        }

        .log-empty {
          background-color: $light-hover;
          height: 5rem;
          width: 100%;
          display: flex;
          justify-content: center;
          align-items: center;
          border-radius: 2rem;
          font-size: 20px;
        }
      }
    }
  }
</style>