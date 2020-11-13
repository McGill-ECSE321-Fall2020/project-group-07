<template>
  <v-container>
        <v-card @click="gotoArtist" @mouseover="hover=true" @mouseleave="hover=false" class="m-0 card">
             <v-img
                 :src="imgUrl"
                 height="200"
             />
          <v-fade-transition>
            <v-overlay v-if="hover" absolute color="#737373">
              <v-card-text class="white--text text-sm-h5">{{firstname}} {{lastname}}</v-card-text>
            </v-overlay>
        </v-fade-transition>

        </v-card>

  </v-container>
</template>

<script>
export default {
  name: 'artist-tile',
  props: ["artistId","imgUrl","firstname","lastname"],
  data(){
    return{
      hover:false,
      clicked:false,
    }
  },
  methods:{
    gotoArtist(){
      console.log(this.artistId);
        this.$router.push({name:"/artist-portfolio", params: {artistid:this.artistId}});     // to be replaced by username from the artist login form
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