<template>
  <main>
    <header class="fs-4 nanum">회원가입</header>
    <div class="fs-1">
      <section><img @click="clickPersonal" src="@/assets/image/signup-personal.svg" alt="">개인회원</section>
      <section><img @click="clickCompany" src="@/assets/image/signup-company.svg" alt="">기업회원</section>
    </div>
    <footer>이미 회원이신가요? 로그인해주세요</footer>
  </main>
</template>

<script>
  import { useStore } from 'vuex'


  export default {
    name: 'UserType',
    setup() {
      const store = useStore()

      const clickPersonal = () => {
        store.commit('signup/SET_USERTYPE', 'personal')
        store.commit('signup/INCREASE_STEP')
      }
      const clickCompany = () => {
        store.commit('signup/SET_USERTYPE', 'company')
        store.commit('signup/INCREASE_STEP')
      }

      return {
        clickPersonal,
        clickCompany
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";


  main {
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: center;
    gap: 2rem;
    width: 100vw;
    height: 100vh;

    header {
      position: relative;

      &::after {
        content: '';
        position: absolute;
        bottom: -10px;
        left: 0;
        width: 100%;
        height: 0.3rem;
        background-color: $tertiary;
      }
    }
  
    div {
      display: flex;
      gap: 5rem;

      section {
        display: flex;
        flex-direction: column;
        gap: 2rem;
        align-items: center;

        img {
          height: 350px;
        }
      }
    }
  }
</style>