<template>
  <div class="login body">
    <section>
      <div class="form">
        <div>
          <Logo/>
          <h1 class="fs-3 fw-bold">로그인</h1>
        </div>
        <Input label="아이디" v-model="username" @input="setUsername"/>
        <Input label="비밀번호" type="password" v-model="password" @input="setPassword"/>
        <FormButton value="로그인" @click="submit"/>
      </div>
      <div class="bg lefttop">
        <img class="light" src="@/assets/image/background/light1.svg" alt="">
        <img class="dark" src="@/assets/image/background/dark3.svg" alt="">
      </div>
      <div class="bg bottom">
        <img class="light" src="@/assets/image/background/light2.svg" alt="">
        <img class="dark" src="@/assets/image/background/dark2.svg" alt="">
      </div>
    </section>
    <aside>
      <img src="@/assets/image/login/login.svg" alt="">
      <img class="bg-image" src="@/assets/image/background/light3.svg" alt="">
    </aside>
  </div>
</template>

<script>
  import Input from '@/components/Input'
  import FormButton from '@/components/FormButton'
  import Logo from '@/components/Logo'
  import { ref } from 'vue'


  export default {
    name: 'Login',
    components: {
      Input,
      FormButton,
      Logo
    },
    setup() {
      // 아이디
      const username = ref('')
      const setUsername = (event) => {
        event.target.value = event.target.value.trim()
        username.value = event.target.value
      }

      // 비밀번호
      const password = ref('')
      const setPassword = (event) => {
        event.target.value = event.target.value.trim()
        password.value = event.target.value
      }

      // 로그인 버튼
      const submit = () => {
        console.log('submit')
      }

      return {
        username,
        setUsername,
        password,
        setPassword,
        submit
      }
    }
  }
</script>

<style lang="scss" scoped>
  .login {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 5rem;
    gap: 1rem;

    h1 {
      cursor: default;
      text-align: center;
      margin-top: 1rem;
      margin-bottom: 3rem;
    }

    section {
      flex: 1;
      position: relative;
      display: flex;
      justify-content: center;

      .form {
        height: 100%;
        width: 650px;
        background-color: white;
        padding: 3rem;
        border-radius: 2rem;
        box-shadow: 5px 5px 20px 0px rgba(0, 0, 0, 0.1);;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        gap: 3rem;
      }

      .bg {
        position: absolute;
        z-index: -1;

        img {
          position: absolute;
        }
      }

      .lefttop {
        left: -400px;
        top: -300px;

        .light {
          transform: rotate(60deg)
        }

        .dark {
          left: -80px;
          top: -100px;
          transform: rotate(0deg)
        }
      }

      .bottom {
        bottom: 200px;
        right: 550px;

        .light {
          transform: rotate(-10deg)
        }

        .dark {
          top: 50px;
          left: 30px;
          transform: rotate(20deg)
        }
      }
    }

    aside {
      flex: 1;
      position: relative;

      img {
        width: 100%;
      }

      .bg-image {
        position: absolute;
        z-index: -1;
        left: 200px;
        top: -200px;
        opacity: 0.15;
      }
    }
  }
</style>