<template>
  <div class="mainDiv">
    <div class="navbar">
      <router-link to="/" class="clearBnBLogo">ClearBnB</router-link>
    </div>
    <div class="centralDiv">
      <div class="titleText">
        <div class="inline">Title: </div>
        {{ myTitle }}
      </div>
      <div class="descriptionText">
        <div class="inline">Description: </div>
        {{ myDescription }}
      </div>
      <div class="imageURLText">
        {{ myImageURL }}
      </div>
      <div class="locationText">
        <div class="inline">Location: </div>
        {{ myLocation }}
      </div>
      <div class="numberOfGuestsText">
        <div class="inline">Number of Guests: </div>
        {{ myNumberOfGuests }}
      </div>
      <div class="priceText">
        <div class="inline">Price: </div>
        {{ myPrice }}
      </div>
      <div class="startDateText">
        <div class="inline">Available from: </div>
        {{ myStartDate }}
      </div>
      <div class="endDateText">
        <div class="inline">Available until: </div>
        {{ myEndDate }}
      </div>
    </div>
  </div>
</template>
<script>
import store from '../store.js';
export default {
  components: {},
  data() {
    return {
      myTitle: this.$route.query.title,
      myDescription: this.$route.query.description,
      myImageURL: this.$route.query.image_url,
      myLocation: this.$route.query.location,
      myNumberOfGuests: this.$route.query.number_guests,
      myPrice: this.$route.query.price,
      myStartDate: this.$route.query.listing_start_date,
      myEndDate: this.$route.query.listing_end_date
    };
  },
  async mounted() {
    console.log(this.$route.query.description);
  },
  methods: {
    tryToPostLease() {
      console.log("hello");
    },
  },
};
</script>
<style scoped>
.mainDiv{
  background-color:lightcyan;
  height: 98vh;
}
.navbar{
  background-color:lightcoral;
  padding-top: 10px;
  padding-bottom: 10px;
}
.clearBnBLogo{
  text-decoration: none;
  font-size:25px;
  font-weight:bolder;
  margin-left: 10px;
  color:black;
}
.inline{
  display:inline-block;
}
.centralDiv{
  width:max-content;
  margin-left:auto;
  margin-right:auto;
  margin-top: 20vh;
}
</style>