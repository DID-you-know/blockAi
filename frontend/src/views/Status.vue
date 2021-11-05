<template>
  <div class="status body">
    <Navbar/>
    <DIDCard/>
  </div>
</template>

<script>
  import Navbar from '@/components/Navbar'
  import DIDCard from '@/components/DIDCard'


  export default {
    name: 'Status',
    components: {
      Navbar,
      DIDCard
    }
  }
</script>

<style lang="scss" scoped>
  .status {
    padding-top: 7rem;
  }
</style>