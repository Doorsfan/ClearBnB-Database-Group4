<template>
  <div class="mainDiv">
    <div class="upperDiv">
      <div class="navbar">
      </div>
      <div class="myProfileBox centerBox">My Profile</div>
      <div class="myProfileInfoBoxUsername centerBox subBox1">Username: {{ username }}</div>
      <div class="myProfileInfoBoxBalance centerBox subBox2">Balance: TEMP</div>
      <router-link to="/leaseAHouse" class="centerBox subBox3">Add a Lease</router-link>
      <p class="myLeasesP">My Leases</p>
      <div class="myProfileInfoBoxLinks centerBox subBox4">
        <p class="tempLeaseList">MY LEASE 1 IN LIST</p>
        <p class="tempLeaseList">MY LEASE 2 IN LIST</p>
        <p class="tempLeaseList">MY LEASE 3 IN LIST</p>
        <p class="tempLeaseList">MY LEASE 4 IN LIST</p>
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

export default {
  components: {
    UserReview
  },
  data() {
    return {
      username: (this.$route.params.username) ? 
      (this.$route.params.username) : ((this.$store.state.user) ? 
        (this.$store.state.user.username) : "Not Logged In"),
      reviewsFromDatabase: [],
      myComment: '',
      wantedAmountOfStars: 3,
      loggedInUser: this.$store.state.user,
      samePerson: (this.$store.state.user) ? (this.$route.params.username == this.$store.state.user.username) : false,
    };
  },
  async mounted() {
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
          console.log(data);
          let currentIndex = 0;
          if(data == "Did not find any reviews"){
            return;
          }
          while(currentIndex < Object.keys(data).length){
            this.reviewsFromDatabase.push(data[currentIndex])
            currentIndex += 1;
          }
        });
  },
  methods: {
    async tryToPostReview(){
        let myQueryParams = {
          author_id: this.$store.state.user.userId,
          postedAbout: this.username
        }
        console.log(this.$store.state.user);
        let comment = {
          comment: this.myComment,
          rating: this.wantedAmountOfStars,
        }

        console.log(this.$store.state.user);

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
          while(currentIndex < Object.keys(data).length){
            this.reviewsFromDatabase.push(data[currentIndex])
            currentIndex += 1;
          }
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
</style>
