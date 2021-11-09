<template>
  <div>
    <img @click="pushHome" class="logo" src="@/assets/image/logo.png" alt="logo">
  </div>
</template>

<script>
  import { useRouter } from 'vue-router'


  export default {
    name: 'Logo',
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

      return {
        pushHome
      }
    }
  }
</script>

<style lang="scss" scoped>
  .logo {
    width: auto;
    height: 53px;

    &:hover {
      cursor: pointer;
    }
  }
</style>