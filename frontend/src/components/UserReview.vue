<template>
  <div class="reviewBox">
    <div class="dateBox">
      {{ year }} - {{ month }} - {{ day }}
    </div>
    <div class="hourBox">
      {{ hour }}:{{ minute }}
    </div>
    <div class="usernameBox">
      <router-link id="postedByLink" :to="{ name: 'profile', params: { username: author.username
        }}">{{ author.username }}</router-link> left the following review: 
    </div>
    <div class="ratingBox">
      <span>&#11088;</span>
      <span v-if="rating > 1">&#11088;</span>
      <span v-if="rating > 2">&#11088;</span>
      <span v-if="rating > 3">&#11088;</span>
      <span v-if="rating > 4">&#11088;</span>
    </div>
    <div class="commentBox">
      {{ comment }}
    </div>
  </div>
</template>
<script>
export default {
  props: ['Content'],
  data() {
    return {
      author: this.Content.author,
      username: this.Content.author.username,
      comment: this.Content.comment,
      rating: this.Content.rating,
      year: this.Content.timestamp[0],
      month: (this.Content.timestamp[1] < 10) ? '0' + this.Content.timestamp[1] : this.Content.timestamp[1],
      day: (this.Content.timestamp[2] < 10) ? '0' + this.Content.timestamp[2] : this.Content.timestamp[2],
      hour: (this.Content.timestamp[3] < 10) ? '0' + this.Content.timestamp[3] : this.Content.timestamp[3],
      minute: (this.Content.timestamp[4] < 10) ? '0' + this.Content.timestamp[4] : this.Content.timestamp[4]
    };
  },
  methods: {
  },
  async mounted() {
    console.log("THE CONENT:");
    console.log(this.Content);
  }
};
</script>
<style scoped>
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
