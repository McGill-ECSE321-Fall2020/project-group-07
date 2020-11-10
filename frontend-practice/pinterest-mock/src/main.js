import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import VueRouter from "vue-router";

import Home from "@/views/Home";
import About from "@/views/About"
import DiscoverPage from "@/views/DiscoverPage";
import AllArtists from "@/views/AllArtists";
import CustomerLogin from "@/views/CustomerLogin";
import ArtistLogin from "@/views/ArtistLogin";
import Register from "@/views/Register";

Vue.use(VueRouter);
Vue.config.productionTip = false;

const router = new VueRouter({
  routes:[
    {path:'/home',component:Home},
    {path:"/about",component: About},
    {path:'/discover',component:DiscoverPage},
    {path:'/all-artists',component:AllArtists},
    {path:"/customer-login", component: CustomerLogin},
    {path:"/artist-login", component:ArtistLogin},
    {path:"/register", component: Register},

  ],
  mode:'history'
})

new Vue({
  vuetify,
  router,
  render: h => h(App)
}).$mount('#app')
