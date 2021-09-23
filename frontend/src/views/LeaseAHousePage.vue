<template>
  <div class="mainDiv">
    <form @submit.prevent="tryToPostLease">
      <div class="navbar">
        <router-link class="clearBnBlogo" to="/">ClearBnB</router-link>
      </div>
      <div class="centerBox">
        <div class="myTitleTitle centerBox">Title</div>
        <input v-model="wantedListingTitle" type="text" placeholder="My Title">
      </div>
      <div class="centerBox">
        <div class="ImageURL centerBox">ImageURL of the Lease</div>
        <input v-model="wantedImageURL" type="text" placeholder="Image URL">
      </div>
      <div class="centerBox">
        <div class="Description centerBox">Description</div>
        <textarea v-model="wantedDescription" placeholder="Description"></textarea>
      </div>
      <div class="centerBox">
        <div class="Location centerBox">Location</div>
        <input v-model="wantedLocation" type="text" placeholder="Location">
      </div>
      <div class="centerBox">
        <div class="nrOfGuests centerBox">Nr of Guests</div>
        <input v-model="wantedNumberOfGuests" type="number" min=1 placeholder="Guests">
      </div>
      <div class="centerBox">
        <div class="price centerBox">Price/Night</div>
        <input v-model="wantedPrice" type="number" min=1 placeholder="Pricing">
      </div>
      <div class="centerBox">
        <div class="leaseStartDate centerBox">Available From</div>
        <input v-model="wantedListingStartDate" min="2021-09-21" type="date" />
      </div>
      <div class="leaseEndDate exceptionCenterBox">Stops Being Available From</div>
      <div class="centerBox noPaddingCenterBox">
        <input v-model="wantedListingEndDate" min="2021-09-21" type="date" />
      </div>
      <button class="centeredButton" type="submit" value="Submit">Submit</button>
    </form>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      wantedListingTitle: '',
      wantedDescription: '',
      wantedImageURL: '',
      wantedLocation: '',
      wantedNumberOfGuests: '',
      wantedPrice: '',
      wantedListingStartDate: '',
      wantedListingEndDate: ''
    };
  },
  async mounted() {},
  methods: {
    async tryToPostLease() {
      let baseDate = new Date(this.wantedListingStartDate);
      let endDate = new Date(this.wantedListingEndDate);
      let wantedListing = {
        title: this.wantedListingTitle,
        description: this.wantedDescription,
        imageUrl: this.wantedImageURL,
        location: this.wantedLocation,
        numberGuests: this.wantedNumberOfGuests,
        price: this.wantedPrice,
        listingStartDate: this.wantedListingStartDate,
        listingEndDate: this.wantedListingEndDate
      }
      let res = await fetch('http://localhost:4000/listing', {
        method: 'POST',
        mode: 'cors',
        credentials: 'include',
        body: JSON.stringify(wantedListing),
      }).then(function(response){
        return response.json();
      }).then(function(data){
        console.log(data);
      });
    },
  },
};
</script>
<style scoped>
.navbar{
  height:75px;
  background-color: lightcoral;
}
.clearBnBlogo{
  position:absolute;
  top: 25px;
  left: 30px;
  text-decoration: none;
  color:black;
  font-weight:bolder;
  font-size:30px;
}
.centerBox{
  margin-left:auto;
  margin-right:auto;
  width:max-content;
  display:block;
  padding-top:20px;
}
.exceptionCenterBox{
  margin-left:auto;
  margin-right:auto;
  width:max-content;
  display:block;
  padding-top:30px;
}
.noPaddingCenterBox{
  padding:0px;
}
.mainDiv{
  background-color:lightcyan;
  height:98vh;
}
.centeredButton{
  margin-left:auto;
  margin-right:auto;
  display:block;
  margin-top: 30px;
}
</style>