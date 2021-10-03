export default class Listing {
  constructor(listingId, postedByUsername, title, description, imageUrl, location, numberGuests, price, listingStartDate, listingEndDate) {
    this.listingId = listingId;
    this.postedByUsername = postedByUsername;
    this.title = title;
    this.description = description;
    this.imageUrl = imageUrl;
    this.location = location;
    this.numberGuests = numberGuests;
    this.price = price;
    this.listingStartDate = listingStartDate;
    this.listingEndDate = listingEndDate;
  }
  
  setListingId(newId) {
    this.listingId = newId;
  }

  getListingId() {
    return this.listingId;
  }
  
  retrieveTheTitleOfTheListing() {
    return this.title;
  }

  setTheTitleOfTheListing(newTitleOfThisListing) {
    this.title = newTitleOfThisListing;
  }

  retrieveTheDescriptionOfTheListing() {
    return this.description;
  }

  setTheDescriptionOfTheListing(newDescription) {
    this.description = newDescription;
  }

  retrieveTheLinkToThePicture() {
    return this.imageUrl;
  }

  setTheLinkToThePicture(newImageURL) {
    this.imageUrl = newImageURL;
  }

  retrieveTheLocation() {
    return this.location;
  }

  setTheLocation(newLocation) {
    this.location = newLocation;
  }

  retrieveTheAmountOfGuests() {
    return this.numberGuests;
  }

  setTheAmountOfGuests(newAmountOfGuests) {
    this.numberGuests = newAmountOfGuests;
  }

  setThePrice(newPrice) {
    this.price = newPrice;
  }

  getThePrice() {
    return this.price;
  }

  setTheStartDateOfThisListing(newStartDate) {
    this.listingStartDate = newStartDate;
  }  

  getTheStartDateOfThisListing() {
    return this.listingStartDate;
  }

  setTheEndDateOfThisListing(newEndDate) {
    this.listingEndDate;
  }

  getTheEndDateOfThisListing() {
    return this.listingEndDate;
  }
}
