<template>
  <div class="leaseBox">
    <div class="cellBox firstCellBox">{{ Listing.title }}</div>
    <div class="cellBox">{{ Listing.description }}</div>
    <div class="cellBox">{{ Listing.image_url }}</div>
    <div class="cellBox">{{ Listing.location }}</div>
    <div class="cellBox">{{ Listing.number_guests }}</div>
    <div class="cellBox">{{ Listing.price }}</div>
    <div class="cellBox">{{ Listing.listing_start_date }}</div>
    <div class="cellBox">{{ Listing.listing_end_date }}</div>
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
  text-overflow:clip;
}
.firstCellBox{
  margin-left: 100px;
}
</style>
