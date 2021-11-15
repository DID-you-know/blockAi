import users from '@/api/users'


const state = () => ({
  accessToken: null,
  isLogin: false,
  userId: null,
  name: null,
  isIssued: false,
  issuedDate: null,
})

const actions = {
  async getAccessToken({ commit }, credentials) {
    try {
      const response = await users.login(credentials)
      console.log(response.data)
      const accessToken = response.data.accessToken
      commit('SET_ACCESS_TOKEN', accessToken)
    } catch (error) {
      console.log(error)
    }
  },
  async didIssue({ commit }, { userId, didData }) {
    try {
      const response = await users.didIssue(userId, didData)
      console.log(response.data)
      commit('SET_ISISSUED', true)
    } catch (error) {
      console.log(error)
      commit('SET_ISISSUED', false)
      console.log('didIssue')
    }
  }
}

const mutations = {
  SET_ACCESS_TOKEN(state, accessToken) {
    state.accessToken = accessToken
  },
  SET_ISISSUED(state, payload) {
    state.isIssued = payload
  },
  LOGOUT(state) {
    state.accessToken = null
    state.isLogin = false
    state.userId = null
    state.name = null
    state.isIssued = false
    state.issuedDate = null
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