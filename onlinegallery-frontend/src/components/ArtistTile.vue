<template>
  <v-container>
        <v-card @click="gotoArtist" class="m-0 card">
             <v-img
                 :src="imgUrl"
                 :height="height"
             />
        </v-card>
  </v-container>
</template>

<script>
export default {
  name: 'image-tile',
  props: ["artistid","imgUrl","height"],
  data(){
    return{
      hover:false,
      clicked:false,
      thumbHeight:null
    }
  },
  methods:{
    gotoArtist(){
        this.$router.push({name:"/artist-portfolio", params: {artistid:"2"}});     // to be replaced by username from the artist login form
    }
  }
};
</script>

<style>
.active{
  cursor: pointer;
}

.title{
  font-family: Roboto;
  text-align: center;
}

.card{
  border: 1px solid black !important;
}
</style>