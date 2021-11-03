<template>
  <div>
    <template v-if="step === 1">
      <Navbar hideContent/>
      <UserType/>
    </template>
    <UserInfo v-if="step === 2"/>
  </div>
</template>

<script>
  import Navbar from '@/components/Navbar'
  import UserType from '@/components/UserType'
  import UserInfo from '@/components/UserInfo'
  import { useStore } from 'vuex'
  import { computed } from 'vue'



  export default {
    name: 'Signup',
    components: {
      Navbar,
      UserType,
      UserInfo
    },
    setup() {
      const store = useStore()
      const step = computed(() => store.state.signup.step)
      const userType = computed(() => store.state.signup.userType)

      return {
        step,
        userType
      }
    }
  }
</script>

<style lang="scss" scoped>
  
</style>