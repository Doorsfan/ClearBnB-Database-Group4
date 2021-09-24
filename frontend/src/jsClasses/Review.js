export default class Review {
  constructor(
    writtenByUser,
    timestamp,
    comment,
    rating,
    version
  ) {
    this.writtenByUser = writtenByUser,
    this.timestamp = timestamp,
    this.comment = comment,
    this.rating = rating,
    this.version = version
  }
  updateTimestamp(newTimeStamp) {
    this.timestamp = newTimeStamp;
  }
  getWhenReviewWasPosted() {
    return this.timestamp;
  }

  updateContentOfReview(newContent) {
    this.comment = newContent;
  }

  getContentOfReview() {
    return this.comment;
  }

  updateRating(newRating) {
    this.rating = newRating;
  }
  getTheRatingOfTheReview() {
    return this.rating;
  }
  getTheVersionOfTheReview() {
    return this.version;
  }
  updateTheVersionOfTheReview(newVersion) {
    this.version = newVersion;
  }
}
