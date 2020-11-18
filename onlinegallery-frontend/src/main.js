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
import AdminLogin from "@/views/AdminLogin";
import Register from "@/views/Register";
import ArtistPortalPage from "@/views/ArtistPortalPage";
import AdminPortalPage from "@/views/AdminPortalPage";
import CustomerPortalPage from "@/views/CustomerPortalPage";
import ArtistPortfolio from "@/views/ArtistPortfolio";
import Summary from "@/views/Summary";
import CustomerSummary from "@/views/CustomerSummary";
import ViewPurchasedArtworks from "@/views/ViewPurchasedArtworks";
import PaymentProcessed from "@/views/PaymentProcessed";
import Help from "@/views/Help";

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
    {path:"/admin-login", component:AdminLogin,meta:{title:"Admin Login"}},
    {path:"/register", component: Register, meta:{title:"Register"}},
    {path:"/artist-portal", name:"/artist-portal", component:ArtistPortalPage, props: true, meta:{title:"Artist Portal"}},
    {path:"/admin-portal", name:"/admin-portal", component:AdminPortalPage, props: true, meta:{title:"Admin Portal"}},
    {path:"/customer-portal", name:"/customer-portal", component:CustomerPortalPage, props: true, meta:{title:"Customer Portal"}},
    {path:"/gallery-summary", name:"/gallery-summary", component:Summary, meta:{title:"Summary"}},
    {path:"/artist-portfolio", name:"/artist-portfolio", component:ArtistPortfolio, props: true, meta:{title:"Artist Portfolio"}},
    {path:"/customer-summary", name:"/customer-summary", component:CustomerSummary, props: true, meta:{title:"Summary"}},
    {path:"/view-purchases", name: "/view-purchases", component:ViewPurchasedArtworks, props: true, meta:{tile:"Purchases"}},
    {path:"/payment-processed", name: "/payment-processed", component:PaymentProcessed, props: true, meta:{tile:"Thank You"}},
    {path:"/help", name: "/hep", component:Help, props: true, meta:{tile:"help"}}


  ],
  mode:'history'
})

new Vue({
  vuetify,
  router,
  render: h => h(App)
}).$mount('#app')
