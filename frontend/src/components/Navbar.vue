<template>
  <nav class="navbar">
    <div class="container">
      <div @click="pushHome">
        <img class="logo" src="@/assets/image/logo.jpg" alt="logo">
      </div>
    </div>
    <div class="container" :class="{ 'hide-content': $props.hideContent }">
      <span class="navbar-item fs-1">로그인</span>
      <span @click="pushSignup" class="navbar-item fs-1">회원가입</span>
    </div>
  </nav>
</template>

<script>
  import { useRouter } from 'vue-router'


  export default {
    name: 'Navbar',
    props: {
      hideContent: {
        type: Boolean,
        default: false
      }
    },
    setup() {
      const router = useRouter()

      const pushHome = () => {
        if (window.location.pathname === '/') {
          router.go()
        }
        else {
          router.push({
            name: 'home'
          })
        }
      }

      const pushSignup = () => {
        router.push({
          name: 'signup',
          query: {
            step: 'userType'
          }
        })
      }

      return {
        pushHome,
        pushSignup
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
