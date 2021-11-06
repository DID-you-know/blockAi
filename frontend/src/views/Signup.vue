<template>
  <div class="signup">
    <section>
      <div class="form">
        <h1 class="fs-3 fw-bold">회원가입</h1>
        <Input label="아이디" :error="usernameError" :paste="false" v-model="username" @input="setUsername"/>
        <Input label="비밀번호" :error="passwordError" :paste="false" type="password" v-model="password" @input="setPassword"/>
        <div class="form-items">
          <Input label="이름" :error="nameError" :paste="false" v-model="name" @input="setName"/>
          <Input label="생년월일" :error="birthError" v-model="birth" @input="setBirth"/>
        </div>
        <div class="form-items">
          <div class="form-items">
            <Input label="전화번호" :error="phoneNumberError" v-model="phoneNumber" @input="setPhoneNumber"/>
          </div>
          <div class="form-items">
            <Input label="인증번호" v-model="code" @input="setCode"/>
            <div class="form-item">
              <FormButton value="문자인증"/>
            </div>
          </div>
        </div>
        <FormButton value="회원가입" @click="submit"/>
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
  import Input from '@/components/Input'
  import { ref } from 'vue'


  export default {
    name: 'Signup',
    components: {
      FormButton,
      Input
    },
    setup() {
      // 문자 확인하는 함수
      // 있으면 true 반환, 없으면 false 반환
      const checkWhitespace = (value) => {
        if (value.search(/\s/) != -1) {
          return true
        }
        return false
      }
      const checkSpecial = (value) => {
        const special = /[!?@#$%^&*():;+\-=~{}<>_[\]|\\"',./`₩]/
        if (value.search(special) != -1) {
          return true
        }
        return false
      }
      const checkKor = (value) => {
        const kor = /[ㄱ-ㅎㅏ-ㅣ가-힣]/
        if (value.search(kor) != -1) {
          return true
        }
        return false
      }
      const checkEng = (value) => {
        const eng = /[a-zA-z]/
        if (value.search(eng) != -1) {
          return true
        }
        return false
      }
      const checkNum = (value) => {
        const num = /[0-9]/
        if (value.search(num) != -1) {
          return true
        }
        return false
      }

      // 아이디
      const username = ref('')
      const usernameError = ref('')
      const usernameValidator = () => {
        if (!username.value) {
          usernameError.value = '아이디 입력은 필수입니다.'
        } else if (checkWhitespace(username.value)) {
          usernameError.value = '아이디는 빈 칸을 포함할 수 없습니다.'
        } else if (checkSpecial(username.value)) {
          usernameError.value = '아이디는 특수문자를 포함할 수 없습니다.'
        } else if (username.value.length < 4 || username.value.length > 20 || checkKor(username.value)) {
          usernameError.value = '아이디는 영문, 숫자 조합으로 4~20자 입니다.'
        } else {
          usernameError.value = ''
        }
      }
      const setUsername = (event) => {
        event.target.value = event.target.value.trim()
        username.value = event.target.value
        usernameValidator()
      }

      // 비밀번호
      const password = ref('')
      const passwordError = ref('')
      const passwordValidator = () => {
        if (!password.value) {
          passwordError.value = '비밀번호 입력은 필수입니다.'
        } else if (checkWhitespace(password.value)) {
          passwordError.value = '비밀번호는 빈 칸을 포함할 수 없습니다.'
        } else if (
          password.value.length < 8 || 
          password.value.length > 20 || 
          !checkSpecial(password.value) || 
          !checkEng(password.value) || 
          !checkNum(password.value)) {
          passwordError.value = '비밀번호는 영문, 숫자, 특수문자 조합으로 8~20자 입니다.'
        } else {
          passwordError.value = ''
        }
      }
      const setPassword = (event) => {
        event.target.value = event.target.value.trim()
        password.value = event.target.value
        passwordValidator()
      }

      // 이름
      const name = ref('')
      const nameError = ref('')
      const nameValidator = () => {
        if (!name.value) {
          nameError.value = '이름 입력은 필수입니다.'
        } else {
          nameError.value = ''
        }
      }
      const setName = (event) => {
        event.target.value = event.target.value.trim()
        name.value = event.target.value
        nameValidator()
      }

      // 생년월일
      const birth = ref('')
      const birthError = ref('')
      const convertBirth = (birth) => {
        birth = birth.trim().replace(/[^0-9]/g, '')
        if (birth.length <= 4) {
          return birth.slice(0, 4)
        }
        if (birth.length <= 6) {
          return birth.slice(0, 4) + '.' + birth.slice(4, 6)
        }
        return birth.slice(0, 4) + '.' + birth.slice(4, 6) + '.' + birth.slice(6, 8)
      }
      const birthValidator = () => {
        const pattern = /\d{4}.\d{2}.\d{2}/
        if (birth.value.search(pattern) != -1){
          birthError.value = ''
        } else {
          birthError.value = '생년월일 입력은 필수입니다.'
        }
      }
      const setBirth = (event) => {
        event.target.value = convertBirth(event.target.value)
        birth.value = event.target.value
        birthValidator()
      }

      // 전화번호
      const phoneNumber = ref('')
      const phoneNumberError = ref('')
      const convertPhoneNumber = (phoneNumber) => {
        phoneNumber = phoneNumber.trim().replace(/[^0-9]/g, '')
        if (phoneNumber.length <= 3) {
          return phoneNumber.slice(0, 3)
        }
        if (phoneNumber.length <= 7) {
          return phoneNumber.slice(0, 3) + '-' + phoneNumber.slice(3, 7)
        }
        return phoneNumber.slice(0, 3) + '-' + phoneNumber.slice(3, 7) + '-' + phoneNumber.slice(7, 11)
      }
      const phoneNumberValidator = () => {
        const pattern = /\d{3}-\d{4}-\d{4}/
        if (phoneNumber.value.search(pattern) != -1){
          phoneNumberError.value = ''
          } else {
          phoneNumberError.value = '전화번호 입력은 필수입니다.'
        }
      }
      const setPhoneNumber = (event) => {
        event.target.value = convertPhoneNumber(event.target.value)
        phoneNumber.value = event.target.value
        phoneNumberValidator()
      }

      // 인증번호
      const code = ref('')
      const setCode = (event) => {
        event.target.value = event.target.value.trim()
        code.value = event.target.value
      }

      const submit = () => {
        usernameValidator()
        passwordValidator()
        nameValidator()
        birthValidator()
        phoneNumberValidator()

        if (!usernameError.value && !passwordError.value && !nameError.value && !birthError.value && !phoneNumberError.value) {
          console.log('submit')
        }
      }

      return {
        username,
        usernameError,
        setUsername,
        password,
        passwordError,
        setPassword,
        name,
        nameError,
        setName,
        birth,
        birthError,
        setBirth,
        phoneNumber,
        phoneNumberError,
        setPhoneNumber,
        code,
        setCode,
        submit
      }
    }
  }
</script>

<style lang="scss" scoped>
  .signup {
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