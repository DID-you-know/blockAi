import axios from 'axios'
import store from '@/store'


const _axios = axios.create({
  baseURL: process.env.VUE_APP_SERVER_URL,
  timeout: 5000
})

_axios.interceptors.request.use(
  (config) => {
  config.headers['Authorization'] = `Bearer ${store.state.users.accessToken}`
  return config
})

export default _axios