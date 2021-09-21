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
    <div class="chatWindow" v-if="chatOpened">
      <div class="innerWindow">
        <input type="text" placeholder="Write your message here.." class="myMessageToSupport">
      </div>
    </div>
    <footer class="bottomFooter">
      <div @click="openSupportChat" class="chatTab">
        Chat With Support
      </div>
    </footer>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      chatOpened: false
    };
  },
  async mounted() {},
  methods: {
    search() {},
    openSupportChat() {
      if(this.chatOpened){
        this.chatOpened = false;
      }
      else if(!this.chatOpened){
        this.chatOpened = true;
      }
    }
  },
};
</script>
<style scoped>
.myMessageToSupport{
  position:absolute;
  bottom: 8px;
  right: 8px;
  width: 275px;
}
.innerWindow{
  height: 190px;
  width: 290px;
  background-color:white;
  margin: 5px;
}
.chatWindow{
  height: 200px;
  width: 300px;
  background-color:red;
  border:lightcoral solid 1px;
  z-index: 3;
  position:absolute;
  right: 20px;
  top:89.5vh;
}
.chatTab{
  position:absolute;
  right: 20px;
  padding-top: 15px;
  padding-right: 15px;
  font-weight:bolder;
  font-size: 20px;
}
.bottomFooter {
  height: 50px;
}
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
