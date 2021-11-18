<template>
  <div class="card">
    <div class="card-image">
      <img src="@/assets/image/onlyLogo.png" alt="">
    </div>
    <div class="card-body">
      <div class="title">
        <span class="fw-bold fs-2">{{ company }}</span>
        <span v-if="idx===0" class="title-label">발급처</span>
      </div>
      <div class="datetime">
        <span>{{ datetime }}</span>
        <span v-if="idx===0" class="datetime-label">발급날짜</span>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'LogCard',
    props: {
      company: String,
      datetime: String,
      idx: Number
    }
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
        max-height: 80%;
      }
    }

    .card-body {
      flex: 5;
      display: flex;
      justify-content: space-around;
      align-items: center;

      .title {
        flex: 2;
        padding-left: 3rem;
        position: relative;
        line-height: 100%;

        .title-label {
          position: absolute;
          top: -3rem;
          font-size: 1rem;
          left: 3.5rem;
        }
      }

      .datetime {
        flex: 3;
        position: relative;
        line-height: 100%;
        display: flex;
        justify-content: center;

        .datetime-label {
          position: absolute;
          top: -3rem;
          font-size: 1rem;
          left: 50%;
          transform: translateX(-50%);
        }
      }
    }
  }

</style>