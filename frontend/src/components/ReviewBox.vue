<template>
  <div class="reviewBox">
    <div class="dateBox">
      {{ year }} - {{ month }} - {{ day }} <button v-if="currentUser" @click="removeComment" class="deleteCommentButton"><i class="trashBin fas fa-trash-alt"></i></button><button v-if="currentUser && editMode" @click="saveComment" class="saveCommentButton"><i class="fas fa-save"></i></button><button v-if="currentUser && !editMode" @click="editComment" class="editCommentButton"><i class="far fa-edit"></i></button>
    </div>
    <div class="hourBox">
      <div v-if="editedComment && !removedComment" class="lastEditedDiv">Edited at: </div> 
      {{ hour }}:{{ minute }}
      </div>
    <div v-if="!removedComment" class="usernameBox">
      <router-link id="postedByLink" :to="{ name: 'profile', params: { author: author
        }}">{{ author }}</router-link> left the following review: 
    </div>
    <div v-if="comment != 'This comment has been removed.'" class="ratingBox">
      <span @click="changeToOneStarIfEditing">&#11088;</span>
      <span @click="changeToTwoStarsIfEditing">
        <span v-if="rating > 1">&#11088;</span><span v-if="rating < 2" class="fa fa-star"></span>
      </span>
      <span @click="changeToThreeStarsIfEditing">
        <span v-if="rating > 2">&#11088;</span><span v-if="rating < 3" class="fa fa-star"></span>
      </span>
      <span @click="changeToFourStarsIfEditing">
        <span v-if="rating > 3">&#11088;</span><span v-if="rating < 4" class="fa fa-star"></span>
      </span>
      <span @click="changeToFiveStarsIfEditing">
        <span v-if="rating > 4">&#11088;</span><span v-if="rating < 5" class="fa fa-star"></span>
      </span>
    </div>
    <div class="commentBox">
      <div v-if="!editMode" class="commentDiv">{{ comment }}</div>
      <input v-model="wantedComment" v-if="editMode" type="text" :placeholder="comment">
    </div>
  </div>
</template>
<script>
export default {
  props: ['Content'],
  data() {
    return {
      currentUser: this.$store.getters.user ? (this.$store.getters.user.username == this.Content.author ? true : false) : false,
      reviewId: this.Content.reviewId,
      year: this.Content.postedAtYear,
      month: (this.Content.postedAtMonth) < 10 ? '0' + this.Content.postedAtMonth : this.Content.postedAtMonth,
      day: (this.Content.postedAtDay) < 10 ? '0' + this.Content.postedAtDay : this.Content.postedAtDay,
      hour: (this.Content.postedAtHour < 10) ? '0' + this.Content.postedAtHour : this.Content.postedAtHour,
      minute: (this.Content.postedAtMinute) < 10 ? '0' + this.Content.postedAtMinute : this.Content.postedAtMinute,
      second: this.Content.postedAtSecond,
      author: this.Content.author,
      rating: this.Content.rating,
      comment: (this.Content.comment.includes("(Edited)")) ? this.Content.comment.substring(0,this.Content.comment.length - 8) : this.Content.comment,
      editMode: false,
      wantedComment: '',
      editedComment: false,
      removedComment: false,
    };
  },
  methods: {
    changeToOneStarIfEditing(){
      if(this.editMode){
        this.rating = 1;
      }
    },
    changeToTwoStarsIfEditing(){
      if(this.editMode){
        this.rating = 2;
      }
    },
    changeToThreeStarsIfEditing(){
      if(this.editMode){
        this.rating = 3;
      }
    },
    changeToFourStarsIfEditing(){
      if(this.editMode){
        this.rating = 4;
      }
    },
    changeToFiveStarsIfEditing(){
      console.log("RAN");
      if(this.editMode){
        this.rating = 5;
      }
    },
    editComment(){
      this.editMode = true;
      console.log(this.editMode);
    },
    async saveComment(){
      this.editMode = false;
      let oldComment = this.comment;
      let newComment = this.wantedComment;
      if(oldComment != newComment && newComment.length > 0){
        oldComment = newComment;
      }
      this.comment = oldComment;
      let myReview = {
        id: this.reviewId,
        comment: this.comment + " (Edited)",
        rating: this.rating
      }
      let res = await fetch('http://localhost:4000/updateReview?'
      + new URLSearchParams(myReview), {
          method: 'POST',
          mode: 'cors',
          credentials: 'include',
          body: JSON.stringify(myReview),
        }).then((response) => {
          return response.json();
        }).then((data) => {
          console.log(data.timestamp);
          this.comment = data.comment;
          this.year = data.timestamp[0];
          this.month = (data.timestamp[1] < 10) ? '0' + data.timestamp[1] : data.timestamp[1];
          this.day = (data.timestamp[2] < 10) ? '0' + data.timestamp[2] : data.timestamp[2];
          this.hour = (data.timestamp[3] < 10) ? '0' + data.timestamp[3] : data.timestamp[3];
          this.minute = (data.timestamp[4] < 10) ? '0' + data.timestamp[4] : data.timestamp[4];
          this.removedComment = false;
        });
    },
    async removeComment(){
      let myReview = {
        id: this.reviewId,
        comment: this.comment,
        rating: this.rating
      }
      let res = await fetch('http://localhost:4000/removeReview?'
      + new URLSearchParams(myReview), {
          method: 'POST',
          mode: 'cors',
          credentials: 'include',
          body: JSON.stringify(myReview),
        }).then((response) => {
          return response.json();
        }).then((data) => {
          this.comment = data.comment;
          this.year = data.timestamp[0];
          this.month = (data.timestamp[1] < 10) ? '0' + data.timestamp[1] : data.timestamp[1];
          this.day = (data.timestamp[2] < 10) ? '0' + data.timestamp[2] : data.timestamp[2];
          this.hour = (data.timestamp[3] < 10) ? '0' + data.timestamp[3] : data.timestamp[3];
          this.minute = (data.timestamp[4] < 10) ? '0' + data.timestamp[4] : data.timestamp[4];
          this.removedComment = true;
        });
    }
  },
  async mounted() {
    console.log(this.Content);
    if(this.Content.comment.includes("(Edited)")){
      this.editedComment = true;
    }
    if(this.Content.comment == "This comment has been removed."){
      this.removedComment = true;
    }
  }
};
</script>
<style scoped>
.removedDiv{
  display:inline-block;
  margin-right: 4px;
}
.lastEditedDiv{
  display:inline-block;
}
.deleteCommentButton{
  display:inline-block;
  background-color:transparent;
  outline:none;
  border:none;
  font-size: 20px;
  margin-left: 140px;
}
.editCommentButton, .saveCommentButton{
  display:inline-block;
  background-color:transparent;
  outline:none;
  border:none;
  font-size: 20px;
  margin-left: 0px;
}
.editIcon{
  margin-left: 170px;
  font-size:30px;
}
a {
  color:black;
  font-weight:bolder;
  text-decoration: none;
}
.reviewBox{
  display:block;
  margin-left:auto;
  margin-right:auto;
  width: 300px;
  margin-top: 10px;
  margin-bottom: 10px;
  outline: solid 1px black;
  padding: 2px;
  background-color:white;
  padding-left:5px;
}
.commentBox{
  display:block;
  height:max-content;
  min-height:20px;
  margin-top: 5px;
  margin-bottom: 5px;
}
</style>
