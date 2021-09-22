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
      <div class="hostedByTitle">
        <div class="inline">Hosted by: </div>
        <router-link id="postedByLink" :to="{ name: 'profile', params: { username: postedByUsername
        }}">{{ postedByUsername }}</router-link>
      </div>
      <div class="descriptionText">
        <div class="inline">Description: </div>
        {{ myDescription }}
      </div>
      <div class="imageURLText">
        <img src='' id="imageOfTheHouse" width="300" height="300">
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
        Version - 
        <select @change="updateInfoBasedOnVersion" class="myGuests" v-model="wantedVersion">
          <option v-for="(version, index) of versions" class="versionOption"
        :key="index"
        :version="version">{{ index + 1}}</option>
        </select>
      </div>
      <div class="BookingDateDiv">
      Book from the:
        <input v-model="wantedStartDate" type="date" class="bookingStartsDateElement">
        Book until the:
        <input v-model="wantedEndDate" type="date" class="bookingEndDateElement">
      </div>
      <button @click="tryToBook" class="bookButton" type="button" value="Book">Book</button>
    </div>
</template>
<script>
import store from '../store.js';
export default {
  components: {},
  data() {
    return {
      wantedStartDate: new Date(this.myStartDate),
      wantedEndDate: new Date(this.myEndDate),
      wantedVersion: 1.0,
      versions: [],
      postedByUsername: this.$route.query.postedByUsername,
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
    //Query the DB for Versions on this point, to get them
    this.versions = ['1.0', '2.0']
    console.log(this.myEndDate);
    document.getElementById('imageOfTheHouse').src = 'https://i2.wp.com/samhouseplans.com/wp-content/uploads/2021/01/Small-House-Plans-6.5x6-Meter-1.jpg?fit=1920%2C1080&ssl=1';
    document.getElementsByClassName('bookingStartsDateElement')[0].min = this.myStartDate;
    document.getElementsByClassName('bookingStartsDateElement')[0].max = this.myEndDate;

    document.getElementsByClassName('bookingEndDateElement')[0].min = this.myStartDate;
    document.getElementsByClassName('bookingEndDateElement')[0].max = this.myEndDate;

    document.getElementById("postedByLink").to = this.postedByUsername;
  },
  methods: {
    updateInfoBasedOnVersion() {
      //Update the State variables in terms of dynamically reflecting the data
      console.log(this.wantedVersion);
      //Based on this Version, query the DB in terms of finding the respective
      //version of it from the DB
    },
    tryToBook() {
      //Implement so queries can be made and actually perform the real booking
    }
  },
};
</script>
<style scoped>
.bookButton{
  display:block;
  margin-left:auto;
  margin-right:auto;
  width:max-content;
  margin-top: 20px;
}
.BookingDateDiv{
  display:block;
  margin-left:auto;
  margin-right:auto;
  width:max-content;
}
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