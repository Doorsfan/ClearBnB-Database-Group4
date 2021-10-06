<template>
  <div class="mainDiv">
    <div class="navbar">
      <div class="locationBox Box">
        Location
        <div class="locationBar">
          <input @change="updateMyLocation" type="text" placeholder="Location" class="myLocationInput searchBar" />
        </div>
      </div>
      <div class="guestBox Box">
        Guests
        <div class="guestsBar">
          <input @change="updateMyGuests" type="number" min="1" placeholder="Guests" class="myGuestsInput guestsBar" />
        </div>
      </div>
      <div class="startDateBox Box">
        Start Date
        <div class="startDateBar"><input @change="updateMyMinDate" class="myMinDateInput" :min="myMinDate" type="date" /></div>
      </div>
      <div class="endDateBox Box">
        End Date
        <div class="endDateBar">
          <input class="myMaxDateInput" @change="updateMyMaxDate" type="date" :min="myMinDate" />
        </div>
      </div>
      <div class="priceBox Box">
        Below Price Of
        <div class="priceBar">
          <input class="myPriceInput" @change="updateMyPrice" placeholder="0" type="number" min="0" />
        </div>
      </div>
      <button class="searchButton" @click="search" type="button">Search</button>
    </div>
    <div class="chat">
      <a @click="navigateToSupport">Chat with support</a>
    </div>
    <div class="searchDiv">
      <div class="categoryBox firstCategoryBox">
        Title
      </div>
      <div class="categoryBox">
        Description
      </div>
      <div class="categoryBox">
        Image URL
      </div>
      <div class="categoryBox">
        Location
      </div>
      <div class="categoryBox">
        Nr Of Guests
      </div>
      <div class="categoryBox">
        Price
      </div>
      <div class="categoryBox">
        Available From
      </div>
      <div class="categoryBox">
        Available Until
      </div>
      <div class="categoryBox">
        Hosted By
      </div>
      <Posting
        v-for="(listItem, index) of relevantListings"
        :Listing="listItem"
        :key="index"
      />
    </div>
  </div>
</template>
<script>
import Listing from '../jsClasses/Listing.js';
import Posting from '../components/Posting.vue';
import ChatBox from '../components/ChatBox.vue';

export default {
  components: {
    Posting,
    ChatBox,
  },
  data() {
    return {
      chatOpened: false,
      relevantListings: [],
      myLocation: '',
      numberGuests: 1,
      myMinDate: '',
      myMaxDate: '',
      myPrice: 0
    };
  },
  async mounted() {
    let myDate = new Date();
    let myYear = myDate.getFullYear();
    let myMonth = ((myDate.getMonth() + 1) < 10 ? '0' + (myDate.getMonth() + 1) : (myDate.getMonth() + 1));
    let myDay = (myDate.getDate() < 10 ? '0' + myDate.getDate() : myDate.getDate());
    this.myMinDate = myYear + '-' + myMonth + '-' + myDay;
    let res = await fetch('http://localhost:4000/getAllListings', {
      method: 'POST',
      mode: 'cors',
      credentials: 'include',
      body: ''
    }).then((response) => {
        return response.json();
    }).then((data) => {
      let currentIndex = 0;
      this.relevantListings = []
      let currentVersion = 0;
      let currentId = 0;
      this.relevantListings = [];
      let myCurrentIndex = 0;
      while(myCurrentIndex < Object.keys(data).length){
        let myListing = new Listing(
              data[myCurrentIndex].listingId,
              data[myCurrentIndex].owner.username, 
              data[myCurrentIndex].title, 
              data[myCurrentIndex].description, 
              data[myCurrentIndex].imageUrl, 
              data[myCurrentIndex].location, 
              data[myCurrentIndex].numberGuests, 
              data[myCurrentIndex].price, 
              data[myCurrentIndex].listingStartDate,
              data[myCurrentIndex].listingEndDate
            );
            this.relevantListings.push(myListing);
            console.log(myListing);
            myCurrentIndex += 1;
      }    
    });
      // listingId 4, versionId 1
      // listingId 4, versionId 2
      // listingId 5, versionId 1
      //
      // listingId5 {1: versionId: 1, 2: versionId: 2}
      // 

  },
  methods: {
    updateMyPrice() {
      this.myPrice = document.getElementsByClassName('myPriceInput')[0].value;
    },
    updateMyLocation() {
      this.myLocation = document.getElementsByClassName('myLocationInput')[0].value;
    },
    updateMyGuests() {
      this.numberGuests = document.getElementsByClassName('myGuestsInput')[0].value;
    },
    updateMyMinDate() {
      this.myMinDate = document.getElementsByClassName('myMinDateInput')[0].value;
    },
    updateMyMaxDate() {
      this.myMaxDate = document.getElementsByClassName('myMaxDateInput')[0].value;
    },
    async search() {
      let myQueryParams = {
        location: this.myLocation,
        numberGuests: this.numberGuests,
        myMinDate: this.myMinDate,
        myMaxDate: this.myMaxDate,
        myPrice: this.myPrice
      }
      let myScope = this;
      this.relevantListings = [];
      
      let res = await fetch('http://localhost:4000/getResultsFromFiltering?' + 
      new URLSearchParams(myQueryParams), {
        method: 'GET',
        mode: 'cors'
      }).then((response) => {
          return response.json();
        }).then((data) => {
          this.relevantListings = [];
          let currentIndex = 0;
          while(currentIndex < Object.keys(data).length){
            console.log("My listing Loop");
            console.log(data);
            let myListing = new Listing(
              data[currentIndex].listingId,
              data[currentIndex].owner.username, 
              data[currentIndex].title, 
              data[currentIndex].description, 
              data[currentIndex].imageUrl, 
              data[currentIndex].location, 
              data[currentIndex].numberGuests, 
              data[currentIndex].price, 
              data[currentIndex].listingStartDate,
              data[currentIndex].listingEndDate
            );
            myScope.relevantListings.push(myListing);
            currentIndex += 1;
          }
          console.log(this.relevantListings);
          
        });
    },
    navigateToSupport() {
      if(!this.$store.state.user) {
        alert("You need to be logged in to chat")
      } else {
        this.$router.push("/supportchat/" + this.$store.state.user.username)
      }
    }
  },
};
</script>
<style scoped>
.firstCategoryBox{
  margin-top: 20px;
  margin-left: 100px;
}
.categoryBox{
  display:inline-block;
  width: 10vw;
  outline: solid 1px black;
  text-align:center;
  margin-right: 6px;
  margin-bottom: 2px;
  color:white;
  background-color:black;
  font-weight: bold;
}
.Box {
  max-width: max-content;
  display: inline-block;
  padding: 20px;
}
.navbar {
  max-width: max-content;
  margin-left: auto;
  margin-right: auto;
}
.mainDiv {
  background-color: lightcoral;
}
a:visited {
  color: blue;
}
.searchButton {
  margin-right: 100px;
}
.myProfileBox {
  margin-left: 50px;
}
.searchDiv {
  background-color: lightcyan;
  height: 98vh;
}
.chat {
  margin: auto;
  text-align: center;
  margin-bottom: 10px;
}
.chat a {
  font-size: 20pt;
  cursor: pointer;
}
</style>
