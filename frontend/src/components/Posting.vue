<template>
  <div class="leaseBox">
    <router-link :to="{ path: '/ListingPage', query: { postedByUsername: Listing.postedByUsername, title: Listing.title, description: Listing.description, image_url: Listing.image_url, location: Listing.location, number_guests: Listing.number_guests, price: Listing.price, listing_start_date: Listing.listing_start_date, listing_end_date: Listing.listing_end_date } }">
      <div class="cellBox firstCellBox">{{ Listing.title }}</div>
      <div class="cellBox">{{ Listing.description }}</div>
      <div class="cellBox">{{ Listing.image_url }}</div>
      <div class="cellBox">{{ Listing.location }}</div>
      <div class="cellBox">{{ Listing.number_guests }}</div>
      <div class="cellBox">{{ Listing.price }}</div>
      <div class="cellBox">{{ Listing.listing_start_date }}</div>
      <div class="cellBox">{{ Listing.listing_end_date }}</div>
      <div class="cellBox">{{ Listing.postedByUsername }}</div>
    </router-link>
  </div>
</template>
<script>
export default {
  props: ['Listing'],
  data() {
    return {};
  },
  methods: {
  },
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
