<template>
  <div class="mainDiv">
    <form @submit.prevent="tryToRegisterUser" class="registerForm">
      <div class="registerTitle">Register a new User</div>
      <div class="usernameTitle">Username</div>
      <input v-model="wantedUsername" type="text" class="nameInput inputField" placeholder="Username">
      <div class="passwordTitle">Password</div>
      <input v-model="wantedPassword" type="password" class="passwordInput inputField" placeholder="Password">
      <div class="repeatPasswordTitle">Repeat Password</div>
      <input v-model="repeatedPassword" type="password" class="repeatPasswordInput inputField" placeholder="Repeat Password">
      <div class="emailTitle">Email</div>
      <input v-model="wantedEmail" type="type" class="emailInput inputField" placeholder="E-mail">
      <div class="balanceTitle">Balance</div>
      <input v-model="availableFunds" type="number" min=0 class="balanceInput inputField" placeholder="Balance">
      <button type="submit" class="submitButton inputField" value="Register">Register</button>
    </form>
  </div>
</template>
<script>
import User from '../jsClasses/User.js';
export default {
  components: {

  },
  data() {
    return {
      wantedUsername: '',
      wantedPassword: '',
      repeatedPassword: '',
      wantedEmail: '',
      availableFunds: 0
    };
  },
  async mounted() {

  },
  methods: {
    async tryToRegisterUser(){
      if (this.wantedPassword !== this.repeatedPassword) {
        alert("Passwords do not match")
        return
      }

      let wantedUser = {
        username: this.wantedUsername, 
        password: this.wantedPassword, 
        email: this.wantedEmail, 
        balance: this.availableFunds
      }

      await this.$store.dispatch("register", wantedUser)

      if (this.$store.state.user) {
        this.$router.push("/");
      } else {
        alert("Username or email has aleady been used")
      }
    }
  },
};
</script>
<style scoped>
.inputField{
  display:block;
  margin-left:auto;
  margin-right:auto;
  margin-top: 20px;
}
.registerTitle{
  display:block;
  margin-left:auto;
  margin-right:auto;
  width:max-content;
  font-size:30px;
  font-weight:bolder;
}
.usernameTitle, .emailTitle, .repeatPasswordTitle, .passwordTitle, .balanceTitle{
  display:block;
  margin-left:auto;
  margin-right:auto;
  width:max-content;
  font-size:20px;
  font-weight:bolder;
  margin-top: 15px;
}
.registerForm{
  margin-top: 20vh;
}
.navBar{
  height:25px;
  background-color:lightcoral;
  padding-top: 15px;
  padding-bottom: 15px;
}
a {
  color:black;
  text-decoration:none;
  font-weight:bolder;
  font-size: 25px;
  margin-left: 10px;
}
.mainDiv{
  background-color:lightcyan;
  height: 98vh;
}
</style>
