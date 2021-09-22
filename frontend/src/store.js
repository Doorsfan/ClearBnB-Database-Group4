import { createStore } from 'vuex';

export default createStore({
  state: {
    users: [],
  },
  // this.$store.commit('mutationName')
  mutations: {
    setUsers(state, users) {
      state.users = users
    },
  },
  getters: {
  },
  actions: {
    async fetchUsers(store) {
      let res = await fetch('/rest/getAllUsers')
      let users = await res.json()
      // setListing runs setListing in mutations
      store.commit('setUsers', users)
    },
  },
});
