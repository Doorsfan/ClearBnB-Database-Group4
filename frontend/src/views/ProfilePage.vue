<template>
  <div class="mainDiv" :key="username">
    <div class="upperDiv">
      <div class="navbar"></div>
      <div class="myProfileBox centerBox">My Profile</div>
      <div class="myProfileInfoBoxUsername centerBox subBox1">Username: {{ username }}</div>
      <div v-if="samePerson" class="myProfileInfoBoxBalance centerBox subBox2">Balance: {{ loggedInUser.balance }}</div>
      <router-link to="/leaseAHouse" class="centerBox subBox3">Add a Lease</router-link>
      <p v-if="myListings.length > 0" class="myLeasesP">My Leases</p>
      <div v-if="myListings.length > 0" class="divOfListings">
        <div class="cellBox">Title</div>
        <div class="cellBox">Description</div>
        <div class="cellBox">Price</div>
        <div class="cellBox">Nr Guests</div>
        <div class="cellBox">City</div>
        <div class="cellBox">Start Date</div>
        <div class="cellBox">End Date</div>
        <ProfilePageListing v-for ="(listing, index) of  myListings" :key="index" :myListing="listing" />
      </div>
    </div>
    
    <div class="reviewsDiv">
      <UserReview
          v-for="(listItem, index) of reviewsFromDatabase"
          :key="index"
          :Content="listItem"
        />
        <div v-if="loggedInUser && !samePerson" class="newReviewDivBox">
          <form @submit.prevent="tryToPostReview" class="reviewForm">
            <span v-if="wantedAmountOfStars >= 1" @click="setToOneStar" class="starRating oneStar">&#11088;</span>
            <span v-if="wantedAmountOfStars >= 2" @click="setToTwoStars" class="starRating twoStars">&#11088;</span>
            <span v-if="wantedAmountOfStars >= 3" @click="setToThreeStars" class="starRating threeStars">&#11088;</span>
            <span v-if="wantedAmountOfStars >= 4" @click="setToFourStars" class="starRating fourStars">&#11088;</span>
            <span v-if="wantedAmountOfStars >= 5" @click="setToFiveStars" class="starRating fiveStars">&#11088;</span>
            <span @click="setToTwoStars" v-if="wantedAmountOfStars < 2" class="fa fa-star"></span>
            <span @click="setToThreeStars" v-if="wantedAmountOfStars < 3" class="fa fa-star"></span>
            <span @click="setToFourStars" v-if="wantedAmountOfStars < 4" class="fa fa-star"></span>
            <span @click="setToFiveStars" v-if="wantedAmountOfStars < 5" class="fa fa-star"></span>
            <textarea v-model="myComment" class="commentArea" wrap="hard"></textarea>
            <input class="submitButton" type="submit" value="Post Review">
          </form>
        </div>
      <div v-if="!loggedInUser" class="notLoggedInDiv">
        <router-link class="loginLink" to="/login">Want to post a review of this User? Log in!</router-link>
      </div>
      <div v-if="samePerson" class="errorDiv">
        <p class="reviewsAboutYourselfP">You cannot post reviews about yourself.</p>
      </div>
    </div>
  </div>
  <router-link class="hidden" to="/login">-</router-link>
</template>
<script>
import UserReview from '../components/UserReview.vue';
import store from '../store.js';
import ProfilePageListing from '../components/ProfilePageListing.vue';

export default {
  components: {
    UserReview,
    ProfilePageListing
  },
  data() {
    return {
      username: this.$route.params.username ? this.$route.params.username : "Not Logged In",
      reviewsFromDatabase: [],
      myComment: '',
      wantedAmountOfStars: 3,
      loggedInUser: this.$store.state.user,
      samePerson: false,
      myListings: []
    };
  },
  async mounted() {
    if (!this.$store.state.user) {
      await this.$store.dispatch("whoAmI")
      this.loggedInUser = this.$store.state.user
      this.username = this.$route.params.username ? this.$route.params.username : "Not Logged In"
    }
    if(this.$store.getters.user){
      if(this.$store.getters.user.username == this.username){
        this.samePerson = true;
      }
    }
    if(this.username == "Not Logged In"){
      document.getElementsByClassName("hidden")[0].click();
    }
        let myQueryParams = {
          wantedUsername: this.$route.params.username
        }
        let res = await fetch('http://localhost:4000/getReviewsForUser?' + 
        new URLSearchParams(myQueryParams), {
          method: 'GET',
          mode: 'cors'
        }).then((response) => {
          return response.json();
        }).then((data) => {
          let currentIndex = 0;
          if(data == "Did not find any reviews"){
            this.reviewsFromDatabase = [];
            return;
          }
          while(currentIndex < Object.keys(data).length){
            this.reviewsFromDatabase.push(data[currentIndex])
            currentIndex += 1;
          }
          console.log(this.reviewsFromDatabase);
        });

        let wantedUsername = {
         username: this.username
       }
       let responseFromListings  = await fetch('http://localhost:4000/getListingsByOwner?' +
       new URLSearchParams(wantedUsername), {
          method: 'GET',
          mode: 'cors'
        }).then((response) => {
         return response.json();
        }).then((data) => {
        
          let currentIndex = 0;
          this.myListings = [];
          if(data == "No user was found."){  
            return;
          }
          while(currentIndex < Object.keys(data).length){
            this.myListings.push(data[currentIndex])
            currentIndex += 1;
          } 
        }); 
  },
  methods: {
    async tryToPostReview(){
        let myQueryParams = {
          authorId: this.$store.state.user.userId,
          postedAbout: this.username
        }
        console.log(this.$store.state.user);
        let comment = {
          comment: this.myComment,
          rating: this.wantedAmountOfStars,
          version: 1
        }

        let res = await fetch('http://localhost:4000/postReviewAboutOtherUser?' + 
        new URLSearchParams(myQueryParams), {
          method: 'POST',
          mode: 'cors',
          body: JSON.stringify(comment)
        }).then((response) => {
          return response.json();
        }).then((data) => {
          this.reviewsFromDatabase = [];
          let currentIndex = 0;
          console.log("The DATA:");
          console.log(data);
          while(currentIndex < Object.keys(data).length){
            this.reviewsFromDatabase.push(data[currentIndex])
            currentIndex += 1;
          }
          console.log(this.reviewsFromDatabase);
        }); 
    },
    setToOneStar(){
      this.wantedAmountOfStars = 1;
    },
    setToTwoStars(){
      this.wantedAmountOfStars = 2;
    },
    setToThreeStars(){
      this.wantedAmountOfStars = 3;
    },
    setToFourStars(){
      this.wantedAmountOfStars = 4;
    },
    setToFiveStars(){
      this.wantedAmountOfStars = 5;
    },
  },
};
</script>
<style scoped>
.divOfListings{
  position: absolute;
  top: 500px;
  left: 15vw;
  width:max-content;
  margin-left:auto;
  margin-right:auto;
  max-width: 75vw;
}
.hidden{
  display:none;
}
.reviewsAboutYourselfP{
  font-size: 20px;
  font-weight: bolder;
}
.notLoggedInDiv{
  width:max-content;
  display:inline-block;
  position: absolute;
  margin-left:-100px;
  margin-top: 20px;
  padding-bottom: 50px;
}
.loginLink{
  font-size: 30px;
  font-weight:bolder;
}
.upperDiv{
  display:block;
}
.reviewsDiv{
  display:block;
  position: absolute;
  top: 650px;
  width:max-content;
  margin-left:auto;
  margin-right:auto;
  left: 43vw;
}
.mainDiv{
  height:100vh;
  background-color:lightcyan;
}
.backToStartPage{
  width: max-content;
  background-color:lightcoral;
  padding-left:1%;
  padding-right:1%;
  padding-top: 0.5%;
  padding-bottom:0.5%;
  font-weight:bold;
}
a {
  text-decoration: none;
  color:black;
}
.navbar{
  background-color:lightcoral;
}
.myProfileBox{
  position:absolute;
  top: 32%;
  left:47%;
  font-weight:bolder;
  font-size:30px;
}
.subBox1{
  position:absolute;
  top: 36%;
  left: 47.2%;
}
.subBox2{
  position:absolute;
  top: 38%;
  left: 47.2%;
}
.subBox3{
  position:absolute;
  top: 40%;
  left: 48%;
  color:blue;
  text-decoration:underline;
}
.subBox4{
  position:absolute;
  top: 45%;
  left: 46.5%;
}
.myLeasesP{
  position:absolute;
  top: 40%;
  left: 47.5%;
  font-weight:bolder;
  font-size:25px;
}
.submitButton{
  display:block;
  margin-left:auto;
  margin-right:auto;
  margin-top: 10px;
}
.newReviewDivBox{
  width:280px;
  padding-left: 10px;
  padding-right: 15px;
  padding-bottom: 10px;
  padding-top: 5px;
  height: 220px;
  display:block;
  margin-left:auto;
  margin-right: auto;
  background-color:white;
  outline: solid 1px black;
}
.commentArea{
  display:block;
  width: 280px;
  height: 160px;
  min-height: 160px;
  min-width: 280px;
  max-height: 160px;
  max-width: 280px;
}
.cellBox{
  display:inline-block;
  width:10vw;
  margin-left: 3px;
  text-align:center;
  font-size:23px;
}
</style>
