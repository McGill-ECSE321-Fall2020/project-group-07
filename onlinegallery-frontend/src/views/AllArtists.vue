<template>
  <v-container fluid>
      <div>
<!--      <HeaderBar/>-->
<!--          <div class="masonry-container">-->
<!--            <v-row dense no-gutters v-masonry origin-left="true" horizontal-order="true" item-selector=".item">-->
<!--              <v-col :cols="totalWidth" v-for="each in artists" v-masonry-tile class="item" :key="each.id" >-->
<!--               <ArtistTile :artid="each.id" :imgUrl="each.imgUrl" :artistname="each.username" :artdesc="each.description" :height="each.height"/>-->
<!--              </v-col>-->
<!--            </v-row>-->
<!--          </div>-->
       </div>
  </v-container>
</template>

<script>
import Vue from 'vue';
import {VueMasonryPlugin} from 'vue-masonry';
// import ArtistTile from "@/components/ArtistTile";
// import HeaderBar from "@/components/HeaderBar";
import axios from 'axios';
import VueAxios from 'vue-axios';
import imagesLoaded from 'vue-images-loaded';

Vue.use(VueMasonryPlugin);
Vue.use(VueAxios,axios);
Vue.use(imagesLoaded);

export default {
  name: 'artist-page',
  // components:{ArtistTile,HeaderBar},
  data:()=>({
    totalWidth:3,
    artists:[],
  }),
  mounted(){
    axios.get("https://onlinegallery-backend-g7.herokuapp.com/getAllArtists")
    .then(res=>{
      console.log(res);
      // let data=res.data;
      // for (let i=0;i<data.length;i++){
      //   let eachArtist={
      //
      //   }
      // }
    })

  //  axios.get("https://api.unsplash.com/collections/9248817/photos?per_page=16&client_id=byOHc2uR5blXApgsdmB2HXqDUco4dtPAAvAfnAmQJDM")
  //   .then(res=>{
  //     let data=res.data;
  //     for (var i=0;i<data.length;i++){
  //       let each={
  //         id:i,
  //         imgUrl:data[i].urls.regular,
  //         username:"Artist Username",
  //         description:"artist biography",
  //         height:Math.floor(300)
  //       }
  //       this.artists.push(each);
  //     }
  //   })
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
