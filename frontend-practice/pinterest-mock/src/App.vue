<template >
  <v-app>
    <v-container class="underlay" fluid>
      <div class="masonry-container">
      <div v-masonry item-selector=".item">
        <v-row dense no-gutters>
          <v-col :sm="totalWidth" v-for="each in artworks" v-masonry-tile class="item" :key="each.id">
            <ImageTile :imgSrc="each.url" :title="each.title" :desc="each.desc"/>
          </v-col>
        </v-row>
      </div>
      </div>
    </v-container>
  </v-app>
</template>

<script>
import Vue from 'vue'
import {VueMasonryPlugin} from 'vue-masonry'
import ImageTile from "@/components/ImageTile";
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(VueMasonryPlugin);
Vue.use(VueAxios,axios);

export default {
  name: 'App',
  components: {ImageTile},
  data: () => ({
    totalWidth:4,
    artworks:[]
  }),

  created() {
    axios.get("https://api.unsplash.com/photos?page=1&per_page=24&client_id=byOHc2uR5blXApgsdmB2HXqDUco4dtPAAvAfnAmQJDM")
    .then(res=>{
      let data=res.data;
      for (var i=0;i<data.length;i++){
        let each={
          id:i,
          title:"Art Title",
          desc:"Nam hendrerit lectus nec risus tempor, vitae convallis nibh elementum. Quisque nulla nulla, facilisis in hendrerit placerat, dictum et augue. Donec eu tortor hendrerit, feugiat dolor vel, molestie velit. Nulla dapibus feugiat sem nec condimentum. Nullam lorem orci, placerat eget rutrum non, molestie ut lorem. Mauris commodo metus vel turpis accumsan, quis ultricies sem sagittis. Proin bibendum orci a neque hendrerit, eleifend vestibulum felis porta. Praesent in libero elit. Mauris ac volutpat lectus. Integer et orci nec metus tincidunt bibendum. Sed eget nisl vitae risus vehicula dignissim sed ut eros. Aliquam id ipsum quam. Sed ultricies posuere cursus. In hac habitasse platea dictumst. Pellentesque eget molestie turpis",
          url:data[i].urls.regular
        }
        this.artworks.push(each);
      }

    })
  }
};
</script>

<style>
.masonry-container {
  width: 75%;
  margin:auto;
}

.underlay{
  background-color: #DFDFDF;
}
</style>