<template>
   <div>
      <div class="hello"><p>Welocme, {{username}}.</p></div>
      <div class="btns">
         <button class="summary" @click="summary">Summary</button>
         <button class="btn btn-danger">Delete Repo</button>
      </div>
   </div>
</template>

<script>
import Vue from 'vue';
import axios from 'axios';
import VueAxios from 'vue-axios';
Vue.use(VueAxios,axios);

export default {
   name: 'admin-portal',
   props:["username"],
   methods:{
    summary(){
      this.$router.push({name:"/gallery-summary"});
    }
  },
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Maven+Pro:wght@700&display=swap');

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

.btn-danger{
   width: 17vw;
   margin-top: 120px;
   height: 3.5vw;
   border-radius: 20px 20px 20px 20px;
   font-size: 1.25vw;
   color: white;
    background-color: red;
   box-shadow: 0 0 20px red;
}

.btn-danger:hover{
   box-shadow: 0 0 5px red,
               0 0 25px red,
               0 0 50px red,
               0 0 100px red;

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

</style>
