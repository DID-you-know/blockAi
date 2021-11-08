import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/Home'
import Signup from '@/views/Signup'
import Login from '@/views/Login'
import Status from '@/views/Status'
import Issue from '@/views/Issue'


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
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router