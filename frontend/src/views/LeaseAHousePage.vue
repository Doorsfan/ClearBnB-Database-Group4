<template>
  <div class="mainDiv">
    <form @submit.prevent="tryToPostLease">
      <div class="navbar">
        <router-link class="clearBnBlogo" to="/">ClearBnB</router-link>
      </div>
      <div class="centerBox">
        <div class="myTitleTitle centerBox">Title</div>
        <input type="text" placeholder="My Title">
      </div>
      <div class="centerBox">
        <div class="ImageURL centerBox">ImageURL of the Lease</div>
        <input type="text" placeholder="Image URL">
      </div>
      <div class="centerBox">
        <div class="Description centerBox">Description</div>
        <textarea placeholder="Description"></textarea>
      </div>
      <div class="centerBox">
        <div class="Location centerBox">Location</div>
        <input type="text" placeholder="Location">
      </div>
      <div class="centerBox">
        <div class="nrOfGuests centerBox">Nr of Guests</div>
        <input type="number" min=1 placeholder="Guests">
      </div>
      <div class="centerBox">
        <div class="price centerBox">Price/Night</div>
        <input type="number" min=1 placeholder="Pricing">
      </div>
      <div class="centerBox">
        <div class="leaseStartDate centerBox">Available From</div>
        <input min="2021-09-21" type="date" />
      </div>
      <div class="leaseEndDate exceptionCenterBox">Stops Being Available From</div>
      <div class="centerBox noPaddingCenterBox">
        <input min="2021-09-21" type="date" />
      </div>
      <button class="centeredButton" type="submit" value="Submit">Submit</button>
    </form>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {};
  },
  async mounted() {},
  methods: {
    tryToPostLease() {
      console.log("hello");
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