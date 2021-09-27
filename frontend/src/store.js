import { createStore } from 'vuex';

export default createStore({
  state: {
    user: null,
  },
  // this.$store.commit('mutationName')
  mutations: {
    setUser(state, user) {
      state.user = user
    },
  },
  actions: {
    async whoAmI(store) {
      let res = await fetch('/api/whoami')
      let user = await res.json()

      store.commit('setUser', user)
    },

    async register(store, credentials) {
      let res = await fetch('/api/register', {
        method: 'POST',
        body: JSON.stringify(credentials)
      })

      let loggedInUser = await res.json()

      if (loggedInUser.error) {
        store.commit('setUser', null)
        return
      }

      store.commit('setUser', loggedInUser)
    },

    async login(store, credentials) {
      let res = await fetch('/api/login', {
        method: 'POST',
        body: JSON.stringify(credentials)
      })

      let loggedInUser = await res.json()

      if (loggedInUser.error) {
        store.commit('setUser', null)
        return
      }
      store.commit('setUser', loggedInUser)
    },
    
    async logout(store) {
      let res = await fetch('/api/logout')

      // remove user from state
      store.commit('setUser', null)
    },
  },
});
