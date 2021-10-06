<template>
  <div class="container">
    <h1>List of Users Connected</h1>
    <ul>
      <li v-for="username in usernames" :key="username">
        <a v-on:click="pushUser(username)">{{username}}</a>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  async beforeCreate() {
    if (!this.$store.state.user) {
      await this.$store.dispatch("whoAmI")
    }
    if (!this.$store.state.user || this.$store.state.user.username !== "support") {
      this.$router.push("/")
    }
    let res = await fetch('/api/usersInChat')
    this.usernames = await res.json()
  },

  data() {
    return {
      usernames: [],
    };
  },

  methods: {
    pushUser(username) {
      this.$router.push("/supportchat/" + username)
    }
  },
};
</script>

<style scoped>
a {
  cursor: pointer;
}
</style>