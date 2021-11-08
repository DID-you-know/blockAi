import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";
import user from "./modules/user";
import image from "./modules/image";

const store = createStore({
  plugins: [createPersistedState()],
  modules: {
    user,
    image,
  },
});

export default store;
