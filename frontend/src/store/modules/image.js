import axios from "axios";

const state = {
  isUpload: false,
};

const actions = {
  uploadImage({ commit }, info) {
    axios({
      method: "post",
      url: `http://localhost:8080/` + "/image/upload",
    })
      .then(() => {
        console.log(info);
        commit("UPLOAD_IMAGE");
      })
      .catch((err) => {
        console.log(err);
        alert("다시 시도해주십시오.");
      });
  },
};

const mutations = {
  UPLOAD_IMAGE(state) {
    state.isUpload = true;
  },
};

export default {
  state,
  actions,
  mutations,
};
