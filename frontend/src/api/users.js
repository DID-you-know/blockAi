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
  getUserId(phoneNumber) {
    return axios({
      url: `/users/phone/${phoneNumber}`,
      method: 'get'
    })
  },
  faceIssue(userId, faceData) {
    return _axios({
      url: `/users/${userId}/face/issue`,
      method: 'post',
      data: faceData
    })
  },
  voiceIssue(userId, voiceData) {
    return _axios({
      url: `/users/${userId}/voice/issue`,
      method: 'post',
      data: voiceData
    })
  },
  didIssue(userId, didData) {
    return _axios({
      url: `/users/${userId}/did/issue`,
      method: 'post',
      data: didData
    })
  }
}