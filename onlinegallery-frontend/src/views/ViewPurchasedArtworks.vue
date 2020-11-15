<template>
  <v-carousel
    cycle
    hide-delimiter-background
    show-arrows-on-hover
    class = "carousel"
  >
    <v-carousel-item class = "item" contain
      v-for="(item, i) in items"
      :key="i"
      :src="item.src"
    >
    </v-carousel-item>
  </v-carousel>
</template>

<script>
import Vue from 'vue';
import axios from 'axios';
import VueAxios from 'vue-axios';
Vue.use(VueAxios,axios);

export default {
  name: 'view-pruchases',
  props:["username"],
   data () {
      return {
         items: [
          
         ],
         myPurchasedArtworks: [

         ]
      }
   },

  methods:{
    
  },
  mounted(){
    axios.get(`https://onlinegallery-backend-g7.herokuapp.com/getAllPurchases`)
      .then(res=>{

         let purchases = res.data;
         for(let i = 0; i<purchases.length; i++){

            let purchase = purchases[i];
            let artworkId;
     
            if(purchase.username == this.username){
               artworkId = purchase.artworkId;
            } else continue;

            this.myPurchasedArtworks.push(artworkId);
         }

      }).then(()=>{
         axios.get(`https://onlinegallery-backend-g7.herokuapp.com/getAllArtworks`).then(response =>{
     
            let artworks = response.data;
         
            for(let j = 0; j<artworks.length; j++){

               let info;
               for(let z = 0; z<this.myPurchasedArtworks.length; z++){
                 
                  if(artworks[j].artworkId == this.myPurchasedArtworks[z]) {
                     let imageSource;

                     axios.get(`http://og-img-repo.s3.us-east-1.amazonaws.com/${artworks[j].url}`).then(res=>{
                        imageSource = res.data;
                        console.log(imageSource);
                        info = {src: imageSource};
                        if(info!=undefined)this.items.push(info);
                     });
                  }

               }

            }

         })
      }).catch(error=>{
         console.log(error.response.data);
      })
  }

}
</script>

<style scoped>
.carousel{
   position: absolute;
   top: 50%;
   left: 50%;
   transform: translate(-50%, -40%);
   width: 60%;
}
</style>
