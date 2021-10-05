<template>
  <div class="mainDiv">
    <div class="centralDiv">
      <div v-if="currentUsername == postedByUsername" class="updateDiv">
        <button v-if="!editMode" @click="changeToEditMode" class="button">Edit Lease</button>
        <button v-if="editMode" @click="saveChanges" class="button">Save Changes</button>
      </div>
      <div class="titleText">
        <div class="inline">Title: </div>
        <div v-if="!editMode" class="inline">{{ myTitle }}</div>
        <input v-model="newTitle" v-if="editMode" type="text" class="inline" :placeholder=myTitle>
      </div>
      <div class="hostedByTitle">
        <div class="inline">Hosted by: </div>
        <router-link id="postedByLink" :to="{ name: 'profile', params: { username: postedByUsername
        }}">{{ postedByUsername }}</router-link>
      </div>
      <div class="descriptionText">
        <div class="inline">Description: </div>
        <div v-if="!editMode" class="inline">{{ myDescription }}</div>
        <input v-model="newDescription" v-if="editMode" type="text" class="inline" :placeholder=myDescription>
      </div>
      <div class="imageURLText">
        <div v-if="editMode" class="inline">ImageURL: </div><img v-if="!editMode" :src=myImageURL id="imageOfTheHouse" width="300" height="300">
        <input v-model="newImageURL" v-if="editMode" type="text" class="inline" :placeholder=myImageURL>
      </div>
      <div class="locationText">
        <div class="inline">Location: </div>
        <div v-if="!editMode" class="inline">{{ myLocation }}</div>
        <input v-model="newLocation" v-if="editMode" type="text" class="inline" :placeholder=myLocation>
      </div>
      <div class="numberOfGuestsText">
        <div class="inline">Number of Guests: </div>
        <div v-if="!editMode" class="inline">{{ myNumberOfGuests }}</div>
        <input v-model="newNumberOfGuests" v-if="editMode" type="text" class="inline" :placeholder=myNumberOfGuests>
      </div>
      <div class="priceText">
        <div class="inline">Price per night: </div>
        <div v-if="!editMode" class="inline">{{ displayPrice }}</div>
        <input v-model="newPrice" v-if="editMode" type="text" class="inline" :placeholder=myPrice>
      </div>
      <div class="startDateText">
        <div class="inline">Available from: </div>
        <div v-if="!editMode" class="inline">{{ myStartDate }}</div>
        <div class="centerText" v-if="editMode">Year:</div>
        <input v-model="newFromYear" v-if="editMode" type="text" class="inline">
        <div class="centerText" v-if="editMode">Month</div>
        <input v-model="newFromMonth" v-if="editMode" type="text" class="inline">
        <div class="centerText" v-if="editMode">Day</div>
        <input v-model="newFromDay" v-if="editMode" type="text" class="inline">
      </div>
      <div class="endDateText">
        <div class="inline">Available until: </div>
        <div v-if="!editMode" class="inline">{{ myEndDate }}</div>
        <div class="centerText" v-if="editMode">Year:</div>
        <input v-model="newEndYear" v-if="editMode" type="text" class="inline">
        <div class="centerText" v-if="editMode">Month</div>
        <input v-model="newEndMonth" v-if="editMode" type="text" class="inline">
        <div class="centerText" v-if="editMode">Day</div>
        <input v-model="newEndDay" v-if="editMode" type="text" class="inline">
      </div>
        <div v-if="!editMode" class="inline">Version -</div> 
        <select v-if="!editMode" @change="updateInfoBasedOnVersion" class="myGuests" v-model="wantedVersion">
          <option v-for="(version, index) of versions" class="versionOption"
        :key="index"
        :version="version">{{ index + 1}}</option>
        </select>
      </div>
      <div v-if="!editMode" class="BookingDateDiv">
      Book from the:
        <input @change="updateDays" v-model="wantedStartDate" type="date" class="bookingStartsDateElement">
        Book until the:
        <input @change="updateDays" v-model="wantedEndDate" type="date" class="bookingEndDateElement">
      </div>
      <div v-if="priceToPay && priceToPay > 0" class="bookingPriceDiv">
        Total sum to pay for {{ amountOfDaysWanted }} days: {{ priceToPay }}
      </div>
      <button v-if="wantedStartDate < wantedEndDate && !editMode && currentUsername.length > 0 && priceToPay" @click="tryToBook" class="bookButton" type="button" value="Book">Book</button>
      <div class="reviewsDiv">
        <ReviewBox
          v-for="(listItem, index) of relevantReviews"
          :key="index"
          :Content="listItem"
        />
        <div v-if="currentUsername.length > 0 && postedByUsername == currentUsername" class="errorBox">You cannot post reviews to your own listing.</div>
        <div v-if="currentUsername.length > 0 && postedByUsername != currentUsername" class="newReviewDivBox">
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
      isValidDate: (this.wantedStartDate != undefined && this.wantedEndDate.length != undefined) ? (this.purgedStartDate < this.purgedEndDate ? true : false) : false,
      purgedStartDate: this.wantedStartDate ? this.wantedStartDate.replaceAll("-", "") : '',
      purgedEndDate: this.wantedEndDate ? this.wantedEndDate.replaceAll("-", "") : '',
      wantedStartDate: new Date(this.myStartDate),
      wantedEndDate: new Date(this.myEndDate),
      amountOfDaysWanted: '',
      wantedVersion: 1.0,
      versions: [],
      versionsOfListing: [],
      currentUserId: (this.$store.getters.user) ? (this.$store.getters.user.userId) : '',
      currentUsername: (this.$store.getters.user) ? (this.$store.getters.user.username) : '',
      currentUserBalance: (this.$store.getters.user) ? (this.$store.getters.user.balance) : 0,
      myListingId: this.$route.query.listingId,
      postedByUsername: this.$route.query.postedByUsername,
      myTitle: this.$route.query.title,
      myDescription: this.$route.query.description,
      myImageURL: this.$route.query.imageUrl,
      myLocation: this.$route.query.location,
      myNumberOfGuests: this.$route.query.numberGuests,
      myPrice: this.$route.query.price,
      myStartDate: this.$route.query.listingStartDate,
      myStartYear: this.$route.query.listingStartDate.substring(0,4),
      myStartMonth: this.$route.query.listingStartDate.substring(5,7),
      myStartDay: this.$route.query.listingStartDate.substring(8,10),
      myEndDate: this.$route.query.listingEndDate,
      myEndYear: this.$route.query.listingEndDate.substring(0,4),
      myEndMonth: this.$route.query.listingEndDate.substring(5,7),
      myEndDay: this.$route.query.listingEndDate.substring(8,10),
      reviewsFromDatabase: [],
      relevantReviews: [],
      wantedAmountOfStars: 3,
      myComment: '',
      editMode: false,
      newTitle: '',
      newDescription: '',
      newImageURL: '',
      newLocation: '',
      newNumberOfGuests: '',
      newPrice: '',
      newFromYear: '',
      newFromMonth: '',
      newFromDay: '',
      newEndYear: '',
      newEndMonth: '',
      newEndDay: '',
      priceToPay: 0,
      displayPrice: 0,
    };
  },
  async mounted() {
    this.amountOfDaysWanted = (this.wantedEndDate.getTime() - this.wantedStartDate.getTime())/(100 * 3600 * 24);
    //Query the DB for Versions on this point, to get them
    document.getElementById('imageOfTheHouse').src = this.myImageURL;
    document.getElementsByClassName('bookingStartsDateElement')[0].min = this.myStartDate;
    document.getElementsByClassName('bookingStartsDateElement')[0].max = this.myEndDate;

    document.getElementsByClassName('bookingEndDateElement')[0].min = this.myStartDate;
    document.getElementsByClassName('bookingEndDateElement')[0].max = this.myEndDate;

    document.getElementById("postedByLink").to = this.postedByUsername;
    let queryParams = {
        title: this.$route.query.title,
        description: this.$route.query.description,
        imageUrl: this.$route.query.imageUrl,
        location: this.$route.query.location,
        numberGuests: this.$route.query.numberGuests,
        price: this.$route.query.price,
        listingStartDate: this.$route.query.listingStartDate,
        listingEndDate: this.$route.query.listingEndDate,
      }

      let resForAllListings = await fetch('http://localhost:4000/getAllVersionsOfListing', {
        method: 'POST',
        mode: 'cors',
        credentials: 'include',
        body: JSON.stringify(queryParams),
      }).then((response) => {
        return response.json();
      }).then((data) => {
        let currentIndex = 0;
        while(currentIndex < Object.keys(data).length){
          this.versions.push(data[currentIndex].version);
          this.versionsOfListing.push(data[currentIndex]);
          currentIndex += 1;
        }
        this.wantedVersion = this.versions.length;
        this.displayPrice = Math.round(this.versionsOfListing[this.wantedVersion-1].price * 1.15);
      })
      
      let res = await fetch('http://localhost:4000/getReviewsForListing', {
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
                  data[currentIndex].reviewId,
                  data[currentIndex].author.username, 
                  data[currentIndex].timestamp[0], 
                  data[currentIndex].timestamp[1], 
                  data[currentIndex].timestamp[2], 
                  data[currentIndex].timestamp[3],
                  data[currentIndex].timestamp[4],
                  data[currentIndex].comment, 
                  data[currentIndex].rating, 
                  data[currentIndex].version
                ));
              this.relevantReviews = [];
              this.reviewsFromDatabase.forEach(element => {
                if(element.version == this.versions.length){
                  this.relevantReviews.push(element);
                }
              });
              currentIndex += 1;
            }
          });
  },
  methods: {
    updateDays(){
      let end = new Date(this.wantedEndDate).getTime();
      let start = new Date(this.wantedStartDate).getTime();
      this.amountOfDaysWanted = (end - start)/(1000 * 3600 * 24);
      this.priceToPay = this.amountOfDaysWanted * this.displayPrice;
    },
    changeToEditMode(){
      if(!this.editMode){
        this.editMode = true;
      }
    },
    async saveChanges(){
      let changedSomething = false;
      if(this.myTitle != this.newTitle && this.newTitle.length > 0){
        this.myTitle = this.newTitle;
        changedSomething = true;
      }
      if(this.myDescription != this.newDescription && this.newDescription.length > 0){
        this.myDescription = this.newDescription;
        changedSomething = true;
      }
      if(this.myImageURL != this.newImageURL && this.newImageURL.length > 0){
        this.myImageURL = this.newImageURL;
        changedSomething = true;
      }
      if(this.myLocation != this.newLocation && this.newLocation.length > 0){
        this.myLocation = this.newLocation;
        changedSomething = true;
      }
      if(this.myNumberOfGuests != this.newNumberOfGuests && this.newNumberOfGuests.length > 0){
        this.myNumberOfGuests = this.newNumberOfGuests;
        changedSomething = true;
      }
      if(this.myPrice != this.newPrice && this.newPrice.length > 0){
        this.myPrice = this.newPrice;
        changedSomething = true;
      }
      if(this.myStartYear != this.newFromYear && this.newFromYear.length > 0){
        this.myStartYear = this.newFromYear;
        changedSomething = true;
      }
      if(this.myStartMonth != this.newFromMonth && this.newFromMonth.length > 0){
        this.myStartMonth = this.newFromMonth;
        changedSomething = true;
      }
      if(this.myStartDay != this.newFromDay && this.newFromDay.length > 0){
        this.myStartDay = this.newFromDay;
        changedSomething = true;
      }
      if(this.myStartDate != (this.myStartYear + '-' + this.myStartMonth + '-' + this.myStartDay)
        && (this.myStartYear + '-' + this.myStartMonth + '-' + this.myStartDay).length == this.myStartDate.length){
          this.myStartDate = (this.myStartYear + '-' + this.myStartMonth + '-' + this.myStartDay)
          changedSomething = true;
      }
      if (this.myEndYear != this.newEndYear && this.newEndYear.length > 0) {
        this.myEndYear = this.newEndYear;
        changedSomething = true;
      }
      if (this.myEndMonth != this.newEndMonth && this.newEndMonth.length > 0) {
        this.myEndMonth = this.newEndMonth;
        changedSomething = true;
      }
      if (this.myEndDay != this.newEndDay && this.newEndDay.length > 0) {
        this.myEndDay = this.newEndDay;
        changedSomething = true;
      }
      if (
        this.myEndDate != (this.myEndYear + '-' + this.myEndMonth + '-' + this.myEndDay) &&
        (this.myEndYear + '-' + this.myEndMonth + '-' + this.myEndDay).length == this.myEndDate.length) {
        this.myEndDate =
          (this.myEndYear + '-' + this.myEndMonth + '-' + this.myEndDay);
        changedSomething = true;
      }

      let queryParams = {
        listingId: this.myListingId,
        title: this.myTitle,
        description: this.myDescription,
        imageUrl: this.imageUrl,
        location: this.location,
        numberGuests: this.numberGuests,
        price: this.myPrice,
        listingStartDate: this.myStartDate,
        listingEndDate: this.myEndDate
      }
      let res = await fetch('http://localhost:4000/updateLease', {
        method: 'POST',
        mode: 'cors',
        body: JSON.stringify(queryParams)
      }).then((response) => {
            return response.json();
      }).then((data) => {
        let currentIndex = 0;
        while(currentIndex < Object.keys(data).length){
          currentIndex += 1;
          this.versions.push(currentIndex);
        } 
      });
      if(this.editMode){
        this.editMode = false;
      }
      console.log(this.myStartDate);
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
    async updateInfoBasedOnVersion() {
      //Update the State variables in terms of dynamically reflecting the data
      //Based on this Version, query the DB in terms of finding the respective
      //version of it from the DB
      this.myTitle = this.versionsOfListing[this.wantedVersion - 1].title;
      this.myDescription = this.versionsOfListing[this.wantedVersion - 1].description;
      this.myImageURL = this.versionsOfListing[this.wantedVersion - 1].imageUrl;
      this.myLocation = this.versionsOfListing[this.wantedVersion - 1].location;
      this.myNumberOfGuests = this.versionsOfListing[this.wantedVersion - 1].numberGuests;
      this.myPrice = this.versionsOfListing[this.wantedVersion - 1].price;
      this.myStartDate = this.versionsOfListing[this.wantedVersion - 1].listingStartDate;
      this.myEndDate = this.versionsOfListing[this.wantedVersion - 1].listingEndDate;
      this.relevantReviews = [];

      let queryParams = {
        title: this.$route.query.title,
        description: this.$route.query.description,
        imageUrl: this.$route.query.imageUrl,
        location: this.$route.query.location,
        numberGuests: this.$route.query.numberGuests,
        price: this.$route.query.price,
        listingStartDate: this.$route.query.listingStartDate,
        listingEndDate: this.$route.query.listingEndDate,
      }

      let res = await fetch('http://localhost:4000/getReviewsForListing', {
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
                  data[currentIndex].reviewId,
                  data[currentIndex].author.username, 
                  data[currentIndex].timestamp[0], 
                  data[currentIndex].timestamp[1], 
                  data[currentIndex].timestamp[2], 
                  data[currentIndex].timestamp[3],
                  data[currentIndex].timestamp[4],
                  data[currentIndex].comment, 
                  data[currentIndex].rating, 
                  data[currentIndex].version
                ));
              currentIndex += 1;
            }
            this.relevantReviews = [];
            this.reviewsFromDatabase.forEach(element => {
              if(element.version == this.wantedVersion){
                this.relevantReviews.push(element);
              }
            });
          });
    },
    async tryToBook() {
      //Implement so queries can be made and actually perform the real booking
      let myUser = {
          userId: this.$store.getters.user.userId,
          username: this.$store.getters.user.username,
          password: "",
          email: this.$store.getters.user.email,
          balance: (this.$store.getters.user.balance - this.myPriceToPay)
      }
      let wantedBooking = {
        amountPaid: this.priceToPay,
        bookedByUser: myUser,
        listingBooked: this.myListingId,
        bookingStartDate: this.wantedStartDate,
        bookingEndDate: this.wantedEndDate,
        cancelled: 0
      }

      let myPriceToPay = this.priceToPay;
      let res = await fetch('http://localhost:4000/booking', {
        method: 'POST',
        mode: 'cors',
        credentials: 'include',
        body: JSON.stringify(wantedBooking),
      }).then(function(response){
        return response.json();
      }).then(async function(data){
        console.log(data);
      });
    },
    async tryToPostReview(){
      //Implement so queries can be made and actually perform the real review posting
      let myUser = {
        userId: this.$store.getters.user.userId,
        username: this.$store.getters.user.username,
        email: this.$store.getters.user.email,
        balance: this.$store.getters.user.balance
      }
 
      console.log("The wanted version: " + this.wantedVersion);
      let queryParams = {
        title: this.$route.query.title,
        description: this.$route.query.description,
        imageUrl: this.$route.query.imageUrl,
        location: this.$route.query.location,
        numberGuests: this.$route.query.numberGuests,
        price: this.$route.query.price,
        listingStartDate: this.$route.query.listingStartDate,
        listingEndDate: this.$route.query.listingEndDate,
      }
      let myReview = {
        author: myUser,
        comment: this.myComment,
        rating: this.wantedAmountOfStars,
        target: myUser,
        timestamp: new Date(),
        version: this.wantedVersion,
        myQueryParameters: queryParams
      }

        
        let res = await fetch('http://localhost:4000/review', {
          method: 'POST',
          mode: 'cors',
          credentials: 'include',
          body: JSON.stringify(myReview),
          }).then((response) => {
            return response.json();
          }).then((data) => {
            let currentIndex = 0;
            this.reviewsFromDatabase = []

            while(currentIndex < Object.keys(data).length){
              this.reviewsFromDatabase.push(
                new Review(
                  data[currentIndex].reviewId,
                  data[currentIndex].author.username, 
                  data[currentIndex].timestamp[0], 
                  data[currentIndex].timestamp[1], 
                  data[currentIndex].timestamp[2], 
                  data[currentIndex].timestamp[3],
                  data[currentIndex].timestamp[4],
                  data[currentIndex].comment, 
                  data[currentIndex].rating, 
                  data[currentIndex].version
                ));
              currentIndex += 1;
            }
            this.relevantReviews = [];
            this.reviewsFromDatabase.forEach(element => {
              if(element.version == this.wantedVersion){
                this.relevantReviews.push(element);
              }
            });
          });
    }
  },
};
</script>
<style scoped>
.errorBox{
  width:max-content;
  margin-left:auto;
  margin-right:auto;
  margin-top: 20px;
  font-size: 25px;
  font-weight: bold;
}
.neededP, .haveP{
  margin: 0px;
}
.notEnoughBalanceDiv{
  width:max-content;
  margin-left:auto;
  margin-right:auto;
  margin-top: 10px;
  margin-bottom: 10px;
}
.BookingDateDiv{
  margin-top: 10px;
  margin-bottom: 10px;
}
.bookButton{
  margin-bottom: 10px;
}
.bookingPriceDiv{
  width:max-content;
  margin-left:auto;
  margin-right:auto;
  margin-top: 20px;
}
.startDateText, .endDateText{
  width:max-content;
  margin-left: auto;
  margin-right:auto;
  padding-top: 10px;
  padding-bottom: 10px;
}
.centerText{
  width:max-content;
}
.updateDiv{
  margin-left:auto;
  margin-right:auto;
  width:max-content;
}
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
  padding-top: 20px;
}
</style>