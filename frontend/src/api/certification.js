import axios from 'axios'


export default {
  getUserId(phoneNumber) {
    return axios({
      url: `/users/phone/${phoneNumber}`,
      method: 'get'
    })
  },
  certification(userId, face, voice, certifiedBy) {
    console.log('userId', userId)
    console.log('face', face)
    console.log('voice', voice)
    console.log('certifiedBy', certifiedBy)
    return axios({
      url: `/certification/users/${userId}`,
      method: 'post',
      data: {
        face: face,
        voice: voice,
        certifiedBy: certifiedBy
      }
    })
  }
}