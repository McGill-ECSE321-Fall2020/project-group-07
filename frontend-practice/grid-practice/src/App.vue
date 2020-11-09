<template >
  <v-app>
  <v-container class="gray darken-4">

    <div class="board">
      <v-row v-masonry item-selector=".item" dense no-gutters>

        <v-col :cols="totalWidth" :md="eachWidth" v-masonry-tile class="item" v-for="each in artworks" :key="each.id">
          <ImageTile :imgSrc="each.url" :title="each.title" :desc="each.desc"/>
        </v-col>
      </v-row>
    </div>

  </v-container>
  </v-app>
</template>

<script>
import Vue from 'vue'
import {VueMasonryPlugin} from 'vue-masonry'
import ImageTile from "@/components/ImageTile";
import {axios} from 'axios';

Vue.use(VueMasonryPlugin);
Vue.use(axios);

export default {
  name: 'App',
  components: {ImageTile},
  data: () => ({
    totalWidth:8,
    eachWidth:3,
    randomUrl:null,
    artworks:[
      {
        id:1,
        title: "Art Title",
        desc:"Onteger dictum magna ut tellus finibus fringilla. Duis velit enim, viverra id nisl a, hendrerit cursus turpis. Vestibulum consectetur dictum elit, eget hendrerit lorem aliquet ac. Maecenas vitae nisi vulputate, malesuada dui quis, dapibus arcu. Sed sit amet lobortis quam. Morbi ac sagittis nibh, id ullamcorper elit. Suspendisse potenti. Nullam ultrices consequat semper. Nam at dui interdum augue euismod sollicitudin quis quis risus. Donec tempor ornare volutpat. Donec elementum odio quis volutpat dapibus. Curabitur pellentesque, velit a porta ullamcorper, orci neque venenatis leo, vel aliquam libero tellus et nisl. Duis eu volutpat odio, id sagittis sem. Quisque eu ligula in quam tincidunt vestibulum. Fusce pretium tempor rhoncus. Donec ullamcorper sem ut lacinia pretium.",
        url:"https://upload.wikimedia.org/wikipedia/commons/b/ba/Vincent_van_Gogh_-_Wheatfield_with_crows_-_Google_Art_Project.jpg"
      }
    ]
  }),
  // methods:{
  //   getPhoto(){
  //     axios.get("https://api.unsplash.com/photos/random?client_id=byOHc2uR5blXApgsdmB2HXqDUco4dtPAAvAfnAmQJDM")
  //     .then(res=>{
  //       console.log(res);
  //     })
  //   }
  // }
};
</script>

<style>
.board{
  width:66.66666%;
  margin:auto;
}
</style>