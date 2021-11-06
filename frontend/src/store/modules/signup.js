const state = {
  step: 1,
  userType: ''
}

const actions = {

}

const mutations = {
  INCREASE_STEP(state) {
    state.step += 1
  },
  SET_USERTYPE(state, payload) {
    state.userType = payload
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