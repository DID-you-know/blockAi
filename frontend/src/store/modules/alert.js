const state = () => ({
  type: 'success',
  message: '',
  isOn: false
})

const actions = {
  async popAlert({ commit, dispatch }, payload) {
    commit('POP_ALERT', payload)
    dispatch('closeAlertTimer')
  },
  async closeAlertTimer({ commit }) {
    setTimeout(() => {
      commit('CLOSE_ALERT')
    }, 2000)
  }
}

const mutations = {
  POP_ALERT(state, payload) {
    state.type = payload.type
    state.message = payload.message
    state.isOn = true
  },
  CLOSE_ALERT(state) {
    state.isOn = false
  }
}

const getters = {

}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}