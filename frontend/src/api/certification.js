import axios from 'axios'


export default {
  getUserId(phoneNumber) {
    return axios({
      url: `/users/phone/${phoneNumber}`,
      method: 'get'
    })
  },
  certification(userId, face, voice, certifiedBy) {
    return axios({
      url: `/certification/users/${userId}`,
      method: 'post',
      data: {
        face: face,
        voice: voice,
        certified_by: certifiedBy
      }
    })
  }
}