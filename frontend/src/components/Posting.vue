<template>
  <div class="leaseBox">
    <router-link :to="{ path: '/ListingPage', query: { listingId: Listing.listingId, postedByUsername: Listing.postedByUsername, title: Listing.title, description: Listing.description, imageUrl: Listing.imageUrl, location: Listing.location, numberGuests: Listing.numberGuests, price: Listing.price, listingStartDate: Listing.listingStartDate, listingEndDate: Listing.listingEndDate } 
    }">
      <div class="cellBox firstCellBox">{{ title }}</div>
      <div class="cellBox">{{ description }}</div>
      <div class="cellBox">{{ imageUrl }}</div>
      <div class="cellBox">{{ location }}</div>
      <div class="cellBox">{{ numberGuests }}</div>
      <div class="cellBox">{{ displayPrice }}</div>
      <div class="cellBox">{{ startDate }}</div>
      <div class="cellBox">{{ endDate }}</div>
      <div class="cellBox">{{ host }}</div>
    </router-link>
  </div>
</template>
<script>
export default {
  props: ['Listing'],
  data() {
    return {
      title: this.Listing.title,
      description: this.Listing.description,
      imageUrl: this.Listing.imageUrl,
      location: this.Listing.location,
      numberGuests: this.Listing.numberGuests,
      price: this.Listing.price,
      displayPrice: Math.round(this.Listing.price * 1.15),
      startDate: this.Listing.listingStartDate,
      endDate: this.Listing.listingEndDate,
      host: this.Listing.postedByUsername
    };
  },
  methods: {
  },
  mounted() {
    this.title = this.Listing.title;
    this.description = this.Listing.description;
    this.imageUrl = this.Listing.imageUrl;
    this.location = this.Listing.location;
    this.numberGuests = this.Listing.numberGuests;
    this.price = this.Listing.price;
    this.displayPrice = Math.round(this.Listing.price * 1.15);
    this.startDate = this.Listing.listingStartDate;
    this.endDate = this.Listing.listingEndDate;
    this.host = this.Listing.postedByUsername;
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
}
.firstCellBox{
  margin-left: 100px;
}
</style>
