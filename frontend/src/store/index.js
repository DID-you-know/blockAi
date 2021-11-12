import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import users from './modules/users'
import certification from './modules/certification'
import image from './modules/image'

const store = createStore({
  plugins: [createPersistedState()],
  modules: {
    users,
    certification,
    image,
  },
})

export default store
