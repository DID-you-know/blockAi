<template>
  <div class="signup body">
    <section>
      <div class="form">
        <h1 class="fs-3 fw-bold">회원가입</h1>
        <Input label="이메일" :error="emailError" :paste="false" v-model="email" @input="setEmail"/>
        <Input label="비밀번호" :error="passwordError" :paste="false" type="password" v-model="password" @input="setPassword"/>
        <div class="form-items">
          <Input label="이름" :error="nameError" :paste="false" v-model="name" @input="setName"/>
          <Input label="생년월일" :error="birthError" v-model="birth" @input="setBirth" placeholder="2000.01.01"/>
        </div>
        <div class="form-items">
          <div class="form-items">
            <Input label="전화번호" :error="phoneNumberError" v-model="phoneNumber" @input="setPhoneNumber" placeholder="010-1234-5678"/>
          </div>
          <div class="form-items">
            <div class="form-item">
              <FormButton value="문자인증" @click="sendSMS" :disabled="!codeButtonActive"/>
            </div>
            <Input label="인증번호" :error="codeError" v-model="code" @input="setCode" :disabled="!codeActive" :maxlength="6"/>
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
  import users from '@/api/users'
  import { useRouter } from 'vue-router'
  import { useStore } from 'vuex'


  export default {
    name: 'Signup',
    components: {
      FormButton,
      Input
    },
    setup() {
      const router = useRouter()
      const store = useStore()

      // 문자 확인하는 함수
      // 있으면 true 반환, 없으면 false 반환
      const checkWhitespace = (value) => {
        return /\s/.test(value)
      }
      const checkSpecial = (value) => {
        const special = /[!?@#$%^&*():;+\-=~{}<>_[\]|\\"',./`₩]/
        if (value.search(special) != -1) {
          return true
        }
        return false
      }
      const checkEng = (value) => {
        const eng = /[a-zA-z]/
        return eng.test(value)
      }
      const checkNum = (value) => {
        const num = /[0-9]/
        return num.test(value)
      }

      // 이메일
      const email = ref('')
      const emailError = ref('')
      const emailValidator = () => {
        const pattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/
        if (!email.value) {
          emailError.value = '이메일 입력은 필수입니다.'
        } else if (checkWhitespace(email.value)) {
          emailError.value = '이메일은 빈 칸을 포함할 수 없습니다.'
        } else if (!pattern.test(email.value)) {
          emailError.value = '잘못된 이메일 형식입니다.'
        } else {
          emailError.value = ''
        }
      }
      const setEmail = (event) => {
        event.target.value = event.target.value.trim()
        email.value = event.target.value
        emailValidator()
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
        birth = birth.replace(/[^0-9]/g, '')
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
        if (pattern.test(birth.value)){
          birthError.value = ''
        } else {
          birthError.value = '생년월일 8자리를 입력해주세요.'
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
        phoneNumber = phoneNumber.replace(/[^0-9]/g, '')
        if (phoneNumber.length <= 3) {
          return phoneNumber.slice(0, 3)
        }
        if (phoneNumber.length <= 7) {
          return phoneNumber.slice(0, 3) + '-' + phoneNumber.slice(3, 7)
        }
        return phoneNumber.slice(0, 3) + '-' + phoneNumber.slice(3, 7) + '-' + phoneNumber.slice(7, 11)
      }
      const codeButtonActive = ref(false)
      const phoneNumberValidator = () => {
        const pattern = /\d{3}-\d{4}-\d{4}/
        if (pattern.test(phoneNumber.value)){
          phoneNumberError.value = ''
          codeButtonActive.value = true
          } else {
          phoneNumberError.value = '전화번호 11자리를 입력해주세요.'
          codeButtonActive.value = false
        }
      }
      const setPhoneNumber = (event) => {
        event.target.value = convertPhoneNumber(event.target.value)
        phoneNumber.value = event.target.value
        phoneNumberValidator()
      }

      // 인증번호
      const randomCode = ref(null)
      const codeActive = ref(false)
      const getRandomCode = () => {
        let randomCode = ''
        for (let i = 0; i < 6; i++) {
          randomCode += Math.ceil(Math.random() * 9)
        }
        return randomCode
      }

      const sendSMS = async () => {
        randomCode.value = getRandomCode()
        codeActive.value = true
        try {
          await users.sendSMS(phoneNumber.value, randomCode.value)
          store.dispatch('alert/popAlert', {
            type: 'success',
            message: '문자가 발송되었습니다.'
          })
        } catch (error) {
          store.dispatch('alert/popAlert', {
            type: 'danger',
            message: '문자발송에 실패했습니다.'
          })
        }
      }

      const code = ref('')
      const codeError = ref('')
      const smsAuth = ref(false)
      const codeValidator = () => {
        const pattern = /\d{6}/
        if (code.value.search(pattern) != -1) {
          if (code.value === randomCode.value) {
            codeButtonActive.value = false
            codeActive.value = false
            smsAuth.value = true
            codeError.value = ''
          } else {
            codeError.value = '틀렸습니다.'
          }
        }
      }
      const setCode = (event) => {
        event.target.value = event.target.value.trim().replace(/[^0-9]/g, '')
        code.value = event.target.value
        codeValidator()
      }

      
      // 회원가입 form 제출
      const submit = async () => {
        emailValidator()
        passwordValidator()
        nameValidator()
        birthValidator()
        phoneNumberValidator()
        codeValidator()

        if (!emailError.value && !passwordError.value && !nameError.value && !birthError.value && !phoneNumberError.value && smsAuth.value) {
          const birthDate = new Date(birth.value.replace(/\./g, '-'))
          const userInfo = {
            email: email.value,
            password: password.value,
            name: name.value,
            phone: phoneNumber.value,
            birth: birthDate
          }
          try {
            await users.signup(userInfo)
            router.push({ name: 'home' })
            store.dispatch('alert/popAlert', {
              type: 'success',
              message: '회원가입에 성공했습니다.'
            })
          } catch (error) {
            store.dispatch('alert/popAlert', {
              type: 'danger',
              message: '회원가입에 실패했습니다.'
            })
          }
        }
      }

      return {
        email,
        emailError,
        setEmail,
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
        codeButtonActive,
        setPhoneNumber,
        code,
        setCode,
        codeError,
        randomCode,
        sendSMS,
        codeActive,
        smsAuth,
        submit
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import "@/assets/style/color.scss";


  .signup {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 5rem;
    gap: 1rem;
    background-color: $light;

    h1 {
      cursor: default;
      padding-bottom: 2rem;
    }

    section {
      flex: 1;
      position: relative;
      display: flex;
      justify-content: center;

      .form {
        height: 100%;
        width: 650px;
        background-color: $white;
        padding: 3rem;
        border-radius: 2rem;
        box-shadow: 5px 5px 20px 0px rgba(0, 0, 0, 0.1);;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        gap: 3rem;
        z-index: 2;

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
        z-index: 1;

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