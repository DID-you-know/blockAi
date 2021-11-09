import _axios from './interceptor'


export default {
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