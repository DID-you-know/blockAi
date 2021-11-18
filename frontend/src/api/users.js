import _axios from './interceptor'
import axios from 'axios'
import didAxios from './didAxios'


export default {
  sendSMS(phoneNumber, randomCode) {
    return axios({
      url: '/users/sms',
      method: 'post',
      data: {
        'phone': phoneNumber,
        'randomCode': randomCode
      }
    })
  },
  signup(userInfo) {
    return axios({
      url: '/users',
      method: 'post',
      data: userInfo
    })
  },
  login(credentials) {
    return axios({
      url: '/users/login',
      method: 'post',
      data: credentials
    })
  },
  getLog(userId) {
    return _axios({
      url: `/users/${userId}/log`,
      method: 'get'
    })
  },
  didIssue(userId, didData) {
    console.log(didData)
    return didAxios({
      url: `/users/${userId}/did/issue`,
      method: 'post',
      data: didData
    })
  },
  getFace(userId) {
    return _axios({
      url: `/ai/users/${userId}/face`,
      method: 'get'
    })
  },
  getVoice(userId) {
    return _axios({
      url: `/ai/users/${userId}/voice`,
      method: 'get'
    })
  }
}