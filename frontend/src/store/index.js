import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import user from './modules/user'


const store = createStore({
  plugins: [createPersistedState()],
  modules: {
    user
  }
})

export default store