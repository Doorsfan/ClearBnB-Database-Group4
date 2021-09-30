<template>
  <div class="innerWindow">
    <div id="chat-output"></div>
    <input type="text" placeholder="Write your message here.." id="chat-input">
    <button id="chat-send" v-on:click="send">send</button>
    <button id="connect" v-on:click="connect">connect</button>
    <button id="disconnect" v-on:click="disconnect">disconnect</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      socket: null,
      div: null,
      input: null,
    };
  },

  methods: {
    addMsg(s) {
      this.div.insertAdjacentHTML('beforeend', `<p>${s}</p>`);
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
      this.div = document.querySelector("#chat-output")
      this.input = document.querySelector("#chat-input")

      console.log('Connecting...');
      this.addMsg('Connecting...');
      this.socket = new WebSocket("ws://localhost:4000/websocket");
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
.innerWindow {
  height: 190px;
  width: 290px;
  background-color:white;
  margin: 5px;
}
#chat-input {
  position:absolute;
  bottom: 32px;
  right: 8px;
  width: 275px;
}
#chat-send {
  position:absolute;
  bottom: 8px;
  left: 10px;
}
#connect {
  position:absolute;
  bottom: 8px;
  left: 58px;
}
#disconnect {
  position:absolute;
  bottom: 8px;
  left: 124px;
}
</style>