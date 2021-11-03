import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import signup from './modules/signup'


const store = createStore({
  plugins: [createPersistedState()],
  modules: {
    signup
  }
})

export default store