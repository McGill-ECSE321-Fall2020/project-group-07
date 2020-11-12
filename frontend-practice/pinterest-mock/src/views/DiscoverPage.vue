<template>
  <v-container fluid>
      <div>
      <HeaderBar/>
          <div class="masonry-container">

            <div class="refresh">
              <v-btn text>
                <v-icon class="black-link">
                  mdi-refresh
                </v-icon>
              </v-btn>
            </div>

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
  name:'discover-page',
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