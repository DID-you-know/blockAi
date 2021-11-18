<template>
  <div>
    <Alert type="success">성공했습니다.</Alert>
    <image-rekognition></image-rekognition>
  </div>
</template>

<script>
import ImageRekognition from "../components/ImageRekognition.vue";
import Alert from '@/components/Alert'


export default {
  components: {
    ImageRekognition,
    Alert
  },
};
</script>

<style></style>
