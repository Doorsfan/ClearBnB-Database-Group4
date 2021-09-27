<template>
  <Header />
  <main class="app-main">
    <router-view />
  </main>
</template>
<script>

import Header from "./components/Header.vue"

export default {
  name: 'App',
  components: {
    Header,
  },
  data() {
    return {
    };
  },
  methods: {
  },
};
</script>
<style scoped></style>
