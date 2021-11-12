import axios from 'axios'

export default {
  faceCertification(userId, face) {
    return axios({
      url: `ai/${userId}/face`,
      method: 'post',
      data: {
        face: face
      }
    })
  },
  voiceCertification(userId, voice) {
    return axios({
      url: `ai/${userId}/voice`,
      method: 'post',
      data: {
        voice: voice
      }
    })
  },
}