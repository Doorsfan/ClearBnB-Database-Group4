import { createStore } from 'vuex';

export default createStore({
  state: {
    user: null,
    listingVersions: null
  },
  // this.$store.commit('mutationName')
  mutations: {
    setUser(state, user) {
      state.user = user
    },
    setListingVersions(state, listingVersions) {
      state.listingVersions = listingVersions
    }
  },
  getters: {
    user: state => {
      return state.user
    },
    listingVersions: state => {
      return state.listingVersions
    }
  },
  computed: {
    currentUser() {
      return store.state.user
    }
  },
  actions: {
    async whoAmI(store) {
      let res = await fetch('/api/whoami')
      console.log(res);
      let user = await res.json()
      console.log(user);
      if (user != "You are not logged in.") {
        store.commit('setUser', user);  
      }
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
    saveListings(store, listingVersions) {
      store.commit('setListingVersions', listingVersions)
    },
    async login(store, credentials) {
      let res = await fetch('/api/login?'
        +new URLSearchParams(credentials), {
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
