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
    let res = await fetch('/rest/usersInChat')
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