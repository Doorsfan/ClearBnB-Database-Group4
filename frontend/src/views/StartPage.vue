<template>
  <div class="mainDiv">
    <div class="navbar">
      <div class="locationBox Box">
        Location
        <div class="locationBar">
          <input type="text" placeholder="Location" class="searchBar" />
        </div>
      </div>
      <div class="guestBox Box">
        Guests
        <div class="guestsBar">
          <input type="number" min="1" placeholder="Guests" class="guestsBar" />
        </div>
      </div>
      <div class="startDateBox Box">
        Start Date
        <div class="startDateBar"><input min="2021-09-17" type="date" /></div>
      </div>
      <div class="endDateBox Box">
        End Date
        <div class="endDateBar">
          <input type="date" min="2021-09-17" max="2021-10-17" />
        </div>
      </div>
      <div class="priceBox Box">
        Below Price Of
        <div class="priceBar">
          <input placeholder="0" type="number" min="0" />
        </div>
      </div>
      <button class="searchButton" @click="search" type="button">Search</button>
      <router-link class="Login" to="/login">Login</router-link>
      <router-link class="myProfileBox" to="/profile">My Profile</router-link>
    </div>
    <div class="searchDiv"></div>
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
    search() {},
  },
};
</script>
<style scoped>
.Box {
  max-width: max-content;
  display: inline-block;
  padding: 20px;
}
.navbar {
  max-width: max-content;
  margin-left: auto;
  margin-right: auto;
}
.mainDiv {
  background-color: lightcoral;
}
a:visited {
  color: blue;
}
.searchButton {
  margin-right: 100px;
}
.myProfileBox {
  margin-left: 50px;
}
.searchDiv {
  background-color: lightcyan;
  height: 100vh;
}
</style>
