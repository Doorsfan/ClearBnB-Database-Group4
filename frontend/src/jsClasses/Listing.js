export default class Listing {
  constructor(listingId, postedByUsername, title, description, image_url, location, number_guests, price, listing_start_date, listing_end_date) {
    this.listingId = listingId;
    this.postedByUsername = postedByUsername;
    this.title = title;
    this.description = description;
    this.image_url = image_url;
    this.location = location;
    this.number_guests = number_guests;
    this.price = price;
    this.listing_start_date = listing_start_date;
    this.listing_end_date = listing_end_date;
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
    return this.image_url;
  }

  setTheLinkToThePicture(newImageURL) {
    this.image_url = newImageURL;
  }

  retrieveTheLocation() {
    return this.location;
  }

  setTheLocation(newLocation) {
    this.location = newLocation;
  }

  retrieveTheAmountOfGuests() {
    return this.number_guests;
  }

  setTheAmountOfGuests(newAmountOfGuests) {
    this.number_guests = newAmountOfGuests;
  }

  setThePrice(newPrice) {
    this.price = newPrice;
  }

  getThePrice() {
    return this.price;
  }

  setTheStartDateOfThisListing(newStartDate) {
    this.listing_start_date = newStartDate;
  }  

  getTheStartDateOfThisListing() {
    return this.listing_start_date;
  }

  setTheEndDateOfThisListing(newEndDate) {
    this.listing_end_date;
  }

  getTheEndDateOfThisListing() {
    return this.listing_end_date;
  }
}
