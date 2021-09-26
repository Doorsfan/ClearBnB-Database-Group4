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
        }}">{{ author }}</router-link> left the following review: 
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
      year: this.Content.postedAtYear,
      month: this.Content.postedAtMonth < 10 ? '0' + this.Content.postedAtMonth : this.Content.postedAtMonth,
      day: this.Content.postedAtDay < 10 ? '0' + this.Content.postedAtDay : this.Content.postedAtDay,
      hour: this.Content.postedAtHour < 10 ? '0' + this.Content.postedAtHour : this.Content.postedAtHour,
      minute: this.Content.postedAtMinute < 10 ? '0' + this.Content.postedAtMinute : this.Content.postedAtMinute,
      second: this.Content.postedAtSecond,
      author: this.Content.author,
      rating: this.Content.rating,
      comment: this.Content.comment
    };
  },
  methods: {
  },
  async mounted() {
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
  background-color:white;
}
.commentBox{
  display:block;
  height:max-content;
  min-height:20px;
}
</style>
