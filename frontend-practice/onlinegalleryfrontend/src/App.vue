<template> 
  <v-app>
    <v-container fluid>
      <div class="masonry-container">
      <div v-masonry item-selector=".item">
        <v-row dense no-gutters>
          <v-col>
      <Registration/>
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
import Registration from './components/RegistrationPage';
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(VueMasonryPlugin);
Vue.use(VueAxios,axios);

export default {
  name: 'App',

  components: {
    Registration,
  },
  data: () => ({

  }),
};
</script>
<style>
.masonry-container {
  width: 75%;
  margin:auto;
}
</style>