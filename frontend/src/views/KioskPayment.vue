<template>
  <div class="body payment">
    <header class="nanum">결제 수단을 선택해 주세요.</header>
    <div class="row">
      <div class="column">
        <img src="@/assets/image/item/item1.png" alt="">
        <div>신용카드</div>
      </div>
      <div class="column">
        <img src="" alt="">
        <div>삼성페이</div>
      </div>
    </div>
    <div class="row">
      <div class="column">
        <img src="" alt="">
        <div>카카오페이</div>
      </div>
      <div class="column">
        <img src="" alt="">
        <div>모바일상품권</div>
      </div>
    </div>
    <div class="button">선택</div>
  </div>
</template>

<script>
  export default {
    name: 'KioskPayment'
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";

  .payment {
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 100vh;
    background-color: $white;
    gap: 3vh;
    padding: 5vh;
    text-align: center;

    header {
      font-size: 3vh;
      margin-bottom: 2vh;
    }

    .row {
      display: flex;
      justify-content: center;
      align-items: center;
      gap: 3vh;

      .column {
        border: 0.1vh solid $secondary;
        width: 20vh;
        height: 20vh;
        border-radius: 3vh;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        gap: 1vh;

        img {
          width: 15vh;
          height: 15vh;
        }

        div {
          font-size: 2vh;
        }
      }
    }

    .button {
      font-size: 2.5vh;
      background-color: $kiosk;
      padding: 1vh 3vh;
      border-radius: 1vh;
    }
  }
</style>