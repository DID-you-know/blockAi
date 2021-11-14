<template>
  <div class="body kiosk">
    <img class="company-logo" src="@/assets/image/logo.png" alt="">
    <div class="header">
      <h1 class="nanumGothic title">주문내역</h1>
    </div>
    <div class="item-list">
      <Item v-for="(item, idx) in itemList" :key="idx" :image="item.image" :name="item.name" :price="item.price" :count="item.count"/>
    </div>
    <div class="total">
      <div class="count row"><span class="label">총 수량</span><span class="value fw-bold">{{ totalCount }}개</span></div>
      <div class="price row"><span class="label">총 금액</span><span class="value fw-bold"><span class="total-price">{{ totalPrice }}</span>원</span></div>
    </div>
    <div class="footer">
      <div class="button" @click="router.push({ name: 'kioskLogin' })">
        결제하기
      </div>
    </div>
  </div>
</template>

<script>
  import Item from '@/components/Item'
  import { computed, ref } from 'vue'
  import { useRouter } from 'vue-router'


  export default {
    name: 'KioskHome',
    components: {
      Item
    },
    setup() {
      const router = useRouter()

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
        {
          image: 'item1',
          name: '참이슬',
          price: 3300,
          count: 5
        }
      ])
      const totalPrice = computed(() => {
        return itemList.value.reduce((prev, cur) => prev + cur.price * cur.count, 0)
      })
      const totalCount = computed(() => {
        return itemList.value.reduce((prev, cur) => prev + cur.count, 0)
      })

      return {
        router,
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
    align-items: center;
    height: 100vh;
    background-color: $white;
    gap: 2vh;
    padding: 5vh;
    text-align: center;
    font-size: 2vh;

    .company-logo {
      flex: 1;
      width: 40vh;
    }

    .header {
      flex: 1;
      display: flex;
      width: 40vh;
      justify-content: flex-start;
    }
      
    .title {
      font-size: 5vh;
      margin-top: 2vh;
      text-align: center;
    }

    .item-list {
      flex: 6;
      display: flex;
      flex-direction: column;
      gap: 2vh;
      background-color: $light-hover;
      padding: 2vh;
      border-radius: 3vh;
    }

    .total {
      flex: 2;
      width: 45vh;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 1vh;

      .row {
        display: flex;
        padding: 0 3vh;
        width: 40vh;

        .label {
          flex: 5;
          text-align: center;
        }

        .value {
          flex: 5;
          text-align: center;
        }
      }
      
      .count{
        font-size: 2vh;
      }

      .price {
        font-size: 3vh;
        border: 0.2vh solid $secondary;
        border-radius: 3vh;
        padding : 1vh;

        .total-price {
          color: $danger;
        }
      }
    }

    .footer {
      flex: 1;

      .button {
        background-color: $kiosk;
        padding: 1vh 3vh;
        border-radius: 1vh;
        font-size: 2.5vh;

        &:hover {
          background-color: $kiosk-hover;
        }
      }
    }
  }
</style>