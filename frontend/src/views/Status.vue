<template>
  <div class="status body">
    <Navbar/>
    <DIDCard :name="name" :issuedAt="issuedDate" class="did-card"/>
    <div v-if="issuedDate" class="background">
      <span class="message">위 사람은 BlockAi가 인증된 신원임을 보장합니다.</span>
    </div>
  </div>
</template>

<script>
  import Navbar from '@/components/Navbar'
  import DIDCard from '@/components/DIDCard'
  import { useStore } from 'vuex'
  import { computed } from 'vue'


  export default {
    name: 'Status',
    components: {
      Navbar,
      DIDCard
    },
    setup() {
      const store = useStore()
      const name = computed(() => store.state.users.name)
      const issuedDate = computed(() => {
        const issuedAt = store.state.users.issuedDate
        if (issuedAt) {
          return issuedAt.slice(0, 10).replace(/-/g, '.')
        } else {
          return null
        }
      })

      return {
        name,
        issuedDate
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";


  .status {
    padding-top: 5rem;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    overflow: hidden;
    position: relative;
    background-color: $light;

    .did-card {
      z-index: 2;
    }

    $height: 70vh;

    .background {
      z-index: 1;
      position: absolute;
      background-color: $white;
      width: $height * 1.62;
      height: $height;
      box-shadow: 1vh 1vh 3vh 1vh rgba($color: #000000, $alpha: 0.15);
      border-radius: 3vh;
      transform: translateY(50%);

      .message {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translateX(-50%);
        font-size: 2.5vh;
      }
    }
  }
</style>