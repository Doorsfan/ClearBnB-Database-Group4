<template>
  <div class="reviewBox">
    <div class="dateBox">
      {{ year }} - {{ month }} - {{ day }} <button v-if="currentUser" @click="removeComment" class="deleteCommentButton"><i class="trashBin fas fa-trash-alt"></i></button><button v-if="currentUser && editMode" @click="saveComment" class="saveCommentButton"><i class="fas fa-save"></i></button><button v-if="currentUser && !editMode" @click="editComment" class="editCommentButton"><i class="far fa-edit"></i></button>
    </div>
    <div class="hourBox">
      <div v-if="editedComment && !removedComment" class="lastEditedDiv">Edited at: </div>{{ hour }}:{{ minute }}
    </div>
    <div v-if="!removedComment" class="usernameBox">
      <router-link @click="populateProfilePage" id="postedByLink" :to="{ name: 'profile', params: { username: author.username
        }}">{{ author.username }}</router-link> left the following review: 
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
      reviewId: this.Content.reviewId,
      author: this.Content.author,
      username: (Object.keys(this.Content.author).length == 1) ? this.Content.username : this.Content.author.username,
      comment: (this.Content.comment.includes("(Edited)")) ? this.Content.comment.substring(0,this.Content.comment.length - 8) : this.Content.comment,
      rating: this.Content.rating,
      year: this.Content.timestamp[0],
      currentUser: this.$store.getters.user ? (this.$store.getters.user.username == this.Content.author.username ? true : false) : false,
      editMode: false,
      wantedComment: '',
      month: (this.Content.timestamp[1] < 10) ? '0' + this.Content.timestamp[1] : this.Content.timestamp[1],
      day: (this.Content.timestamp[2] < 10) ? '0' + this.Content.timestamp[2] : this.Content.timestamp[2],
      hour: (this.Content.timestamp[3] < 10) ? '0' + this.Content.timestamp[3] : this.Content.timestamp[3],
      minute: (this.Content.timestamp[4] < 10) ? '0' + this.Content.timestamp[4] : this.Content.timestamp[4],
      theRoute: this.$route.path,
      editedComment: false,
      removedComment: false,
    };
  },
  methods: {
    editComment(){
      this.editMode = true;
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
          console.log(data);
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
    },
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
      if(this.editMode){
        this.rating = 5;
      }
    },
    populateProfilePage(){
      console.log(this.$route.path);
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
.lastEditedDiv{
  display:inline-block;
  margin-right: 3px;
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
  background-color: white;
}
.commentBox{
  display:block;
  height:max-content;
  min-height:20px;
}
</style>
