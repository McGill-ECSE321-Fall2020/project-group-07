<template>
  <v-container fluid>
      <div>
      <HeaderBar/>

          <div class="refresh">
              <v-btn text @click="refresh">
                <v-icon class="black-link">
                  mdi-refresh</v-icon>
              </v-btn>
          </div>

          <div class="masonry-container">

          <v-row>
            <v-col :cols="9">
              <v-row dense no-gutters v-masonry origin-left="true" horizontal-order="true" item-selector=".item">
                <v-col :sm="totalWidth" v-for="each in artworks" v-masonry-tile class="item" :key="each.id" >
                 <ImageTile :artid="each.id" :imgUrl="each.imgUrl" :artname="each.name" :artistname="each.username" :artdesc="each.description" :medium="each.medium" :dimension="each.dimension" :price="each.price" :height="each.height" @gatherID="gather"/>
                </v-col>
              </v-row>
            </v-col>

            <v-col :cols="1" class="checkout-btn">
              <v-btn fixed outlined @click="initiateCheckout" :disabled="this.addedArtworkIds.length==0">checkout</v-btn>
            </v-col>

            </v-row>
          </div>
       </div>
        <v-dialog v-model="checkoutDialog" overlay-color="white" overlay-opacity="1.0" width="600" class="pa-0">
            <CheckoutArtworkTable :ids="addedArtworkIds"/>
        </v-dialog>
  </v-container>
</template>

<script>
import Vue from 'vue';
import {VueMasonryPlugin} from 'vue-masonry';
import ImageTile from "@/components/ImageTile";
import CheckoutArtworkTable from "@/components/CheckoutArtworkTable";
import HeaderBar from "@/components/HeaderBar";
import axios from 'axios';
import VueAxios from 'vue-axios';
import imagesLoaded from 'vue-images-loaded';

Vue.use(VueMasonryPlugin);
Vue.use(VueAxios,axios);
Vue.use(imagesLoaded);

export default {
  name:'discover-page',
  components:{ImageTile,HeaderBar, CheckoutArtworkTable},
  data:()=>({
    totalWidth:4,
    artworks:[],
    btnWidth:2,
    awsAddress:"http://og-img-repo.s3.us-east-1.amazonaws.com/",
    addedArtworkIds: [],
    checkoutDialog:false,
    numAvailable:0

  }),
  
  mounted(){
    this.retrieveArtworks();
  },

  methods:{
    gather(artid){
        this.addedArtworkIds.push(artid);
    },
    initiateCheckout(){
      this.checkoutDialog=true;
    },

    retrieveArtworks(){
        let promise=[]
        promise.push(
            axios.get("https://onlinegallery-backend-g7.herokuapp.com/getAllAvailableArtwork")
            .then(res=>{
              this.numAvailable=Math.min(res.data.length,30);
            })
        )
        Promise.all(promise)
        .then(()=>{
          axios.get(`https://onlinegallery-backend-g7.herokuapp.com/retrieveRandomAvailableArtworks/${this.numAvailable}`)
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
        });
    },

    refresh(){
      this.artworks=[];
      this.retrieveArtworks();
    }
  }
}
</script>

<style scoped>

.masonry-container {
  width: 100%;
  margin-left:13%;
  margin-top: 20px;
}

.refresh{
  text-align: center;
  margin-bottom: 50px;
  padding-top: 100px;
  /*background-color: blue;*/
}

.black-link{
  color:black !important;
  text-decoration: none !important;
}
.checkout-btn{
  padding-top:2vh;
}

</style>