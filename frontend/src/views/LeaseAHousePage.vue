<template>
  <div class="mainDiv">
    <div class="navbar">
      <router-link class="clearBnBlogo" to="/">ClearBnB</router-link>
    </div>
    <input type="text" placeholder="My Title">
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {};
  },
  async mounted() {},
  methods: {
    putUpHouseForLease() {},
  },
};
</script>
<style scoped>
.navbar{
  height:75px;
  background-color: lightcoral;
}
.clearBnBlogo{
  position:absolute;
  top: 25px;
  left: 30px;
  text-decoration: none;
  color:black;
  font-weight:bolder;
  font-size:30px;
}
</style>