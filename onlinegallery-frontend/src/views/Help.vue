<template>
    <div class="about">
        <p class="text-center text-sm-subtitle-1">Help</p>
        <v-divider class="pb-8"/>
        <v-card-text> discover randomly selected artworks from our <span @click="goToDiscover" class="text-decoration-underline">Discover</span> page</v-card-text>
        <v-card-text> meet all our artists from our <a @click="gotoArtist" class="text-decoration-underline">Artists</a> page</v-card-text>
        <v-card-text> view high resolution photos of our artwork and start shopping by clicking on their thumbnails </v-card-text>
        <v-card-text> customers, review your purchases in the <a @click="goToCustPortal" class="text-decoration-underline">Portal</a> page</v-card-text>
        <v-card-text> artists, edit your profiles and upload your art in the <a @click="goToArtistPortal" class="text-decoration-underline">Portal</a> page</v-card-text>
    </div>
</template>

<script>

export default {
  name: 'help',
  components: {

  },
  methods:{
    handleClick(){
      this.$router.push("/customer-login");
    },
    goToDiscover(){
      this.$router.push("/discover");
    },
    gotoArtist(){
      this.$router.push("/all-artists");
    },
    goToCustPortal(){
      this.$router.push("/customer-login");
    },
    goToArtistPortal(){
      this.$router.push("/artist-login");
    }
  }
}
</script>

<style>

.about{
  width:65%;
  margin-left: auto;
  margin-right: auto;
  margin-top:200px;
}

</style>
