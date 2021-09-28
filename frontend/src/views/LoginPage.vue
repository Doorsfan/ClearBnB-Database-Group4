<template>
  <div class="mainDiv">
    <div class="navbar">
      <router-link class="clearBnBlogo" to="/">ClearBnB</router-link>
    </div>
    <div class="loginForm">
      <p class="usernameText">Username</p>
      <p class="passwordText">Password</p>
      <form @submit.prevent="tryToLogIn" class="inputForm">
        <input class="usernameInput" type="text" placeholder="Username" />
        <input class="passwordInput" type="password" placeholder="Password" />
        <input class="submitButton" type="submit" value="Log In" />
      </form>
    </div>
    <router-link to="/register" class="registerLink"
      >Register New Account</router-link
    >
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
    tryToLogIn() {
      console.log("hi");
    },
  },
};
</script>
<style scoped>
.mainDiv {
  height: 98vh;
  background-color: lightcyan;
}
a {
  text-decoration: none;
}
.clearBnBlogo {
  color: black;
  font-size: 25px;
  position: absolute;
  left: 30px;
  top: 25px;
}
.navbar {
  background-color: lightcoral;
  width: 99vw;
  height: 7vh;
}
.inputForm {
  position: absolute;
  left: 45vw;
  top: 40vh;
}
.usernameInput {
  width: max-content;
  display: block;
  margin-bottom: 50px;
}
.passwordInput {
  width: max-content;
  display: block;
  margin-bottom: 20px;
}
.usernameText {
  position: absolute;
  top: 36vh;
  left: 47.5vw;
}
.passwordText {
  position: absolute;
  top: 43vh;
  left: 47.5vw;
}
.submitButton {
  margin-left: 55px;
}
.registerLink {
  position: absolute;
  top: 54vh;
  left: 45.6vw;
  color: blue;
}
</style>