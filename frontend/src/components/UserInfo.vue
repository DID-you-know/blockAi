<template>
  <div class="userinfo">
    <section>
      <div class="form">
        <h1 class="fs-3 fw-bold">회원가입</h1>
        <div class="form-item">
          <label class="label fw-light">아이디</label>
          <input type="text">
        </div>
        <div class="form-item">
          <label class="label fw-light">비밀번호</label>
          <input type="password">
        </div>
        <div class="form-items">
          <div class="form-item">
            <label class="label fw-light">이름</label>
            <input type="text">
          </div>
          <div class="form-item">
            <label class="label fw-light">생년월일</label>
            <input type="text">
          </div>
        </div>
        <div class="form-items">
          <div class="form-items">
            <div class="form-item">
              <label class="label fw-light">전화번호</label>
              <input type="text">
            </div>
          </div>
          <div class="form-items">
            <div class="form-item">
              <label class="label fw-light">인증번호</label>
              <input type="text">
            </div>
            <div class="form-item">
              <FormButton value="인증번호 받기"/>
            </div>
          </div>
        </div>
        <FormButton value="회원가입"/>
      </div>
      <div class="bg lefttop">
        <img class="light" src="@/assets/image/background/light1.svg" alt="">
        <img class="dark" src="@/assets/image/background/dark1.svg" alt="">
      </div>
      <div class="bg bottom">
        <img class="light" src="@/assets/image/background/light2.svg" alt="">
        <img class="dark" src="@/assets/image/background/dark2.svg" alt="">
      </div>
    </section>
    <aside>
      <img src="@/assets/image/signup/signup-form.svg" alt="">
    </aside>
  </div>
</template>

<script>
  import FormButton from '@/components/FormButton'


  export default {
    name: 'UserInfo',
    components: {
      FormButton
    }
  }
</script>

<style lang="scss" scoped>
  .userinfo {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100vw;
    height: 100vh;
    padding: 5rem;
    gap: 1rem;
    overflow: hidden;

    h1 {
      cursor: default;
    }

    section {
      flex: 1;
      height: 100%;
      min-height: 600px;
      position: relative;

      .form {
        background-color: white;
        padding: 3rem;
        border-radius: 2rem;
        box-shadow: 5px 5px 20px 0px rgba(0, 0, 0, 0.1);;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        gap: 3rem;
        height: 100%;

        .form-item {
          position: relative;

          .label {
            position: absolute;
            top: -27px;
            left: 5px;
          }
        }

        .form-items {
          display: flex;
          gap: 1rem;
          width: 100%;

          .form-item {
            flex: 1;
          }
        }
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
          transform: rotate(-45deg)
        }

        .dark {
          left: -80px;
          top: -130px;
          transform: rotate(60deg)
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

      img {
        max-width: 100%;
      }
    }
  }
</style>