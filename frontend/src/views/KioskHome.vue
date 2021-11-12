<template>
  <div class="body kiosk">
    <img class="company-logo" src="@/assets/image/logo.png" alt="">
    <div>
      <h1 class="nanum title">주문내역</h1>
    </div>
    <div class="item-list">
      <Item v-for="item in itemList" :key="item.name" :image="item.image" :name="item.name" :price="item.price" :count="item.count"/>
    </div>
    <div class="total">
      <span>총 수량 : <span class="fw-bold">{{ totalCount }}</span>개</span>
      <span>총 금액 : <span class="fw-bold">{{ totalPrice }}</span>원</span>
    </div>
    <div>
      결제하기
    </div>
  </div>
</template>

<script>
  import Item from '@/components/Item'
  import { computed, ref } from 'vue'


  export default {
    name: 'KioskHome',
    components: {
      Item
    },
    setup() {
      const itemList = ref([
        {
          image: 'item1',
          name: '참이슬',
          price: 1300,
          count: 2
        },
        {
          image: 'item1',
          name: '참이슬',
          price: 3300,
          count: 5
        },
        {
          image: 'item1',
          name: '참이슬',
          price: 3300,
          count: 5
        },
        {
          image: 'item1',
          name: '참이슬',
          price: 3300,
          count: 5
        },
      ])

      const totalPrice = computed(() => {
        return itemList.value.reduce((prev, cur) => prev + cur.price * cur.count, 0)
      })

      const totalCount = computed(() => {
        return itemList.value.reduce((prev, cur) => prev + cur.count, 0)
      })

      return {
        itemList,
        totalPrice,
        totalCount
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";


  .kiosk {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    height: 100vh;
    background-color: $light-hover;
    gap: 2vh;
    padding: 5vh;
    text-align: center;
    font-size: 2vh;

    .company-logo {
      width: 40vh;
      margin-bottom: 5vh;
    }

    .title {
      font-size: 5vh;
      margin-bottom: 2vh;
    }

    .item-list {
      display: flex;
      flex-direction: column;
      gap: 2vh;
    }

    .total {
      width: 100%;
      display: flex;
      justify-content: space-around;
    }
  }
</style>