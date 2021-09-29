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
        <div class="startDateBar"><input @change="updateMyMinDate" class="myMinDateInput" min="2021-09-17" type="date" /></div>
      </div>
      <div class="endDateBox Box">
        End Date
        <div class="endDateBar">
          <input class="myMaxDateInput" @change="updateMyMaxDate" type="date" min="2021-09-17" max="2021-10-17" />
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
        :key="index"
        :Listing="listItem"
      />
    </div>
    <div class="chatWindow" v-if="chatOpened">
        <ChatBox></ChatBox>
    </div>
    <footer class="bottomFooter">
      <div @click="openSupportChat" class="chatTab">
        Chat With Support
      </div>
    </footer>
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
    console.log("test");
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

      // listingId 4, versionId 1
      // listingId 4, versionId 2
      // listingId 5, versionId 1
      //
      // listingId5 {1: versionId: 1, 2: versionId: 2}
      // 
      const groupBy = (objectArray, property) => {
          return objectArray.reduce(function (total, obj) {
            let key = obj[property];
            if (!total[key]) {
              total[key] = [];
            }
            total[key].push(obj);
            return total;
          }, {});
        }

        let groupedListings = groupBy(data, 'listingId');

        console.log(groupedListings);

        for(var listing in groupedListings){
          let currentListing = groupedListings[listing];
          let relevantListing = currentListing[currentListing.length - 1];
          let latestVersionOfListing = new Listing(
            relevantListing.owner.username, 
            relevantListing.title, 
            relevantListing.description, 
            relevantListing.imageUrl, 
            relevantListing.location, 
            relevantListing.numberGuests, 
            relevantListing.price, 
            relevantListing.listingStartDate,
            relevantListing.listingEndDate
          );
          this.relevantListings.push(latestVersionOfListing);
        }     
    });
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
      console.log(myQueryParams);
      let res = await fetch('http://localhost:4000/getResultsFromFiltering?' + 
      new URLSearchParams(myQueryParams), {
        method: 'GET',
        mode: 'cors'
      });
    },
    openSupportChat() {
      if(this.chatOpened){
        this.chatOpened = false;
      }
      else if(!this.chatOpened){
        this.chatOpened = true;
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
}
.myMessageToSupport{
  position:absolute;
  bottom: 8px;
  right: 8px;
  width: 275px;
}
.innerWindow{
  height: 190px;
  width: 290px;
  background-color:white;
  margin: 5px;
}
.chatWindow{
  height: 200px;
  width: 300px;
  background-color:red;
  border:lightcoral solid 1px;
  z-index: 3;
  position:absolute;
  right: 20px;
  top:87vh;
}
.chatTab{
  position:absolute;
  right: 20px;
  padding-top: 15px;
  padding-right: 15px;
  font-weight:bolder;
  font-size: 20px;
}
.bottomFooter {
  height: 50px;
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
</style>
