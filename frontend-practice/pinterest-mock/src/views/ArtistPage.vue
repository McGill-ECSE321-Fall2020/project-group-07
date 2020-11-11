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
          Da Vinci, Leonardo
        </v-list-item-title>
        <v-list-item-subtitle>Painted the Mona Lisa! Renown artist from some centuries ago. Also a teenage mutant ninja turtle kawabunga</v-list-item-subtitle>
      </v-list-item-content>

      <v-list-item-avatar
      tile
      size="175">
      <v-img src="https://curiosmos.com/wp-content/uploads/2018/10/Future-human-Face.png"/>
      </v-list-item-avatar>
    </v-list-item>
    </v-card>
     </div>
          <div class="masonry-container">

            <v-row dense no-gutters v-masonry origin-left="true" horizontal-order="true" item-selector=".item">
              <v-col :sm="totalWidth" v-for="each in artworks" v-masonry-tile class="item" :key="each.id" >
               <ImageTile :artid="each.id" :imgUrl="each.imgUrl" :artname="each.name" :artistname="each.username" :artdesc="each.description" :medium="each.medium" :dimension="each.dimension" :price="each.price" :height="each.height"/>
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
  name:'artist-page',
  components:{ImageTile,HeaderBar},
  data:()=>({
    totalWidth:4,
    artworks:[],
    btnWidth:2
  }),
  created(){
    axios.get("https://api.unsplash.com/collections/9248817/photos?per_page=16&client_id=byOHc2uR5blXApgsdmB2HXqDUco4dtPAAvAfnAmQJDM")
    .then(res=>{
      let data=res.data;
      for (var i=0;i<data.length;i++){
        let each={
          id:i,
          imgUrl:data[i].urls.regular,
          name:"Art Name",
          username:"artist username",
          dimension:"12in x 8in",
          description:"Cras faucibus lorem fermentum odio viverra gravida vitae nec massa. Donec ut ultrices dui. Quisque vitae dolor et sem posuere pellentesque. Vestibulum tincidunt nulla quis rhoncus ultrices. Suspendisse neque neque, aliquet at neque a, finibus pretium sapien. Fusce sagittis tortor in commodo viverra. Duis lacinia risus sit amet scelerisque ultricies. Mauris in neque non erat molestie feugiat. Vivamus vel dictum libero. Maecenas mi nunc, consequat ac erat imperdiet, eleifend efficitur neque. Vestibulum id augue et turpis tincidunt sagittis id ac lorem. Integer malesuada magna vel lobortis lacinia. Etiam vitae nibh magna. Nullam dolor libero, lobortis a nisl vel, pretium tincidunt urna. Nullam facilisis diam ut pharetra interdum. Proin eu lorem felis. Maecenas diam sapien, lacinia in velit at, tempus dapibus magna. Morbi ultrices est nulla, faucibus malesuada urna tempus non. Integer eget orci vel lorem feugiat faucibus at et nisi. Nullam aliquam magna urna, sit amet faucibus risus elementum fringilla. Vivamus orci lacus, hendrerit et tempor eget",
          medium:"oil on canvas",
          price:"100",
          height:Math.floor(Math.random() * Math.floor(500))+300
        }
        this.artworks.push(each);
      }

    })
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