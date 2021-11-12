import users from '@/api/users'


const state = {
  accessToken: null,
  isLogin: false,
  userId: null,
  name: null,
  isIssued: false,
  issuedDate: null
}

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
  // async getUserInfo({ commit }) {

  // }
}

const mutations = {
  SET_ACCESS_TOKEN(state, accessToken) {
    state.accessToken = accessToken
  },
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