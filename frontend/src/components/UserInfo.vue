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
          <input type="text">
        </div>
        <div class="form-items">
          <div class="form-item">
            <label class="label fw-light">이름</label>
            <input type="text">
          </div>
          <div class="form-item">
            <label class="label fw-light">전화번호</label>
            <input type="text">
          </div>
        </div>
        <div class="form-item">
          <label class="label fw-light">생년월일</label>
          <input type="text">
        </div>
        <button>회원가입</button>
      </div>
    </section>
    <aside>
      <img src="@/assets/image/signup-form.svg" alt="">
    </aside>
  </div>
</template>

<script>
  export default {
    name: 'UserInfo'
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

    h1 {
      cursor: default;
    }

    section {
      flex: 1;

      .form {
        padding: 3rem;
        border-radius: 2rem;
        box-shadow: 2px 2px 20px 0px rgba(0, 0, 0, 0.2);;
        display: flex;
        flex-direction: column;
        gap: 3rem;

        .form-item {
          position: relative;
          flex: 1;

          .label {
            position: absolute;
            top: -27px;
            left: 5px;
          }
        }

        .form-items {
          display: flex;
          gap: 1rem;
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