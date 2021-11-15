import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import users from './modules/users'
import certification from './modules/certification'
// import image from './modules/image'

const store = createStore({
  modules: {
    users,
    certification,
    // image,
  },
  plugins: [createPersistedState({
    key: 'blockAi'
  })]
})

export default store
