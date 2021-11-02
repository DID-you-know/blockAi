<template>
  <div>
    <header><img class="logo" src="@/assets/image/logo.jpg" alt="logo"></header>
    <main>
      <header class="fs-5 nanum">회원가입</header>
      <div class="fs-1">
        <section><img src="@/assets/image/signup-personal.svg" alt="">개인회원</section>
        <section><img src="@/assets/image/signup-company.svg" alt="">기업회원</section>
      </div>
      <footer>이미 회원이신가요? 로그인해주세요</footer>
    </main>
  </div>
</template>

<script>
  export default {
    name: 'Signup',
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";


  .logo {
    position: fixed;
    top: 1rem;
    left: 2rem;
  }

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
        height: 0.2rem;
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