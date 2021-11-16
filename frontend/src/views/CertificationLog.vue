<template>
  <div class="body certification-log">
    <Navbar/>
    <div class="image">
      <img src="@/assets/image/certification-log/certification-log.svg" alt="">
    </div>
    <div class="container">
      <h1 class="fs-3 fw-bold">인증 내역</h1>
      <div class="log-card-list">
        <div class="log-field">
          <span class="field-1">발급처</span>
          <span class="field-2">발급날짜</span>
        </div>
        <LogCard/>
      </div>
    </div>
  </div>
</template>

<script>
  import Navbar from '@/components/Navbar'
  import LogCard from '@/components/LogCard'
  import users from '@/api/users'
  import { ref, computed } from 'vue'
  import { useStore } from 'vuex'


  export default {
    name: 'CertificationLog',
    components: {
      Navbar,
      LogCard
    },
    async setup() {
      const store = useStore()
      const logList = ref([])
      const userId = computed(() => store.state.users.userId)
      try {
        const response = await users.getLog(userId)
        console.log(response.data)
        logList.value = response.data
      } catch (error) {
        console.log(error.data)
      }
    }
  }
</script>

<style lang="scss" scoped>
  .certification-log {
    padding: 2rem 3rem 0;
    display: flex;
    gap: 1rem;
    justify-content: center;
    align-items: center;

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
      box-shadow: 2px 2px 10px 2px rgba($color: #000000, $alpha: 0.2);
      padding: 2rem 4rem;
      height: 80vh;

      .log-card-list {
        height: 100%;
        display: flex;
        flex-direction: column;
        gap: 1rem;
        position: relative;

        .field-1 {
          position: absolute;
          top: -1rem;
          left: 35%;
          transform: translateX(-50%)
        }
        .field-2 {
          position: absolute;
          top: -1rem;
          left: 75%;
          transform: translateX(-50%)
        }
      }
    }
  }
</style>