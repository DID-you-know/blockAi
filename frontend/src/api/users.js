import _axios from './interceptor'


export default {
  sendSMS(phoneNumber, randomCode) {
    return _axios({
      url: '/users/sms',
      method: 'post',
      data: {
        'phone': phoneNumber,
        'randomCode': randomCode
      }
    })
  },
  signup(userInfo) {
    return _axios({
      url: '/users',
      method: 'post',
      data: userInfo
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