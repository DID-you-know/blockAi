<template>
  <div class="container">
    <h1>업로드할 파일</h1>
    <input type="file" id="img" />
    <button
      type="button"
      class="btn btn-warning makeCenter"
      @click="uploadImage"
    >
      업로드
    </button>
  </div>
</template>

<script>
export default {
  components: {},
  methods: {
    uploadImage() {
      //   var file = $("#img")[0].files[0];
      //   var formData = new FormData();
      //   formData.append("data", file);
      //   this.$store.state.image.uploadImage;
      alert("업로드!");
    },
  },
};
</script>

<style>
.makeCenter {
  display: flex;
  justify-items: center;
  align-items: center;
}
</style>
