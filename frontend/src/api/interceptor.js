import axios from 'axios'


const _axios = axios.create({
  baseURL: process.env.VUE_APP_SERVER_URL
})

export default _axios