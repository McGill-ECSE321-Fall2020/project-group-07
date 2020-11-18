<template >
  <v-app >
    <v-container fluid>
      <HeaderBar/>
      <router-view></router-view>
    </v-container>
  </v-app>
</template>

<script>
import Vue from 'vue'
import {VueMasonryPlugin} from 'vue-masonry'
import axios from 'axios'
import VueAxios from 'vue-axios'
import HeaderBar from "@/components/HeaderBar";

Vue.use(VueMasonryPlugin);
Vue.use(VueAxios,axios);

export default {
  name: 'App',
  components: {HeaderBar},
  data: () => ({
  }),
  watch:{
    '$route' (to) {
        document.title = to.meta.title || 'online-gallery'
      }
  }
};
</script>

<style>

</style>
