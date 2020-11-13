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
import ArtistPortalPage from "@/views/ArtistPortalPage";
import ArtistPortfolio from "@/views/ArtistPortfolio";

Vue.use(VueRouter);
Vue.config.productionTip = false;

const router = new VueRouter({
  routes:[
    {path:'/',component:Home, meta:{title:"Home"}},
    {path:'/home',component:Home, meta:{title:"Home"}},
    {path:"/about",component: About, meta:{title:"About"}},
    {path:'/discover',component:DiscoverPage, meta:{title:"Discover"}},
    {path:'/all-artists',component:AllArtists, meta:{title:"All Artists"}},
    {path:"/customer-login", component: CustomerLogin, meta:{title:"Customer Login"}},
    {path:"/artist-login", component:ArtistLogin,meta:{title:"Artist Login"}},
    {path:"/register", component: Register, meta:{title:"Register"}},
    {path:"/artist-portal", name:"/artist-portal", component:ArtistPortalPage, props: true, meta:{title:"Artist Portal"}},
    {path:"/artist-portfolio", name:"/artist-portfolio", component:ArtistPortfolio, props: true, meta:{title:"Artist Portfolio"}}

  ],
  mode:'history'
})

new Vue({
  vuetify,
  router,
  render: h => h(App)
}).$mount('#app')
