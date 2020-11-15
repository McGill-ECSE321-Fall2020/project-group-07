<template>
  <v-container fluid>
      <div>
      <HeaderBar/>
          <div class="masonry-container">

            <div class="refresh">
              <v-btn text @click="refresh">
                <v-icon class="black-link">
                  mdi-refresh</v-icon>
              </v-btn>
            </div>

            <v-row dense no-gutters v-masonry origin-left="true" horizontal-order="true" item-selector=".item">
              <v-col :sm="totalWidth" v-for="each in artworks" v-masonry-tile class="item" :key="each.id" >
               <ImageTile :artid="each.id" :imgUrl="each.imgUrl" :artname="each.name" :artistname="each.username" :artdesc="each.description" :medium="each.medium" :dimension="each.dimension" :price="each.price" :height="each.height" @gatherID="gather"/>
              </v-col>
            </v-row>
          </div>
       </div>
  </v-container>
</template>

<script>
import Vue from 'vue';
import {VueMasonryPlugin} from 'vue-masonry';
import ImageTile from "@/components/ImageTile";
import HeaderBar from "@/components/HeaderBar";
import axios from 'axios';
import VueAxios from 'vue-axios';
import imagesLoaded from 'vue-images-loaded';

Vue.use(VueMasonryPlugin);
Vue.use(VueAxios,axios);
Vue.use(imagesLoaded);

export default {
  name:'discover-page',
  components:{ImageTile,HeaderBar},
  data:()=>({
    number: "",
    totalWidth:4,
    artworks:[],
    btnWidth:2,
    awsAddress:"http://og-img-repo.s3.us-east-1.amazonaws.com/",
    addedArtworkIds: []
  }),
  
  created(){
    axios.get("https://onlinegallery-backend-g7.herokuapp.com/getAllArtworks")
    .then(response=>{
      this.number = response.data.length;
    });
    axios.get(`https://onlinegallery-backend-g7.herokuapp.com/retrieveRandomAvailableArtworks/${this.number}`)
    .then(res=>{
      for (let i=0;i<res.data.length;i++){
       let data=res.data[i];
        let awsUrl=this.awsAddress.concat(data.url);
        axios.get(awsUrl)
        .then(awsRes=>{
          let each= {
            id: data.artworkId,
            imgUrl: awsRes.data,
            name: data.name,
            username: data.username,
            dimension: data.dimension,
            description: data.description,
            medium: data.medium,
            price: data.price,
            weight: data.weight,
            height: data.url.split("_")[4]
        }
        this.artworks.push(each);
       })
      }
    })
  },
    methods:{
      gather(artid){
        this.addedArtworkIds.push(artid);
    },
      refresh(){
        this.artworks = [];
         axios.get(`https://onlinegallery-backend-g7.herokuapp.com/retrieveRandomAvailableArtworks/${this.number}`)
       .then(res=>{
         for (let i=0;i<res.data.length;i++){
          let data=res.data[i];
           let awsUrl=this.awsAddress.concat(data.url);
           axios.get(awsUrl)
            .then(awsRes=>{
             let each= {
             id: data.artworkId,
             imgUrl: awsRes.data,
             name: data.name,
             username: data.username,
             dimension: data.dimension,
             description: data.description,
             medium: data.medium,
             price: data.price,
              weight: data.weight,
             height: data.url.split("_")[4]
          }
        this.artworks.push(each);
       })
      }
    })
    .catch((error)=>{
      console.log(error);
    })
    }
  }
}
</script>

<style scoped>
.masonry-container {
  width: 75%;
  margin:auto;
  margin-top: 100px;
}

.refresh{
  text-align: center;
  margin-bottom: 50px;
}

.black-link{
  color:black !important;
  text-decoration: none !important;
}
</style>