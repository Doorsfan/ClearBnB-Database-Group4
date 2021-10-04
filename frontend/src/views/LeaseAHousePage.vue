<template>
  <div class="mainDiv">
    <form @submit.prevent="tryToPostLease">
      <div class="centerBox">
        <div class="myTitleTitle centerBox">Title</div>
        <input v-model="myTitle" type="text" placeholder="My Title">
      </div>
      <div class="centerBox">
        <div class="ImageURL centerBox">ImageURL of the Lease</div>
        <input v-model="myImageURL" type="text" placeholder="Image URL">
      </div>
      <div class="centerBox">
        <div class="Description centerBox">Description</div>
        <textarea v-model="myDescription" placeholder="Description"></textarea>
      </div>
      <div class="centerBox">
        <div class="Location centerBox">Location</div>
        <input v-model="myLocation" type="text" placeholder="Location">
      </div>
      <div class="centerBox">
        <div class="nrOfGuests centerBox">Nr of Guests</div>
        <input v-model="myGuests" type="number" min=1 placeholder="Guests">
      </div>
      <div class="centerBox">
        <div class="price centerBox">Price/Night</div>
        <input v-model="myPrice" type="number" min=1 placeholder="Pricing">
      </div>
      <div class="centerBox">
        <div class="leaseStartDate centerBox">Available From</div>
        <input v-model="myStartDate" :min="myStartDate" type="date" />
      </div>
      <div class="leaseEndDate exceptionCenterBox">Stops Being Available From</div>
      <div class="centerBox noPaddingCenterBox">
        <input v-model="myEndDate" :min="myStartDate" type="date" />
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
      myTitle: '',
      myImageURL: '',
      myDescription: '',
      myLocation: '',
      myGuests: 0,
      myPrice: 0,
      myStartDate: '',
      myEndDate: ''
    };
  },
  async mounted() {
    let myDate = new Date();
    let myYear = myDate.getFullYear();
    let myMonth = ((myDate.getMonth() + 1) < 10 ? '0' + (myDate.getMonth() + 1) : (myDate.getMonth() + 1));
    let myDay = (myDate.getDate() < 10 ? '0' + myDate.getDate() : myDate.getDate());
    console.log(myDate.getDate());
    this.myStartDate = myYear + '-' + myMonth + '-' + myDay;
  },
  methods: {
    async tryToPostLease() {
      let myUser = this.$store.getters.user;
      let now = new Date();
      let year = now.getFullYear();
      let month = (now.getMonth() + 1) < 10 ? ('0' + (now.getMonth() + 1)) : (now.getMonth() + 1);
      let date = now.getDate();

      let hour = now.getHours();
      let minute = now.getMinutes();

      let newLease = {
        title: this.myTitle,
        description: this.myDescription,
        imageUrl: this.myImageURL,
        location: this.myLocation,
        numberGuests: this.myGuests,
        price: this.myPrice,
        listingStartDate: this.myStartDate,
        listingEndDate: this.myEndDate,
        auditedDatetime: year + '-' + month + '-' + date + ' ' + hour + ':' + minute,
        version: 1,
        owner: myUser,
      }
      
      let res = await fetch('http://localhost:4000/makeANewLease', {
          method: 'POST',
          mode: 'cors',
          body: JSON.stringify(newLease),
        }).then((response) => {
            return response.json();
          }).then((data) => {
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