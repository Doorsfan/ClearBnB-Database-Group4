<template>
  <div class="leaseBox">
    <router-link :to="{ path: '/ListingPage', query: { listingId: myListing.listingId, postedByUsername: myListing.owner.username, title: myListing.title, description: myListing.description, imageUrl: myListing.imageUrl, location: myListing.location, numberGuests: myListing.numberGuests, price: myListing.price, listingStartDate: myListing.listingStartDate, listingEndDate: myListing.listingEndDate } }">
      <div class="cellBox">{{ myListing.title }}</div>
      <div class="cellBox">{{ myListing.description }}</div>
      <div class="cellBox">{{ myListing.price }}</div>
      <div class="cellBox">{{ myListing.numberGuests }}</div>
      <div class="cellBox">{{ myListing.location }}</div>
      <div class="cellBox">{{ myListing.listingStartDate }}</div>
      <div class="cellBox">{{ myListing.listingEndDate }}</div>
    </router-link>
  </div>
</template>
<script>
export default {
  props: ['myListing'],
  data() {
    return {
      listingId: this.myListing.listingId,
      postedByUsername: this.myListing.owner.username,
      title: this.myListing.title,
      description: this.myListing.description,
      imageUrl: this.myListing.imageUrl,
      location: this.myListing.location,
      numberGuests: this.myListing.numberGuests,
      price: this.myListing.price,
      listingStartDate: this.myListing.listingStartDate,
      listingEndDate: this.myListing.listingEndDate,
    };
  },
  methods: {
  },
  mounted() {
    console.log(this.myListing);


  }
};
</script>
<style scoped>
a, a:visited {
  color: white;
  font-size: 20px;
  font-weight: bold;
}
.cellBox{
  background-color: darkcyan;
  width:max-content;
  padding-bottom: 3px;
  max-width:10vw;
  min-width:10vw;
  display: inline-block;
  text-align:left;
  outline:solid 1px black;
  text-overflow:hidden;
  overflow-x:hidden;
  overflow-y:hidden;
  height: 20px;
  margin: 3px;
  text-align: center;
  color:white;
  font-weight:bolder;
  padding-top: 3px;
}
.leaseBox{
  width:max-content;
  display:inline-block;
  margin-left:auto;
  margin-right:auto;
}
</style>
