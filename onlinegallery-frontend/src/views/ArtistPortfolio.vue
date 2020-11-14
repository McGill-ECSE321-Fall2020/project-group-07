<template>
  <v-container fluid>
      <div>
      <HeaderBar/>
      <div class="artist-details">

      <v-card
        class="mx-auto"
        max-wide="344"
        outlined
      >
       <v-list-item three-line>
      <v-list-item-content>
        <div class="overline mb-4">
          Artist Information
        </div>
        <v-list-item-title class="headline mb-1">
          {{artistFirstname}} {{artistLastname}}
        </v-list-item-title>
        <v-list-item-subtitle>{{artistDesc}}</v-list-item-subtitle>
      </v-list-item-content>

      <v-list-item-avatar
      tile
      size="175">
      <v-img :src="profileUrl"/>
      </v-list-item-avatar>
    </v-list-item>
    </v-card>


     </div>
          <div class="masonry-container">

            <v-row dense no-gutters v-masonry origin-left="true" horizontal-order="true" item-selector=".item">
              <v-col :sm="totalWidth" v-for="each in orderedArtworks" v-masonry-tile class="item" :key="each.id" >
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
import underscore from "vue-underscore";

Vue.use(VueMasonryPlugin);
Vue.use(VueAxios,axios);
Vue.use(imagesLoaded);
Vue.use(underscore);

export default {
  name:'artist-portfolio',
  components:{ImageTile,HeaderBar},
  props:["artistid", "artid"],
  data:()=>({
    totalWidth:4,
    artworks:[],
    backendInfo:null,
    btnWidth:2,
    awsAddress:"http://og-img-repo.s3.us-east-1.amazonaws.com/",
    profileUrl:"",
    artistFirstname:"",
    artistLastname:"",
    artistDesc:"",
    addedArtworkIds:[]
  }),
  mounted(){
    axios.get(`https://onlinegallery-backend-g7.herokuapp.com//getAvailableArtworkByArtistId/${this.$props.artistid}`)
    .then((res)=>{
      for (let i=0;i<res.data.length;i++){
        let data=res.data[i];
        let awsUrl=this.awsAddress.concat(data.url);
        axios.get(awsUrl)
        .then(awsRes=>{
            let eachArt= {
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
            this.artworks.push(eachArt);
        })
      }
    })
    .catch((error)=>{
      console.log(error);
    })

    axios.get(`https://onlinegallery-backend-g7.herokuapp.com//getArtistById/${this.$props.artistid}`)
    .then(res=>{
        axios.get(res.data.url)
        .then(res=>{
          this.profileUrl=res.data;
        })
        .catch(error=>{
          console.log(error);
        });

        this.artistFirstname=res.data.firstname;
        this.artistLastname=res.data.lastname;
        this.artistDesc=res.data.selfDescription;
    })
  },
  methods:{
    gather(artid){
      this.addedArtworkIds.push(artid);
    }
  },
  computed:{
    orderedArtworks: function(){
      return this.$_.sortBy(this.artworks,"id").reverse();
    }
  }
}
</script>

<style scoped>

.artist-details {
  width: 73.15%;
  margin:auto;
  margin-top: 100px;
}

.masonry-container {
  width: 75%;
  margin:auto;
  margin-top: 20px;
}

.refresh{
  text-align: center;
  margin-bottom: 20px;
}

.black-link{
  color:black !important;
  text-decoration: none !important;
}
</style>