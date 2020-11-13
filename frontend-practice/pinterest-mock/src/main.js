import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import VueRouter from "vue-router";

import Home from "@/views/Home";
import About from "@/views/About"
import DiscoverPage from "@/views/DiscoverPage";
//import AllArtists from "@/views/AllArtists";
import CustomerLogin from "@/views/CustomerLogin";
import ArtistLogin from "@/views/ArtistLogin";
import Register from "@/views/Register";
import ArtistPage from "@/views/ArtistPage";

Vue.use(VueRouter);
Vue.config.productionTip = false;

const router = new VueRouter({
  routes:[
    {path:'/home',component:Home},
    {path:"/about",component: About},
    {path:'/discover',component:DiscoverPage},
    {path:'/all-artists',component:ArtistPage},
    {path:"/customer-login", component: CustomerLogin},
    {path:"/artist-login", component:ArtistLogin},
    {path:"/register", component: Register},
    {path:"/artistpage", name:"/artistpage", component:ArtistPage, props: true}

  ],
  mode:'history'
})

new Vue({
  vuetify,
  router,
  render: h => h(App)
}).$mount('#app')
