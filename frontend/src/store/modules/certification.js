import certification from '@/api/certification'


const state = {
  userId: null,
  isCertificated: false
}

const actions = {
  async getUserId({ commit }, phoneNumber) {
    try {
      const response = await certification.getUserId(phoneNumber)
      const userId = response.data.userId
      commit('SET_USER_ID', userId)
    } catch (error) {
      commit('SET_USER_ID', null)
    }
  },
  async certification({ commit, state }, { face, voice, certifiedBy }) {
    try {
      await certification.certification(state.userId, face, voice, certifiedBy)
      const isCertificated = true
      commit('SET_ISCERTIFICATED', isCertificated)
    } catch (error) {
      commit('SET_ISCERTIFICATED', false)
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