<template>
  <div class="container">
    <ul id="chat-output">
      <li v-for="message in previousMessages" :key="message.messageId">
        <a>{{message.writtenByUser.username}} ({{extractMessageDate(message)}}): {{message.content}}</a>
      </li>
    </ul>
    <input type="text" placeholder="message.." id="chat-input">
    <button id="chat-send" v-on:click="send">send</button>
    <button id="connect" v-on:click="connect">connect</button>
    <button id="disconnect" v-on:click="disconnect">disconnect</button>
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
      socket: null,
      ul: null,
      input: null,
    };
  },

  methods: {
    extractMessageDate(msg) {
      let date = new Date(msg.timestamp[0], msg.timestamp[1] - 1, msg.timestamp[2], msg.timestamp[3], msg.timestamp[4], msg.timestamp[5]);
      return date.toLocaleString();
    },
    addMsg(s) {
      this.ul.insertAdjacentHTML('beforeend', `<li><a>${s}</a></li>`);
    },
    addSocketEventListeners() {
      this.socket.onclose = event => {
        console.warn('Disconnected:', event);
        this.addMsg('Disconnected: ' + JSON.stringify(event));
      };

      this.socket.onerror = event => {
        console.error('Error:', event);
        this.addMsg('Error: ' + JSON.stringify(event));
      };

      this.socket.onmessage = event => {
        console.log('Message from server:', event.data);
        let msg = JSON.parse(event.data);
        let date = new Date(msg.timestamp[0], msg.timestamp[1] - 1, msg.timestamp[2], msg.timestamp[3], msg.timestamp[4], msg.timestamp[5]);
        this.addMsg(msg.writtenByUser.username + ' (' + date.toLocaleString() + '): ' + msg.content);
      };

      this.socket.onopen = event => {
        console.warn('Connected:', event);
        this.addMsg('Connected: ' + JSON.stringify(event));
      };
    },
    connect() {
      //if (client) return;
      this.ul = document.querySelector("#chat-output")
      this.input = document.querySelector("#chat-input")

      console.log('Connecting...');
      this.addMsg('Connecting...');
      this.socket = new WebSocket("ws://localhost:4000/websocket/" + this.$route.params.username);
      this.addSocketEventListeners();
    },
    disconnect() {
      //if (!client) return;

      console.log('Disconnecting...');
      this.addMsg('Disconnecting...');
      this.socket.close();
    },
    send() {
      const msg = this.input.value;
      this.input.value = '';
      console.log('Sending:', msg);
      this.socket.send(JSON.stringify({ writtenByUser: this.$store.state.user, content: msg, timestamp: new Date().toISOString()}));
      // addMsg(msg); // if locally rendered instead of reliably pushed from server
    },
  },
};
</script>

<style scoped>
</style>