<template>
  <div id="app">
    <router-view/>
    <Alert/>
  </div>
</template>

<script>
import '@/assets/style/index.scss'
import Alert from '@/components/Alert'


export default {
  name: 'App',
  components: {
    Alert
  }
}
</script>