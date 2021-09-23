<template>
  <div class="mainDiv">
    <div class="navbar">
      <div class="backToStartPage">
        <router-link to="/">ClearBNB</router-link>
      </div>
    </div>
    <div class="myProfileBox centerBox">My Profile</div>
    <div class="myProfileInfoBoxUsername centerBox subBox1">Username: {{ username }}</div>
    <div class="myProfileInfoBoxBalance centerBox subBox2">Balance: TEMP</div>
    <router-link to="/leaseAHouse" class="centerBox subBox3">Add a Lease</router-link>
    <p class="myLeasesP">My Leases</p>
    <div class="myProfileInfoBoxLinks centerBox subBox4">
      <p class="tempLeaseList">MY LEASE 1 IN LIST</p>
      <p class="tempLeaseList">MY LEASE 2 IN LIST</p>
      <p class="tempLeaseList">MY LEASE 3 IN LIST</p>
      <p class="tempLeaseList">MY LEASE 4 IN LIST</p>
    </div>
  </div>
</template>
<script>

export default {
  components: {

  },
  data() {
    return {
      username: this.$route.params.username
    };
  },
  async mounted() {
    console.log(this.username);
  },
  methods: {
  },
};
</script>
<style scoped>
.mainDiv{
  height:100vh;
  background-color:lightcyan;
}
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
  top: 32%;
  left:47%;
  font-weight:bolder;
  font-size:30px;
}
.subBox1{
  position:absolute;
  top: 36%;
  left: 47.2%;
}
.subBox2{
  position:absolute;
  top: 38%;
  left: 47.2%;
}
.subBox3{
  position:absolute;
  top: 40%;
  left: 48%;
  color:blue;
  text-decoration:underline;
}
.subBox4{
  position:absolute;
  top: 45%;
  left: 46.5%;
}
.myLeasesP{
  position:absolute;
  top: 40%;
  left: 47.5%;
  font-weight:bolder;
  font-size:25px;
}

</style>
