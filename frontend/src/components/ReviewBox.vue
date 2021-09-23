<template>
  <div class="reviewBox">
    {{ unformattedTimeOfPosting.getFullYear() + '-' + (unformattedTimeOfPosting.getMonth() < 10 ? '0' + unformattedTimeOfPosting.getMonth() : unformattedTimeOfPosting.getMonth()) + '-' + unformattedTimeOfPosting.getDate() }} 
    {{ (unformattedTimeOfPosting.getHours() < 10 ? '0' + unformattedTimeOfPosting.getHours() : unformattedTimeOfPosting.getHours()) + ':' + (unformattedTimeOfPosting.getMinutes() < 10 ? '0' + unformattedTimeOfPosting.getMinutes() : unformattedTimeOfPosting.getMinutes()) }} 
    <div class="usernameBox">
      <router-link id="postedByLink" :to="{ name: 'profile', params: { username: userName
        }}">{{ userName }}</router-link> left the following review: 
    </div>
    <div class="ratingBox">
      <span>&#11088;</span>
      <span v-if="rating > 1">&#11088;</span>
      <span v-if="rating > 2">&#11088;</span>
      <span v-if="rating > 3">&#11088;</span>
      <span v-if="rating > 4">&#11088;</span>
    </div>
    <div class="contentBox">
      {{ content }}
    </div>
  </div>
</template>
<script>
export default {
  props: ['Content'],
  data() {
    return {
      unformattedTimeOfPosting: new Date(this.Content.timestamp),
      userName: this.Content.writtenByUser,
      rating: this.Content.rating,
      content: this.Content.comment
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
  background-color:white;
}
</style>
