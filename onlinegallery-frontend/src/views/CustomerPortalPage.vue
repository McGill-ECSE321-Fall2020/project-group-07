<template>
   <div>
      <div class="hello"><p>Welcome, {{username}}.</p></div>
      <div class="btns">
         <button class="summary" @click="summary">Summary</button>
         <button class="carrousel" @click="viewArtworks">View Purchases</button>
      </div>
   </div>
</template>

<script>
import Vue from 'vue';
import axios from 'axios';
import VueAxios from 'vue-axios';
Vue.use(VueAxios,axios);

export default {
  name: 'customer-portal',
  props:["username"],
   data () {
      return {
         
      }
   },
   methods:{
   summary(){
      this.$router.push({name:"/customer-summary", params: {username: this.username}});
   },
   viewArtworks(){
      this.$router.push({name:"/view-purchases", params: {username: this.username}});
   }
  },
}
</script>

<style>

.hello{
   position: relative;
   font-size: 3.5vw;
   top: 200px;
   margin: 0;
   padding: 0;
   width: 100%;
   display: flex;
   justify-content: center;
   align-items: center;
   font-family: 'Maven Pro', sans-serif;
}

.btns{
   margin: 0;
   padding: 0;
   width: 100%;
   top: 200px;
   display: flex;
   position: relative;
   justify-content: space-evenly;
   align-items: center;
}

.summary{
   width: 17vw;
   margin-top: 120px;
   height: 3.5vw;
   border: 2px solid black;
   border-radius: 20px 20px 20px 20px;
   font-size: 1.25vw;
   color: white;
   background-color: black;
}

.summary:hover{

   transition: all 0.2s linear;
   color: black;
   background-color: transparent;

}

.carrousel{
   width: 17vw;
   margin-top: 120px;
   height: 3.5vw;
   border: 2px solid black;
   border-radius: 20px 20px 20px 20px;
   font-size: 1.25vw;
   color: black;
   background-color: transparent;
}

.carrousel:hover{

   transition: all 0.2s linear;
   color: white;
   background-color: black;

}
</style>
