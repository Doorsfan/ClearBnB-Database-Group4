export default class Review {
  constructor(
    reviewId,
    author,
    postedAtYear,
    postedAtMonth,
    postedAtDay,
    postedAtHour,
    postedAtMinute,
    comment,
    rating,
    version
  ) {
    this.reviewId = reviewId,
    this.author = author,
    this.postedAtYear = postedAtYear,
    this.postedAtMonth = postedAtMonth,
    this.postedAtDay = postedAtDay,
    this.postedAtHour = postedAtHour,
    this.postedAtMinute = postedAtMinute,
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
