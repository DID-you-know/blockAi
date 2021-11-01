import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/Home'


const routes = [
  // 메인 페이지
  {
    path: '/',
    name: 'Home',
    component: Home
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router