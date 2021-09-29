<template>
  <div class="reviewBox">
    <div class="dateBox">
      {{ year }} - {{ month }} - {{ day }}
    </div>
    <div class="hourBox">
      {{ hour }}:{{ minute }}
    </div>
    <div class="usernameBox">
      <router-link id="postedByLink" :to="{ name: 'profile', params: { author: author
        }}">{{ username }}</router-link> left the following review: 
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
      year: this.Content.timestamp.year,
      month: (this.Content.timestamp.monthValue < 10 ? '0' + this.Content.timestamp.monthValue : this.Content.timestamp.monthValue),
      day: this.Content.timestamp.dayOfMonth,
      hour: this.Content.timestamp.hour,
      minute: this.Content.timestamp.minute,
      username: this.Content.author.username,
      rating: this.Content.rating,
      comment: this.Content.comment
    };
  },
  methods: {
  },
  async mounted() {
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
