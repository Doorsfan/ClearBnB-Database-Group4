<template>
  <div class="reviewBox">
    <div class="dateBox">
      {{ year }} - {{ month }} - {{ day }} <button v-if="currentUser" @click="removeComment" class="deleteCommentButton"><i class="trashBin fas fa-trash-alt"></i></button><button v-if="currentUser && editMode" @click="saveComment" class="saveCommentButton"><i class="fas fa-save"></i></button><button v-if="currentUser && !editMode" @click="editComment" class="editCommentButton"><i class="far fa-edit"></i></button>
    </div>
    <div class="hourBox">
      {{ hour }}:{{ minute }}
    </div>
    <div class="usernameBox">
      <router-link id="postedByLink" :to="{ name: 'profile', params: { author: author
        }}">{{ author }}</router-link> left the following review: 
    </div>
    <div class="ratingBox">
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
      <input v-if="editMode" type="text" :placeholder="comment">
    </div>
  </div>
</template>
<script>
export default {
  props: ['Content'],
  data() {
    return {
      currentUser: this.$store.getters.user ? (this.$store.getters.user.username == this.Content.author ? true : false) : false,
      year: this.Content.postedAtYear,
      month: this.Content.postedAtMonth < 10 ? '0' + this.Content.postedAtMonth : this.Content.postedAtMonth,
      day: this.Content.postedAtDay < 10 ? '0' + this.Content.postedAtDay : this.Content.postedAtDay,
      hour: this.Content.postedAtHour < 10 ? '0' + this.Content.postedAtHour : this.Content.postedAtHour,
      minute: this.Content.postedAtMinute < 10 ? '0' + this.Content.postedAtMinute : this.Content.postedAtMinute,
      second: this.Content.postedAtSecond,
      author: this.Content.author,
      rating: this.Content.rating,
      comment: this.Content.comment,
      editMode: false
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
    saveComment(){
      this.editMode = false;
      //temp
    },
    removeComment(){
      console.log("temp");
    }
  },
  async mounted() {
  }
};
</script>
<style scoped>
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
