<template>
  <v-container fluid>
    <HeaderBar/>
    <div class="masonry-container">
          <div v-masonry item-selector=".item" transition-duration="0.5s">
            <v-row dense no-gutters>

              <v-col :sm="totalWidth" v-for="each in artworks" v-masonry-tile class="item" :key="each.id">
                <ImageTile :imgSrc="each.url" :title="each.title" :desc="each.desc"/>
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

Vue.use(VueMasonryPlugin);
Vue.use(VueAxios,axios);

export default {
  name:'discover-page',
  components:{ImageTile,HeaderBar},
  data:()=>({
    totalWidth:4,
    artworks:[],
    btnWidth:2
  }),

  created(){
    axios.get("https://api.unsplash.com/photos?page=1&per_page=24&client_id=byOHc2uR5blXApgsdmB2HXqDUco4dtPAAvAfnAmQJDM")
    .then(res=>{
      let data=res.data;
      for (var i=0;i<data.length;i++){
        let each={
          id:i,
          title:"Art Title",
          desc:"Cras faucibus lorem fermentum odio viverra gravida vitae nec massa. Donec ut ultrices dui. Quisque vitae dolor et sem posuere pellentesque. Vestibulum tincidunt nulla quis rhoncus ultrices. Suspendisse neque neque, aliquet at neque a, finibus pretium sapien. Fusce sagittis tortor in commodo viverra. Duis lacinia risus sit amet scelerisque ultricies. Mauris in neque non erat molestie feugiat. Vivamus vel dictum libero. Maecenas mi nunc, consequat ac erat imperdiet, eleifend efficitur neque. Vestibulum id augue et turpis tincidunt sagittis id ac lorem. Integer malesuada magna vel lobortis lacinia. Etiam vitae nibh magna. Nullam dolor libero, lobortis a nisl vel, pretium tincidunt urna. Nullam facilisis diam ut pharetra interdum. Proin eu lorem felis. Maecenas diam sapien, lacinia in velit at, tempus dapibus magna. Morbi ultrices est nulla, faucibus malesuada urna tempus non. Integer eget orci vel lorem feugiat faucibus at et nisi. Nullam aliquam magna urna, sit amet faucibus risus elementum fringilla. Vivamus orci lacus, hendrerit et tempor eget",
          url:data[i].urls.regular
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
}
</style>