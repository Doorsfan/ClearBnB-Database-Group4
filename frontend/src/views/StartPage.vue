<template>
  <div class="mainDiv">
    <div class="navbar">
      <div class="locationBox Box">
        Location
        <div class="locationBar">
          <input type="text" placeholder="Location" class="searchBar" />
        </div>
      </div>
      <div class="guestBox Box">
        Guests
        <div class="guestsBar">
          <input type="number" min="1" placeholder="Guests" class="guestsBar" />
        </div>
      </div>
      <div class="startDateBox Box">
        Start Date
        <div class="startDateBar"><input min="2021-09-17" type="date" /></div>
      </div>
      <div class="endDateBox Box">
        End Date
        <div class="endDateBar">
          <input type="date" min="2021-09-17" max="2021-10-17" />
        </div>
      </div>
      <div class="priceBox Box">
        Below Price Of
        <div class="priceBar">
          <input placeholder="0" type="number" min="0" />
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
      <div class="innerWindow">
        <input type="text" placeholder="Write your message here.." class="myMessageToSupport">
      </div>
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
export default {
  components: {
    Posting
  },
  data() {
    return {
      chatOpened: false,
      relevantListings: [
      ]
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
    search() {},
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
