import certification from '@/api/certification'


const state = {
  userId: null,
  isCertificated: false
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
  async certification({ commit }, { face, voice, certifiedBy }) {
    try {
      const response = await certification.certification(state.userId, face, voice, certifiedBy)
      console.log(response.data)
      const isCertificated = true
      commit('SET_ISCERTIFICATED', isCertificated)
    } catch (error) {
      console.log(error)
    }
  }
}

const mutations = {
  SET_USER_ID(state, userId) {
    state.userId = userId
  },
  SET_ISCERTIFICATED(state, isCertificated) {
    state.isCertificated = isCertificated
  },
  RESET(state) {
    state.userId = null
    state.isCertificated = false
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations
}