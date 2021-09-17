<template>
  <div class="mainDiv">
    <div class="navbar">
      <div class="backToStartPage">
        <router-link to="/">ClearBNB</router-link>
      </div>
    </div>
    <div class="myProfileBox centerBox">My Profile</div>
    <div class="myProfileInfoBoxUsername centerBox subBox1">Username: TEMP</div>
    <div class="myProfileInfoBoxBalance centerBox subBox2">Balance: TEMP</div>
  </div>
</template>
<script>

export default {
  components: {

  },
  data() {
    return {
    };
  },
  async mounted() {

  },
  methods: {
  },
};
</script>
<style scoped>
.backToStartPage{
  width: max-content;
  background-color:lightcoral;
  padding-left:1%;
  padding-right:1%;
  padding-top: 0.5%;
  padding-bottom:0.5%;
  font-weight:bold;
}
a {
  text-decoration: none;
  color:black;
}
.navbar{
  background-color:lightcoral;
}
.myProfileBox{
  position:absolute;
  top: 35%;
  left:47%;
  font-weight:bolder;
  font-size:30px;
}
.subBox1{
  position:absolute;
  top: 39%;
  left: 47.2%;
}
.subBox2{
  position:absolute;
  top: 41%;
  left: 47.2%;
}
</style>
