<template>
  <nav class="navbar">
    <div class="container">
      <Logo/>
    </div>
    <div class="container" :class="{ 'hide-content': $props.hideContent }">
      <router-link :to="{ name: 'status' }" class="navbar-item fs-1">신원증명발급</router-link>
      <router-link :to="{ name: 'login' }" class="navbar-item fs-1">로그인</router-link>
      <router-link :to="{ name: 'signup' }" class="navbar-item fs-1">회원가입</router-link>
    </div>
  </nav>
</template>

<script>
  import Logo from '@/components/Logo'


  export default {
    name: 'Navbar',
    components: {
      Logo
    },
    props: {
      hideContent: {
        type: Boolean,
        default: false
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";

  .navbar {
    display: flex;
    justify-content: space-between;
    position: fixed;
    top: 0;
    width: 100%;
    z-index: 1;
    transform: translateZ(0);

    .container {
      display: flex;
      justify-content: center;
      margin: 1rem 2rem;

      .navbar-item {
        padding: 1rem 1rem 1rem 1rem;
        position: relative;

        &:hover {
          cursor: pointer;
        }
        // underline
        &::after {
          content: '';
          position: absolute;
          bottom: 10px;
          left: 15%;
          width: 70%;
          height: 0.1rem;
          background-color: $primary;
          transition: transform 300ms;
          transform: scale(0);
          transform-origin: center;
        }

        &:hover:after {
          transform: scale(1);
        }
      }
    }

    .hide-content{
      display: none;
    }
  }
</style>
