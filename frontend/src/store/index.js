import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";
import image from "./modules/image";

const store = createStore({
  plugins: [createPersistedState()],
  modules: {
    image,
  },
});

export default store;
