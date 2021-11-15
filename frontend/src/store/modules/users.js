import users from '@/api/users'


const state = () => ({
  accessToken: null,
  isLogin: false,
  userId: null,
  name: null,
  issuedDate: null,
})

const actions = {
  async getAccessToken({ commit }, credentials) {
    try {
      const response = await users.login(credentials)
      console.log(response.data)
      const accessToken = response.data.accessToken
      commit('SET_ACCESS_TOKEN', accessToken)
      const payload = {
        userId: response.data.userId,
        name: response.data.name,
        issuedDate: response.data.issued_at
      }
      commit('SET_USERINFO', payload)
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
    state.isLogin = true
  },
  SET_ISISSUED(state, payload) {
    state.isIssued = payload
  },
  SET_USERINFO(state, payload) {
    state.userId = payload.userId
    state.name = payload.name
    state.issuedDate = payload.issuedDate
  },
  LOGOUT(state) {
    state.accessToken = null
    state.isLogin = false
    state.userId = null
    state.name = null
    state.issuedDate = null
  }
}

const getters = {
  isIssued(state) {
    if (state.issuedDate) {
      return true
    } else {
      return false
    }
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}