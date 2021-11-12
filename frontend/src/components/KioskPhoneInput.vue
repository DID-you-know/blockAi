<template>
  <div class="kiosk-phone-input">
    <input class="input" type="text" ref="input" v-model="phoneNumber">
    <div class="number-pad">
      <div class="row">
        <div class="key" @click="clickNumber(1)"><span>1</span></div>
        <div class="key" @click="clickNumber(2)"><span>2</span></div>
        <div class="key" @click="clickNumber(3)"><span>3</span></div>
      </div>
      <div class="row">
        <div class="key" @click="clickNumber(4)"><span>4</span></div>
        <div class="key" @click="clickNumber(5)"><span>5</span></div>
        <div class="key" @click="clickNumber(6)"><span>6</span></div>
      </div>
      <div class="row">
        <div class="key" @click="clickNumber(7)"><span>7</span></div>
        <div class="key" @click="clickNumber(8)"><span>8</span></div>
        <div class="key" @click="clickNumber(9)"><span>9</span></div>
      </div>
      <div class="row">
        <div class="key text" @click="backspace"><span>이전</span></div>
        <div class="key" @click="clickNumber(0)"><span>0</span></div>
        <div class="key text submit" @click="submit"><span>blockAi 연결</span></div>
      </div>
    </div>
  </div>
</template>

<script>
  import { ref } from 'vue'
  import { useStore } from 'vuex'
  import { useRouter } from 'vue-router'


  export default {
    name: 'KioskPhoneInput',
    setup() {
      const store = useStore()
      const router = useRouter()
      const phoneNumber = ref('')
      const input = ref(null)

      const clickNumber = (number) => {
        phoneNumber.value += number
      }
      const backspace = () => {
        phoneNumber.value = phoneNumber.value.slice(0, phoneNumber.value.length-1)
      }
      const submit = () => {
        console.log(phoneNumber.value)
        // store.dispatch('certification/getUserId', phoneNumber.value)
        store.commit('certification/SET_PHONE', phoneNumber)
        router.push({ name: 'kioskLogin' })
      }

      return {
        phoneNumber,
        input,
        clickNumber,
        backspace,
        submit
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";

  .kiosk-phone-input {
    display: flex;
    flex-direction: column;

    .input {
      border: 0.3vh solid $white;
      border-radius: 1vh;
      margin: 4vh 0;
      font-size: 3vh;
      padding: 1.5vh;
      background-color: transparent;
      color: $white;
      width: 40vh;
      letter-spacing: 0.1vh;

      &:focus {
        outline: none;
      }
    }

    .number-pad {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      gap: 3vh;

      .row {
        display: flex;
        gap: 3vh;

        .key {
          background-color: $tertiary;
          color: $primary;
          width: 11vh;
          height: 11vh;
          font-size: 4vh;
          border-radius: 4vh;
          box-shadow: 0.3vh 0.3vh 0.6vh 0.3vh rgba($color: #000000, $alpha: 0.2);
          display: flex;
          justify-content: center;
          align-items: center;
          padding: 1vh;
        }
        
        .text {
          font-size: 2.3vh;
        }

        .submit {
          background-color: $primary;
          color: $white;
        }
      }
    }
  }
</style>