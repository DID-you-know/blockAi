<template>
  <div class="item">
    <div class="image-container" >
      <img class="image" :src="require(`@/assets/image/item/${image}.png`)" alt="">
    </div>
    <span class="name fw-bold">{{ name }}</span>
    <span class="price"><span class="fw-bold">{{ price }}</span>원</span>
    <span class="count"><span class="fw-bold">{{ count }}</span>개</span>
  </div>
</template>

<script>
  export default {
    name: 'item',
    props: {
      name: String,
      price: Number,
      count: Number,
      image: String
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";

  .item {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: $white;
    padding: 1vh 2vh;
    border-radius: 3vh;
    width: 45vh;

    .image-container {
      flex: 2;
      
      .image {
        width: auto;
        height: 5vh;
      }
    }

    .name {
      flex: 5;
    }

    .price {
      flex: 3;
    }

    .count {
      flex: 2;
    }
  }
</style>