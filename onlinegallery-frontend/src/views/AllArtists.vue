<template>
  <v-container fluid>
      <div>
      <HeaderBar/>
          <div class="masonry-container">
            <v-row dense no-gutters >
              <v-col :cols="totalWidth" v-for="each in artists" :key="each.id" >
                <ArtistTile :artistId="each.artistId" :imgUrl="each.url" :firstname="each.firstname" :lastname="each.lastname"/>
              </v-col>
            </v-row>
          </div>
       </div>
  </v-container>
</template>

<script>
import Vue from 'vue';
import {VueMasonryPlugin} from 'vue-masonry';
import ArtistTile from "@/components/ArtistTile";
import HeaderBar from "@/components/HeaderBar";
import axios from 'axios';
import VueAxios from 'vue-axios';
import imagesLoaded from 'vue-images-loaded';

Vue.use(VueMasonryPlugin);
Vue.use(VueAxios,axios);
Vue.use(imagesLoaded);

export default {
  name: 'artist-page',
  components:{ArtistTile,HeaderBar},
  data:()=>({
    totalWidth:2,
    artists:[],
  }),
  mounted(){
    axios.get("https://onlinegallery-backend-g7.herokuapp.com/getAllArtists")
    .then(res=>{
      let data=res.data;
      for (let i=0;i<data.length;i++){
        let eachData=data[i];
        axios.get(eachData.url)
            .then(res=>{
              let profilePic;
              profilePic=res.data;

              let eachArtist={
                artistId:eachData.artistId,
                firstname:eachData.firstname,
                lastname:eachData.lastname,
                profileId:eachData.profileId,
                rating:eachData.rating,
                selfDescription:eachData.selfDescription,
                url:profilePic,
                username:eachData.username
              }
              this.artists.push(eachArtist);
            });
      }
    })

  }
}

</script>

<style scoped>
.masonry-container {
  width: 75%;
  margin:auto;
  margin-top: 100px;
}

</style>
