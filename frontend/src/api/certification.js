import axios from 'axios'
import didAxios from './didAxios'


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
    return didAxios({
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