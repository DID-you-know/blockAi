<template>
  <div class="body certification-log">
    <Navbar/>
    <div class="log-container">
      <LogCard/>
    </div>
    <div class="image">
      <img src="@/assets/image/certification-log/certification-log.svg" alt="">
    </div>
  </div>
</template>

<script>
  import Navbar from '@/components/Navbar'
  import LogCard from '@/components/LogCard'


  export default {
    name: 'CertificationLog',
    components: {
      Navbar,
      LogCard
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
      flex: 1;

      img {
        max-width: 100%;
      }
    }

    .log-container {
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      gap: 1rem;
    }
  }
</style>