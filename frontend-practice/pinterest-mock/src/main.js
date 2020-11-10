import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import VueRouter from "vue-router";
import DiscoverPage from "@/views/DiscoverPage";

Vue.use(VueRouter);
Vue.config.productionTip = false;

const router = new VueRouter({
  routes:[
    {path:'/discover',component:DiscoverPage},
    {path:'/',component:App}
  ]
})

new Vue({
  vuetify,
  router,
  render: h => h(App)
}).$mount('#app')
