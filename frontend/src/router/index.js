import { createRouter, createWebHistory } from 'vue-router'
import store from '@/store'
import Home from '@/views/Home'
import Signup from '@/views/Signup'
import Login from '@/views/Login'
import Status from '@/views/Status'
import Issue from '@/views/Issue'
import ImageTest from '@/views/ImageTest'
import KioskHome from '@/views/KioskHome'


const routes = [
  // 메인 페이지
  {
    path: '/',
    name: 'home',
    component: Home
  },
  // 회원가입
  {
    path: '/signup',
    name: 'signup',
    component: Signup
  },
  // 로그인
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  // 발급현황
  {
    path: '/status',
    name: 'status',
    component: Status
  },
  // DID발급
  {
    path: '/issue',
    name: 'issue',
    component: Issue
  },
  // 키오스크 홈
  {
    path: '/kiosk',
    name: 'kioskHome',
    component: KioskHome
  },
  {
    path: '/image',
    name: 'Image',
    component: ImageTest
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

// 로그인 리다이렉트
router.beforeEach((to, from, next) => {
  if (!store.state.users.isLogin) {
    if (to.name === 'home' || to.name === 'signup' || to.name === 'login' || to.name === 'kioskHome') {
      next()
    } else {
      next({ name: 'login' })
    }
  } else next()
})

export default router
