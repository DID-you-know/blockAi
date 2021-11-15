import certification from '@/api/certification'


const state = {
  userId: null,
  passFace: false,
  passVoice: false,
}

const actions = {
  async getUserId({ commit }, phoneNumber) {
    try {
      const response = await certification.getUserId(phoneNumber)
      console.log(response.data)
      const userId = response.data.userId
      commit('SET_USER_ID', userId)
    } catch (error) {
      console.log(error)
    }
  },
  async sendFace({ commit }, face) {
    try {
      const response = await certification.faceCertification(state.userId, face)
      console.log(response.data)
      const passFace = true
      commit('SET_PASSFACE', passFace)
    } catch (error) {
      console.log(error)
    }
  },
  async sendVoice({ commit }, voice) {
    try {
      const response = await certification.voiceCertification(state.userId, voice)
      console.log(response.data)
      const passVoice = true
      commit('SET_PASSVOICE', passVoice)
    } catch (error) {
      console.log(error)
    }
  }
}

const mutations = {
  SET_USER_ID(state, userId) {
    state.userId = userId
  },
  SET_PASSFACE(state, passFace) {
    state.passFace = passFace
  },
  SET_PASSVOICE(state, passVoice) {
    state.passVoice = passVoice
  },
  RESET(state) {
    state.userId = null
    state.passFace = false
    state.passVoice = false
  }
}

const getters = {
  isPassed(state) {
    return state.passFace && state.passVoice
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}