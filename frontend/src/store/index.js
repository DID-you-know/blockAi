import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import users from './modules/users'
import certification from './modules/certification'
import alert from './modules/alert'


const store = createStore({
  modules: {
    users,
    certification,
    alert
  },
  plugins: [createPersistedState({
    key: 'blockAi',
    paths: ['users']
  })]
})

export default store
