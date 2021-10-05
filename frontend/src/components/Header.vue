<template>
  <!-- a -->
  <header>
    <nav class="container">
      <ul>
        <li>
          <a class="logo">
            <h1 @click="goToHomePage">ClearBnB</h1>
          </a>
        </li>
        <li v-if="!$store.state.user"></li>
        <li v-else>
          <a class="login-menu-item" @click="goToProfilePage">My profile</a>
        </li>
        <li v-if="!$store.state.user">
          <a class="login-menu-item" @click="goToLoginPage">Login</a>
        </li>
        <li v-else>
          <a class="login-menu-item" @click="logout">Logout</a>
        </li>
      </ul>
    </nav>
  </header>
</template>

<script>
export default {
  async beforeCreate() {
    await this.$store.dispatch("whoAmI");
  },

  data() {
    return {};
  },

  methods: {
    goToHomePage() {
      this.$router.push("/");
    },
    goToLoginPage() {
      this.$router.push("/login");
    },
    async goToProfilePage() {
      await this.$router.push("/profile/" + this.$store.getters.user.username);
      window.location.reload(false); // fixed a redering issue when going from one profile to the next
                                     // not a great solution, but it works for the time-being
    },
    logout() {
      this.$store.dispatch("logout");
      this.$router.push("/");
    },
  },
};
</script>

<style scoped>
.container {
  background-color: lightcoral;
}

ul {
  margin: 0 40px;
  list-style: none;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  cursor: pointer;
}

.login-menu-item {
  cursor: pointer;
}
</style>