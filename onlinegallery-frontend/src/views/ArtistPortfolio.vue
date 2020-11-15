<template>
  <v-container fluid>
          <div>
          <HeaderBar/>
              <div class="artist-details">
            <v-card class="mx-auto rounded-lg" max-wide="344" outlined>
              <v-list-item three-line>
                <v-row  >
                  <v-col :cols="2">
                    <v-list-item-content class="pt-5">
                      <v-list-item-title class="overline mb-4 text-left">Artist Information</v-list-item-title>
                      <v-list-item-title class="headline mb-1 text-left"> {{artistFirstname}} {{artistLastname}} </v-list-item-title>
                    </v-list-item-content>
                  </v-col>

                  <v-col :cols="8">
                      <v-list-item-content class="text-justify pt-0">
                        <v-card-subtitle class="text-sm-body-2">{{artistDesc}}</v-card-subtitle>
                      </v-list-item-content>
                  </v-col>

                  <v-col :cols="2">
                        <v-img :src="profileUrl" class="rounded-lg"/>
                  </v-col>
                </v-row>
              </v-list-item>
            </v-card>
         </div>

          <div class="masonry-container">
            <v-row>
              <v-col :cols="9">
                <v-row dense no-gutters v-masonry origin-left="true" horizontal-order="true" item-selector=".item">
                  <v-col :sm="totalWidth" v-for="each in orderedArtworks" v-masonry-tile class="item" :key="each.id" >
                   <ImageTile :artid="each.id" :imgUrl="each.imgUrl" :artname="each.name" :artistname="each.username" :artdesc="each.description" :medium="each.medium" :dimension="each.dimension" :price="each.price" :height="each.height" @gatherID="gather"/>
                  </v-col>
                </v-row>
              </v-col>

              <v-col :cols="1" class="checkout-btn">
                <v-btn fixed outlined @click="initiateCheckout" :disabled="this.addedArtworkIds.length==0">my cart</v-btn>
              </v-col>

            </v-row>
          </div>
    </div>

    <v-dialog v-model="checkoutDialog" overlay-color="white" overlay-opacity="1.0" width="600" class="pa-0">
        <CheckoutArtworkTable :ids="addedArtworkIds" />
    </v-dialog>

  </v-container>
</template>


<script>
import Vue from 'vue';
import {VueMasonryPlugin} from 'vue-masonry';
import ImageTile from "@/components/ImageTile";
import HeaderBar from "@/components/HeaderBar";
import CheckoutArtworkTable from "@/components/CheckoutArtworkTable";
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
  components:{ImageTile,HeaderBar,CheckoutArtworkTable},
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
    addedArtworkIds:[],
    checkoutDialog:false
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
    },
    initiateCheckout(){
      this.checkoutDialog=true;
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
  width: 100%;
  margin-left:12.7%;
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
.checkout-btn{
  padding-top:40vh;
}
</style>