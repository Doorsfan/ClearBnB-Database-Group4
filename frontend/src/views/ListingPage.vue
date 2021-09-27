<template>
  <div class="mainDiv">
    <div class="navbar">
      <router-link to="/" class="clearBnBLogo">ClearBnB</router-link>
    </div>
    <div class="centralDiv">
      <div class="titleText">
        <div class="inline">Title: </div>
        {{ myTitle }}
      </div>
      <div class="hostedByTitle">
        <div class="inline">Hosted by: </div>
        <router-link id="postedByLink" :to="{ name: 'profile', params: { username: postedByUsername
        }}">{{ postedByUsername }}</router-link>
      </div>
      <div class="descriptionText">
        <div class="inline">Description: </div>
        {{ myDescription }}
      </div>
      <div class="imageURLText">
        <img src='' id="imageOfTheHouse" width="300" height="300">
      </div>
      <div class="locationText">
        <div class="inline">Location: </div>
        {{ myLocation }}
      </div>
      <div class="numberOfGuestsText">
        <div class="inline">Number of Guests: </div>
        {{ myNumberOfGuests }}
      </div>
      <div class="priceText">
        <div class="inline">Price: </div>
        {{ myPrice }}
      </div>
      <div class="startDateText">
        <div class="inline">Available from: </div>
        {{ myStartDate }}
      </div>
      <div class="endDateText">
        <div class="inline">Available until: </div>
        {{ myEndDate }}
      </div>
        Version - 
        <select @change="updateInfoBasedOnVersion" class="myGuests" v-model="wantedVersion">
          <option v-for="(version, index) of versions" class="versionOption"
        :key="index"
        :version="version">{{ index + 1}}</option>
        </select>
      </div>
      <div class="BookingDateDiv">
      Book from the:
        <input v-model="wantedStartDate" type="date" class="bookingStartsDateElement">
        Book until the:
        <input v-model="wantedEndDate" type="date" class="bookingEndDateElement">
      </div>
      <button @click="tryToBook" class="bookButton" type="button" value="Book">Book</button>
      <div class="reviewsDiv">
        <ReviewBox
          v-for="(listItem, index) of reviewsFromDatabase"
          :key="index"
          :Content="listItem"
        />
        <div class="newReviewDivBox">
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
      </div>
    </div>
</template>
<script>
import store from '../store.js';
import Review from '../jsClasses/Review.js';
import ReviewBox from '../components/ReviewBox.vue';
import User from '../jsClasses/User.js';
export default {
  components: {
    ReviewBox
  },
  data() {
    return {
      wantedStartDate: new Date(this.myStartDate),
      wantedEndDate: new Date(this.myEndDate),
      wantedVersion: 1.0,
      versions: [],
      postedByUsername: this.$route.query.postedByUsername,
      myTitle: this.$route.query.title,
      myDescription: this.$route.query.description,
      myImageURL: this.$route.query.image_url,
      myLocation: this.$route.query.location,
      myNumberOfGuests: this.$route.query.number_guests,
      myPrice: this.$route.query.price,
      myStartDate: this.$route.query.listing_start_date,
      myEndDate: this.$route.query.listing_end_date,
      reviewsFromDatabase: [],
      wantedAmountOfStars: 3,
      myComment: ''
    };
  },
  async mounted() {
    //Query the DB for Versions on this point, to get them
    this.versions = ['1.0', '2.0']
    document.getElementById('imageOfTheHouse').src = 'https://i2.wp.com/samhouseplans.com/wp-content/uploads/2021/01/Small-House-Plans-6.5x6-Meter-1.jpg?fit=1920%2C1080&ssl=1';
    document.getElementsByClassName('bookingStartsDateElement')[0].min = this.myStartDate;
    document.getElementsByClassName('bookingStartsDateElement')[0].max = this.myEndDate;

    document.getElementsByClassName('bookingEndDateElement')[0].min = this.myStartDate;
    document.getElementsByClassName('bookingEndDateElement')[0].max = this.myEndDate;

    document.getElementById("postedByLink").to = this.postedByUsername;
    let queryParams = {
        title: this.$route.query.title,
        description: this.$route.query.description,
        image_url: this.$route.query.image_url,
        location: this.$route.query.location,
        number_guests: this.$route.query.number_guests,
        price: this.$route.query.price,
        listing_start_date: this.$route.query.listing_start_date,
        listing_end_date: this.$route.query.listing_end_date,
      }
      let res = await fetch('http://localhost:4000/getSpecificListing', {
          method: 'POST',
          mode: 'cors',
          credentials: 'include',
          body: JSON.stringify(queryParams),
        }).then((response) => {
            return response.json();
          }).then((data) => {
            let currentIndex = 0;
            this.reviewsFromDatabase = []
            while(currentIndex < Object.keys(data).length){
              this.reviewsFromDatabase.push(
                new Review(
                  data[currentIndex].author.username, 
                  data[currentIndex].timestamp.year, 
                  data[currentIndex].timestamp.monthValue, 
                  data[currentIndex].timestamp.dayOfMonth, 
                  data[currentIndex].timestamp.hour,
                  data[currentIndex].timestamp.minute,
                  data[currentIndex].comment, 
                  data[currentIndex].rating, 
                  data[currentIndex].version
                ));
              currentIndex += 1;
            }
          });
    console.log("I WAS WENT INTO");
  },
  methods: {
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
    updateInfoBasedOnVersion() {
      //Update the State variables in terms of dynamically reflecting the data
      //Based on this Version, query the DB in terms of finding the respective
      //version of it from the DB
    },
    async tryToBook() {
      //Implement so queries can be made and actually perform the real booking
      let myUser = {
          userId: 1,
          username: "no",
          password: "no",
          email: "no",
          balance: 1000
      }
      let wantedBooking = {
        bookingId: 1,
        amountPaid: 1000,
        bookedByUser: myUser,
        listingBooked: 1,
        bookingStartDate: this.wantedStartDate,
        bookingEndDate: this.wantedEndDate,
        cancelled: 0
      }
      let res = await fetch('http://localhost:4000/booking', {
        method: 'POST',
        mode: 'cors',
        credentials: 'include',
        body: JSON.stringify(wantedBooking),
      }).then(function(response){
        return response.json();
      }).then(function(data){
        console.log(data);
      });
    },
    async tryToPostReview(){
      //Implement so queries can be made and actually perform the real review posting
      let myUser = {
        userId: 1,
        username: "John McCain",
        email: "John@cain.com",
        balance: 0
      }
      //author_id, comment, rating, target_id, timestamp, version, reviewId
      
      let queryParams = {
        title: this.$route.query.title,
        description: this.$route.query.description,
        image_url: this.$route.query.image_url,
        location: this.$route.query.location,
        number_guests: this.$route.query.number_guests,
        price: this.$route.query.price,
        listing_start_date: this.$route.query.listing_start_date,
        listing_end_date: this.$route.query.listing_end_date,
      }
      let myReview = {
        author: myUser,
        comment: this.myComment,
        rating: this.wantedAmountOfStars,
        target: myUser,
        timestamp: new Date(),
        version: 1,
        myQueryParameters: queryParams
      }
        let res = await fetch('http://localhost:4000/review', {
          method: 'POST',
          mode: 'cors',
          credentials: 'include',
          body: JSON.stringify(myReview, queryParams),
          }).then((response) => {
            return response.json();
          }).then((data) => {
            let currentIndex = 0;
            this.reviewsFromDatabase = []

            while(currentIndex < Object.keys(data).length){
              console.log(data[currentIndex].comment);
              this.reviewsFromDatabase.push(
                new Review(
                  data[currentIndex].author.username, 
                  data[currentIndex].timestamp.year, 
                  data[currentIndex].timestamp.monthValue, 
                  data[currentIndex].timestamp.dayOfMonth, 
                  data[currentIndex].timestamp.hour,
                  data[currentIndex].timestamp.minute,
                  data[currentIndex].comment, 
                  data[currentIndex].rating, 
                  data[currentIndex].version
                ));
              currentIndex += 1;
            }
          });
    }
  },
};
</script>
<style scoped>
.newReviewDivBox{
  width:300px;
  padding-left: 10px;
  padding-right: 15px;
  padding-bottom: 10px;
  padding-top: 5px;
  display:block;
  margin-left:auto;
  margin-right: auto;
  background-color:white;
  outline: solid 1px black;
}
.submitButton{
  width: max-content;
  display:block;
  margin-left:auto;
  margin-right:auto;
  margin-top: 10px;
}
.commentArea{
  margin-top: 5px;
  width: 300px;
  max-width: 300px;
  min-width: 300px;
  height: 200px;
  min-height: 200px;
  display:block;
  margin-left:auto;
  margin-right:auto;
  padding: 3px;
}
.bookButton{
  display:block;
  margin-left:auto;
  margin-right:auto;
  width:max-content;
  margin-top: 20px;
}
.BookingDateDiv{
  display:block;
  margin-left:auto;
  margin-right:auto;
  width:max-content;
}
.mainDiv{
  background-color:lightcyan;
  min-height: 102vh;
  padding-bottom: 20px;
  height: max-content;
}
.navbar{
  background-color:lightcoral;
  padding-top: 10px;
  padding-bottom: 10px;
}
.clearBnBLogo{
  text-decoration: none;
  font-size:25px;
  font-weight:bolder;
  margin-left: 10px;
  color:black;
}
.inline{
  display:inline-block;
}
.centralDiv{
  width:max-content;
  margin-left:auto;
  margin-right:auto;
  margin-top: 20vh;
}
</style>