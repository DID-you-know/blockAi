import axios from 'axios'
import store from '@/store'


const didAxios = axios.create({
  baseURL: process.env.VUE_APP_SERVER_URL,
  timeout: 0
})

didAxios.interceptors.request.use(
  (config) => {
    config.headers['Authorization'] = `Bearer ${store.state.users.accessToken}`
    return config
  })

export default didAxios