import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";
import users from "./modules/users";
import image from "./modules/image";

const store = createStore({
  plugins: [createPersistedState()],
  modules: {
    users,
    image,
  },
});

export default store;
