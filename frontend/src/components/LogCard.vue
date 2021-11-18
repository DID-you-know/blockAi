<template>
  <div class="card">
    <div class="card-image">
      <img src="@/assets/image/onlyLogo.png" alt="">
    </div>
    <div class="card-body">
      <div class="title fw-bold fs-3">GS25</div>
      <div class="datetime">
        2010-10-10 월요일 14:30
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'LogCard'
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";


  .card {
    border-radius: 1rem;
    background-color: $light-hover;
    width: 100%;
    height: 60px;
    min-height: 40px;
    display: flex;

    .card-image {
      flex: 1;
      display: flex;
      justify-content: center;
      align-items: center;

      img {
        max-width: 80%;
      }
    }

    .card-body {
      flex: 5;
      display: flex;
      justify-content: space-around;
      align-items: center;

      .datetime {
        text-align: right;
        display: flex;
        justify-content: flex-end;
        gap: 1rem;
      }
    }
  }
</style>