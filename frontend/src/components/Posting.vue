<template>
  <div class="leaseBox">
    <router-link :to="{ path: '/ListingPage', query: { listingId: Listing.listingId, postedByUsername: Listing.postedByUsername, title: Listing.title, description: Listing.description, image_url: Listing.image_url, location: Listing.location, number_guests: Listing.number_guests, price: Listing.price, listing_start_date: Listing.listing_start_date, listing_end_date: Listing.listing_end_date } }">
      <div class="cellBox firstCellBox">{{ title }}</div>
      <div class="cellBox">{{ description }}</div>
      <div class="cellBox">{{ image_url }}</div>
      <div class="cellBox">{{ location }}</div>
      <div class="cellBox">{{ number_guests }}</div>
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
      image_url: this.Listing.image_url,
      location: this.Listing.location,
      number_guests: this.Listing.number_guests,
      price: this.Listing.price,
      displayPrice: Math.round(this.Listing.price * 1.15),
      startDate: this.Listing.listing_start_date,
      endDate: this.Listing.listing_end_date,
      host: this.Listing.postedByUsername
    };
  },
  methods: {
  },
  mounted() {
    console.log(this.Listing);
  }
};
</script>
<style scoped>
.cellBox{
  background-color: lightcoral;
  width:10vw;
  display: inline-block;
  text-align:left;
  outline:solid 1px black;
  text-overflow:hidden;
  overflow-x:hidden;
  overflow-y:hidden;
  height: 20px;
}
.firstCellBox{
  margin-left: 100px;
}
</style>
