import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/Home'
import Signup from '@/views/Signup'


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
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router