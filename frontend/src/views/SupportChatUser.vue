<template>
  <div class="container">
    <ul>
      <li v-for="message in previousMessages" :key="message.messageId">
        <a>{{message.writtenByUser.username}} ({{extractMessageDate(message)}}): {{message.content}}</a>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  async beforeCreate() {
    let res = await fetch('/rest/getAllMessagesForUser/' + this.$route.params.username)
    this.previousMessages = await res.json()
    console.log(this.previousMessages)
  },

  data() {
    return {
      previousMessages: [],
    };
  },

  methods: {
    extractMessageDate(msg) {
      let date = new Date(msg.timestamp[0], msg.timestamp[1] - 1, msg.timestamp[2], msg.timestamp[3], msg.timestamp[4], msg.timestamp[5]);
      return date.toLocaleString();
    }
  },
};
</script>

<style scoped>
</style>