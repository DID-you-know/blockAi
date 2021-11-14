import _axios from './interceptor'
import axios from 'axios'


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
  didIssue(userId, didData) {
    return _axios({
      url: `/users/${userId}/did/issue`,
      method: 'post',
      data: didData
    })
  },
  getFace(userId) {
    return _axios({
      url: `/ai/${userId}/face`,
      method: 'get'
    })
  },
  getVoice(userId) {
    return _axios({
      url: `/ai/${userId}/voice`,
      method: 'get'
    })
  }
}