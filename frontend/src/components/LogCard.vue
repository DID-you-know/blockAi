<template>
  <div class="card">
    <div class="card-image">
      <img src="@/assets/image/GS25.png" alt="">
    </div>
    <div class="card-body">
      <div class="title fw-bold fs-3">GS25</div>
      <div class="datetime">
        <div class="date">
          2010-10-10
        </div>
        <div class="time">
          월요일 14:30
        </div>
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
    border: 1px solid $tertiary;
    border-radius: 1rem;
    width: 600px;
    height: 110px;
    display: flex;

    .card-image {
      flex: 4;
      display: flex;
      justify-content: center;
      align-items: center;

      img {
        max-width: 80%;
      }
    }

    .card-body {
      flex: 6;
      padding: 5px 2rem;
      display: flex;
      flex-direction: column;
      justify-content: space-between;

      .datetime {
        text-align: right;
        display: flex;
        flex-direction: column;
        justify-content: flex-end;
      }
    }
  }
</style>