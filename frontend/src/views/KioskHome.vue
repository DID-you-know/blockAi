<template>
  <div class="body kiosk">
    <img class="company-logo" src="@/assets/image/GS25.png" alt="">
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
      <div class="button" @click="showModal">
        결제하기
      </div>
    </div>
    <Modal v-show="modalBackgroundOn" :isModalVisible="isModalVisible" @close="closeModal">
      <KioskLogin v-if="step===1" @pass="nextStep"/>
      <Certification v-if="step===2" @pass="nextStep" @close="closeModal"/>
      <KioskPayment v-if="step===3"/>
    </Modal>
  </div>
</template>

<script>
  import Item from '@/components/Item'
  import Modal from '@/components/Modal'
  import KioskLogin from '@/components/KioskLogin'
  import Certification from '@/components/Certification'
  import KioskPayment from '@/components/KioskPayment'
  import { computed, ref } from 'vue'
  import { useRouter } from 'vue-router'


  export default {
    name: 'KioskHome',
    components: {
      Item,
      Modal,
      KioskLogin,
      Certification,
      KioskPayment
    },
    setup() {
      const router = useRouter()

      const itemList = ref([
        {
          image: 'item1',
          name: '참이슬 fresh',
          price: 1300,
          count: 2
        },
        {
          image: 'item2',
          name: '처음처럼',
          price: 1300,
          count: 2
        },
        {
          image: 'item3',
          name: '진로이즈백',
          price: 1300,
          count: 3
        },
        {
          image: 'item4',
          name: '테라',
          price: 1450,
          count: 4
        },
        {
          image: 'item5',
          name: '장수막걸리',
          price: 1300,
          count: 2
        }
      ])
      const totalPrice = computed(() => {
        return itemList.value.reduce((prev, cur) => prev + cur.price * cur.count, 0)
      })
      const totalCount = computed(() => {
        return itemList.value.reduce((prev, cur) => prev + cur.count, 0)
      })

      const modalBackgroundOn = ref(false)
      const isModalVisible = ref(false)
      const showModal = () => {
        modalBackgroundOn.value = true
        isModalVisible.value = true
      }

      const step = ref(1)
      const nextStep = () => {
        step.value += 1
      }

      const closeModal = () => {
        step.value = 1
        isModalVisible.value = false
        setTimeout(() => {
          modalBackgroundOn.value = false
        }, 800)
      }

      return {
        router,
        itemList,
        totalPrice,
        totalCount,
        modalBackgroundOn,
        isModalVisible,
        showModal,
        step,
        nextStep,
        closeModal
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
        width: 40vh;
        border-radius: 3vh;
        font-size: 2.5vh;

        &:hover {
          background-color: $kiosk-hover;
        }
      }
    }
  }
</style>