<template>
    <div class="placeholder">
      <div> all artists page</div>
      <v-btn @click="gotoArtist">
        artist
      </v-btn>
    </div>
</template>

<script>

export default {
  name: 'artist-page',
  components: {

  },
  methods:{
      gotoArtist(){
        this.$router.push({name:"/artist-portfolio", params: {artistid:"2"}});     // to be replaced by username from the artist login form
    }
  }
}
</script>

<style>

.placeholder{
  padding-top: 300px;
  text-align: center;
}

</style>
