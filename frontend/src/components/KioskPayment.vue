<template>
  <div class="body payment">
    <header class="nanum">결제 수단을 선택해 주세요.</header>
    <div class="row">
      <div class="column">
        <img src="@/assets/image/icon/credit-card.png" alt="credit card">
        <div>신용카드</div>
      </div>
      <div class="column">
        <img src="@/assets/image/icon/samsung-pay.png" alt="samsung pay">
        <div>삼성페이</div>
      </div>
    </div>
    <div class="row">
      <div class="column">
        <img src="@/assets/image/icon/kakao-pay.png" alt="kakao pay" style="border-radius: 50%;">
        <div>카카오페이</div>
      </div>
      <div class="column">
        <div class="image-container">
          <img src="@/assets/image/icon/naver-pay.png" alt="naver pay" style="width: 15vh; height: auto;">
        </div>
        <div>네이버페이</div>
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
    gap: 5vh;
    padding: 5vh;
    text-align: center;
    border-radius: inherit;
    padding-bottom: inherit;

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
        height: 25vh;
        border-radius: 3vh;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        gap: 2vh;

        img {
          width: auto;
          height: 15vh;
        }

        .image-container {
          width: 15vh;
          height: 15vh;
          display: flex;
          align-items: center;
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
      width: 40vh;
      border-radius: 3vh;
    }
  }
</style>